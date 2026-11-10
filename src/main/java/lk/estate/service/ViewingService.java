
package lk.estate.service;

import java.util.List;

import lk.estate.domain.Viewing;
import lk.estate.storage.ViewingRepository;

import org.springframework.stereotype.Service;

@Service
public class ViewingService {

    private final ViewingRepository repository;

    public ViewingService(ViewingRepository repository) {
        this.repository = repository;
    }

    public List<Viewing> getAllViewings() {
        return repository.findAll();
    }

    public Viewing getViewingById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new AppException(404, "Viewing not found."));
    }

    public Viewing addViewing(Viewing viewing) {

        if (viewing == null) {
            throw new AppException(400, "Viewing details are required.");
        }

        if (repository.findById(viewing.getId()).isPresent()) {
            throw new AppException(409, "Viewing ID already exists.");
        }

        repository.save(viewing);
        return viewing;
    }

    public Viewing updateViewing(Viewing viewing) {

        if (viewing == null) {
            throw new AppException(400, "Viewing details are required.");
        }

        getViewingById(viewing.getId());

        repository.save(viewing);
        return viewing;
    }

    public void deleteViewing(String id) {
        getViewingById(id);
        repository.deleteById(id);
    }
}
