package lk.estate.web;

import java.time.LocalDateTime;
import lk.estate.domain.Viewing;
import lk.estate.domain.ViewingStatus;
import org.springframework.format.annotation.DateTimeFormat;

public class ViewingForm {
    private String id;
    private String listingId;
    private String customerName;
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime scheduledAt;
    private String type = "IN_PERSON";
    private String meetingDetails;
    private ViewingStatus status = ViewingStatus.REQUESTED;

    public static ViewingForm from(Viewing viewing) {
        ViewingForm form = new ViewingForm();
        form.id = viewing.getId();
        form.listingId = viewing.getListingId();
        form.customerName = viewing.getCustomerName();
        form.email = viewing.getEmail();
        form.scheduledAt = viewing.getScheduledAt();
        form.type = viewing.getType();
        form.meetingDetails = viewing.getMeetingDetails();
        form.status = viewing.getStatus();
        return form;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getListingId() { return listingId; }
    public void setListingId(String listingId) { this.listingId = listingId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMeetingDetails() { return meetingDetails; }
    public void setMeetingDetails(String meetingDetails) { this.meetingDetails = meetingDetails; }
    public ViewingStatus getStatus() { return status; }
    public void setStatus(ViewingStatus status) { this.status = status; }
}