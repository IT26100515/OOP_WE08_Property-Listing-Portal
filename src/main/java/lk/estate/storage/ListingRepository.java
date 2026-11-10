
package lk.estate.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import lk.estate.domain.Listing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ListingRepository extends FileRepository<Listing> {

    public ListingRepository(
            @Value("${app.data-dir:./data}") String dataDir,
            ObjectMapper mapper) {

        super(
                Path.of(dataDir).resolve("listings.txt"),
                mapper,
                Listing.class
        );
    }
}
