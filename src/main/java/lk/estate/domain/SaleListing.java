
package lk.estate.domain;

import java.math.BigDecimal;

public class SaleListing extends Listing {

    public SaleListing(String id, String title, String city,
                       String address, BigDecimal price,
                       String description) {

        super(id, title, city, address, price, description);
    }

    @Override
    public String getType() {
        return "SALE";
    }

    @Override
    public String getPriceLabel() {
        return "Sale Price";
    }
}
