package lk.estate.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lk.estate.domain.Listing;
import lk.estate.domain.RentalListing;
import lk.estate.domain.SaleListing;
import lk.estate.storage.ListingRepository;
import lk.estate.storage.ViewingRepository;
import org.springframework.stereotype.Service;

@Service
public class ListingService {
    private final ListingRepository listings;
    private final ViewingRepository viewings;

    public ListingService(ListingRepository listings, ViewingRepository viewings) {
        this.listings = listings;
        this.viewings = viewings;
    }

    public List<Listing> search(String query) {
        String term = query == null ? "" : query.strip().toLowerCase(Locale.ROOT);
        return listings.findAll().stream().filter(item -> term.isEmpty()
                || item.getTitle().toLowerCase(Locale.ROOT).contains(term)
                || item.getCity().toLowerCase(Locale.ROOT).contains(term)
                || item.getAddress().toLowerCase(Locale.ROOT).contains(term)).toList();
    }

    public Listing get(String id) {
        return listings.findById(id)
                .orElseThrow(() -> new AppException(404, "Property listing was not found."));
    }

    public Listing save(String id, String type, String title, String city, String address,
                        BigDecimal price, String description) {
        synchronized (listings) {
            String recordId;
            if (id == null || id.isBlank()) {
                recordId = UUID.randomUUID().toString();
            } else {
                recordId = get(id).getId();
            }
            Listing listing;
            if ("SALE".equals(type)) {
                listing = new SaleListing(recordId, title, city, address, price, description);
            } else if ("RENT".equals(type)) {
                listing = new RentalListing(recordId, title, city, address, price, description);
            } else {
                throw new AppException(400, "Select sale or rent.");
            }
            listings.save(listing);
            return listing;
        }
    }

    public void delete(String id) {
        synchronized (listings) {
            get(id);
            boolean referenced = viewings.findAll().stream()
                    .anyMatch(viewing -> viewing.getListingId().equals(id));
            if (referenced) {
                throw new AppException(409,
                        "Delete this property's viewing records before deleting the listing.");
            }
            listings.deleteById(id);
        }
    }
}