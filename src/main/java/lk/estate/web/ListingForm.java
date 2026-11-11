package lk.estate.web;

import java.math.BigDecimal;
import lk.estate.domain.Listing;

/** Mutable web input; persisted domain objects remain immutable. */
public class ListingForm {
    private String id;
    private String type = "SALE";
    private String title;
    private String city;
    private String address;
    private BigDecimal price;
    private String description;

    public static ListingForm from(Listing listing) {
        ListingForm form = new ListingForm();
        form.id = listing.getId();
        form.type = listing.getType();
        form.title = listing.getTitle();
        form.city = listing.getCity();
        form.address = listing.getAddress();
        form.price = listing.getPrice();
        form.description = listing.getDescription();
        return form;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}