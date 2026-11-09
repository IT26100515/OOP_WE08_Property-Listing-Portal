package lk.estate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.math.BigDecimal;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SaleListing.class, name = "SALE"),
        @JsonSubTypes.Type(value = RentalListing.class, name = "RENT")
})
public abstract class Listing implements Identified {
    private final String id;
    private final String title;
    private final String city;
    private final String address;
    private final BigDecimal price;
    private final String description;

    protected Listing(String id, String title, String city, String address,
                      BigDecimal price, String description) {
        this.id = Rules.text(id, "Listing ID", 80);
        this.title = Rules.text(title, "Title", 120);
        this.city = Rules.text(city, "City", 80);
        this.address = Rules.text(address, "Address", 250);
        this.price = Rules.price(price);
        this.description = Rules.text(description, "Description", 2000);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }

    @JsonIgnore
    public abstract String getType();

    @JsonIgnore
    public abstract String getPriceLabel();
}
