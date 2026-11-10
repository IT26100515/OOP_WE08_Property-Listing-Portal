
package lk.estate.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public final class InPersonViewing extends Viewing {

    private final String meetingPoint;

    @JsonCreator
    public InPersonViewing(
            @JsonProperty("id") String id,
            @JsonProperty("listingId") String listingId,
            @JsonProperty("customerName") String customerName,
            @JsonProperty("email") String email,
            @JsonProperty("scheduledAt") LocalDateTime scheduledAt,
            @JsonProperty("status") ViewingStatus status,
            @JsonProperty("meetingPoint") String meetingPoint) {

        super(id, listingId, customerName, email, scheduledAt, status);

        this.meetingPoint = Rules.text(
                meetingPoint, "Meeting point", 250);
    }

    public String getMeetingPoint() {
        return meetingPoint;
    }

    @Override
    public String getType() {
        return "IN_PERSON";
    }

    @Override
    public String getMeetingDetails() {
        return meetingPoint;
    }

    @Override
    public String getInstructions() {
        return "Meet in person at " + meetingPoint + ".";
    }
}
