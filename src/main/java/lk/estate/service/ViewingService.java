package lk.estate.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lk.estate.domain.InPersonViewing;
import lk.estate.domain.Viewing;
import lk.estate.domain.ViewingStatus;
import lk.estate.domain.VirtualViewing;
import lk.estate.storage.ListingRepository;
import lk.estate.storage.ViewingRepository;
import org.springframework.stereotype.Service;

@Service
public class ViewingService {
    private final ViewingRepository viewings;
    private final ListingRepository listings;
    private final Clock clock;

    public ViewingService(ViewingRepository viewings, ListingRepository listings, Clock clock) {
        this.viewings = viewings;
        this.listings = listings;
        this.clock = clock;
    }

    public List<Viewing> all() {
        return viewings.findAll().stream()
                .sorted(Comparator.comparing(Viewing::getScheduledAt)).toList();
    }

    public Viewing get(String id) {
        return viewings.findById(id)
                .orElseThrow(() -> new AppException(404, "Viewing appointment was not found."));
    }

    public Viewing save(String id, String listingId, String customerName, String email,
                        LocalDateTime scheduledAt, String type, String meetingDetails,
                        ViewingStatus status) {
        // All multi-record writes use the same monitor, including listing deletion.
        synchronized (listings) {
            if (listings.findById(listingId).isEmpty()) {
                throw new AppException(404, "Choose an existing property listing.");
            }
            boolean creating = id == null || id.isBlank();
            Viewing existing = creating ? null : get(id);
            if (creating) {
                if (status != null && status != ViewingStatus.REQUESTED) {
                    throw new AppException(400, "A new viewing must start as REQUESTED.");
                }
                status = ViewingStatus.REQUESTED;
            } else {
                if (!existing.getListingId().equals(listingId)) {
                    throw new AppException(400, "An existing viewing cannot change its property.");
                }
                validateTransition(existing.getStatus(), status);
            }
            LocalDateTime now = LocalDateTime.now(clock);
            if (scheduledAt == null) {
                throw new AppException(400, "Choose the viewing date and time.");
            }
            if ((creating || !scheduledAt.equals(existing.getScheduledAt()))
                    && !scheduledAt.isAfter(now)) {
                throw new AppException(400, "New or rescheduled viewings must be in the future.");
            }
            if (status == ViewingStatus.COMPLETED && scheduledAt.isAfter(now)) {
                throw new AppException(400, "A viewing can be completed only at or after its scheduled time.");
            }
            String recordId = creating ? UUID.randomUUID().toString() : existing.getId();
            Viewing viewing;
            if ("IN_PERSON".equals(type)) {
                viewing = new InPersonViewing(recordId, listingId, customerName, email,
                        scheduledAt, status, meetingDetails);
            } else if ("VIRTUAL".equals(type)) {
                viewing = new VirtualViewing(recordId, listingId, customerName, email,
                        scheduledAt, status, meetingDetails);
            } else {
                throw new AppException(400, "Select in-person or virtual viewing.");
            }
            if (reservesSlot(status)) {
                boolean occupied = viewings.findAll().stream().anyMatch(other ->
                        !other.getId().equals(recordId)
                                && other.getListingId().equals(listingId)
                                && other.getScheduledAt().equals(scheduledAt)
                                && reservesSlot(other.getStatus()));
                if (occupied) {
                    throw new AppException(409, "This property already has a viewing in that 30-minute slot.");
                }
            }
            viewings.save(viewing);
            return viewing;
        }
    }

    public void delete(String id) {
        synchronized (listings) {
            get(id);
            viewings.deleteById(id);
        }
    }

    private boolean reservesSlot(ViewingStatus status) {
        return status == ViewingStatus.REQUESTED || status == ViewingStatus.CONFIRMED;
    }

    private void validateTransition(ViewingStatus current, ViewingStatus next) {
        boolean allowed = switch (current) {
            case REQUESTED -> next == ViewingStatus.REQUESTED || next == ViewingStatus.CONFIRMED
                    || next == ViewingStatus.CANCELLED;
            case CONFIRMED -> next == ViewingStatus.CONFIRMED || next == ViewingStatus.COMPLETED
                    || next == ViewingStatus.CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };
        if (!allowed) {
            throw new AppException(400,
                    "Invalid status change. Completed/cancelled viewings cannot be edited.");
        }
    }
}