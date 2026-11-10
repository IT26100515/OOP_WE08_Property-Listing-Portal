package lk.estate.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Locale;

public final class SaleListing extends Listing {
    @JsonCreator
    public SaleListing(@JsonProperty("id") String id,
                       @JsonProperty("title") String title,
                       @JsonProperty("city") String city,
                       @JsonProperty("address") String address,
                       @JsonProperty("price") BigDecimal price,
                       @JsonProperty("description") String description) {
        super(id, title, city, address, price, description);
    }

    @Override
    public String getType() { return "SALE"; }

    @Override
    public String getPriceLabel() {
        return String.format(Locale.US, "LKR %,.2f", getPrice());
    }
}