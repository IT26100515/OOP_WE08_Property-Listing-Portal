
package lk.estate.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import lk.estate.domain.Viewing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class ViewingRepository extends FileRepository<Viewing> {

    public ViewingRepository(
            @Value("${app.data-dir:./data}") String dataDir,
            ObjectMapper mapper) {

        super(
                Path.of(dataDir).resolve("viewings.txt"),
                mapper,
                Viewing.class
        );
    }
}
