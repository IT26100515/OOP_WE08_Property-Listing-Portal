package lk.estate.web;

import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import lk.estate.domain.Listing;
import lk.estate.domain.Viewing;
import lk.estate.domain.ViewingStatus;
import lk.estate.service.AppException;
import lk.estate.service.ListingService;
import lk.estate.service.ViewingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewingController {
    private final ViewingService service;
    private final ListingService listings;

    public ViewingController(ViewingService service, ListingService listings) {
        this.service = service;
        this.listings = listings;
    }

    @GetMapping("/viewings")
    public String index(Model model) {
        Map<String, String> titles = new LinkedHashMap<>();
        for (Listing listing : listings.search("")) {
            titles.put(listing.getId(), listing.getTitle());
        }
        model.addAttribute("listingTitles", titles);
        model.addAttribute("viewings", service.all());
        return "viewings/index";
    }

    @GetMapping("/viewings/new")
    public String create(@RequestParam(required = false) String listingId, Model model) {
        ViewingForm form = new ViewingForm();
        if (listingId != null && !listingId.isBlank()) {
            form.setListingId(listings.get(listingId).getId());
        }
        model.addAttribute("viewingForm", form);
        return prepareForm(model);
    }

    @GetMapping("/viewings/{id}")
    public String detail(@PathVariable String id, Model model) {
        Viewing viewing = service.get(id);
        model.addAttribute("viewing", viewing);
        model.addAttribute("listing", listings.get(viewing.getListingId()));
        return "viewings/detail";
    }

    @GetMapping("/viewings/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Viewing viewing = service.get(id);
        if (viewing.getStatus() == ViewingStatus.COMPLETED
                || viewing.getStatus() == ViewingStatus.CANCELLED) {
            throw new AppException(409, "Completed and cancelled appointments cannot be edited.");
        }
        model.addAttribute("viewingForm", ViewingForm.from(viewing));
        return prepareForm(model);
    }

    @PostMapping("/viewings/save")
    public String save(@ModelAttribute("viewingForm") ViewingForm form, BindingResult result,
                       Model model, RedirectAttributes flash, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(400);
            model.addAttribute("error", "Check the highlighted fields, date/time, and status.");
            return prepareForm(model);
        }
        try {
            Viewing viewing = service.save(form.getId(), form.getListingId(), form.getCustomerName(),
                    form.getEmail(), form.getScheduledAt(), form.getType(), form.getMeetingDetails(),
                    form.getStatus());
            flash.addFlashAttribute("success", "Viewing saved.");
            return "redirect:/viewings/" + viewing.getId();
        } catch (AppException ex) {
            response.setStatus(ex.getStatus());
            model.addAttribute("error", ex.getMessage());
            return prepareForm(model);
        }
    }

    @PostMapping("/viewings/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes flash) {
        service.delete(id);
        flash.addFlashAttribute("success", "Viewing deleted.");
        return "redirect:/viewings";
    }

    private String prepareForm(Model model) {
        model.addAttribute("listings", listings.search(""));
        return "viewings/form";
    }
}