
package lk.estate.service;

import java.util.List;

import lk.estate.domain.Listing;
import lk.estate.storage.ListingRepository;

import org.springframework.stereotype.Service;

@Service
public class ListingService {

    private final ListingRepository repository;

    public ListingService(ListingRepository repository) {
        this.repository = repository;
    }

    public List<Listing> getAllListings() {
        return repository.findAll();
    }

    public Listing getListingById(String id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new AppException(404, "Listing not found."));
    }

    public Listing addListing(Listing listing) {

        if (listing == null) {
            throw new AppException(400, "Listing details are required.");
        }

        if (repository.findById(listing.getId()).isPresent()) {
            throw new AppException(409, "Listing ID already exists.");
        }

        repository.save(listing);
        return listing;
    }

    public Listing updateListing(Listing listing) {

        if (listing == null) {
            throw new AppException(400, "Listing details are required.");
        }

        getListingById(listing.getId());

        repository.save(listing);
        return listing;
    }

    public void deleteListing(String id) {
        getListingById(id);
        repository.deleteById(id);
    }
}
