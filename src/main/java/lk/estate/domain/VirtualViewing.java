package lk.estate.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public final class VirtualViewing extends Viewing {
    private final String meetingUrl;

    @JsonCreator
    public VirtualViewing(@JsonProperty("id") String id,
                          @JsonProperty("listingId") String listingId,
                          @JsonProperty("customerName") String customerName,
                          @JsonProperty("email") String email,
                          @JsonProperty("scheduledAt") LocalDateTime scheduledAt,
                          @JsonProperty("status") ViewingStatus status,
                          @JsonProperty("meetingUrl") String meetingUrl) {
        super(id, listingId, customerName, email, scheduledAt, status);
        this.meetingUrl = Rules.meetingUrl(meetingUrl);
    }

    public String getMeetingUrl() { return meetingUrl; }

    @Override
    public String getType() { return "VIRTUAL"; }

    @Override
    public String getMeetingDetails() { return meetingUrl; }

    @Override
    public String getInstructions() { return "Join the virtual viewing using " + meetingUrl + "."; }
}