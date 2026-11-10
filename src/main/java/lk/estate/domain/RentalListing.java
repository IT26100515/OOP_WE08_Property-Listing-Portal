
package lk.estate.domain;

import java.math.BigDecimal;

public class RentalListing extends Listing {

    public RentalListing(String id, String title, String city,
                         String address, BigDecimal price,
                         String description) {

        super(id, title, city, address, price, description);
    }

    @Override
    public String getType() {
        return "RENT";
    }

    @Override
    public String getPriceLabel() {
        return "Monthly Rent";
    }
}
