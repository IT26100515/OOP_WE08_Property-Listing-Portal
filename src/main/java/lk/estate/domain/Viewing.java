package lk.estate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.time.LocalDateTime;
import lk.estate.service.AppException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = InPersonViewing.class, name = "IN_PERSON"),
        @JsonSubTypes.Type(value = VirtualViewing.class, name = "VIRTUAL")
})
public abstract class Viewing implements Identified {
    private final String id;
    private final String listingId;
    private final String customerName;
    private final String email;
    private final LocalDateTime scheduledAt;
    private final ViewingStatus status;

    protected Viewing(String id, String listingId, String customerName, String email,
                      LocalDateTime scheduledAt, ViewingStatus status) {
        this.id = Rules.text(id, "Viewing ID", 80);
        this.listingId = Rules.text(listingId, "Listing ID", 80);
        this.customerName = Rules.text(customerName, "Customer name", 100);
        this.email = Rules.email(email);
        if (scheduledAt == null || scheduledAt.getSecond() != 0
                || scheduledAt.getNano() != 0 || scheduledAt.getMinute() % 30 != 0) {
            throw new AppException(400, "Choose a 30-minute slot at :00 or :30.");
        }
        if (status == null) {
            throw new AppException(400, "Viewing status is required.");
        }
        this.scheduledAt = scheduledAt;
        this.status = status;
    }

    public String getId() { return id; }
    public String getListingId() { return listingId; }
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public ViewingStatus getStatus() { return status; }

    @JsonIgnore
    public abstract String getType();

    @JsonIgnore
    public abstract String getMeetingDetails();

    @JsonIgnore
    public abstract String getInstructions();
}
