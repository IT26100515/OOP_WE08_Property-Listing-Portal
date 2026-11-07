package lk.estate.storage;

import java.util.List;
import java.util.Optional;
import lk.estate.domain.Identified;

public interface Repository<T extends Identified> {
    List<T> findAll();
    Optional<T> findById(String id);
    void save(T item);
    void deleteById(String id);
}