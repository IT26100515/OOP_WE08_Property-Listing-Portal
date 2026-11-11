package lk.estate.web;

import jakarta.servlet.http.HttpServletResponse;
import lk.estate.domain.Listing;
import lk.estate.service.AppException;
import lk.estate.service.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ListingController {
    private final ListingService service;

    public ListingController(ListingService service) { this.service = service; }

    @GetMapping("/")
    public String home() { return "redirect:/listings"; }

    @GetMapping("/listings")
    public String index(@RequestParam(defaultValue = "") String q, Model model) {
        model.addAttribute("listings", service.search(q));
        model.addAttribute("query", q);
        return "listings/index";
    }

    @GetMapping("/listings/new")
    public String create(Model model) {
        model.addAttribute("listingForm", new ListingForm());
        return "listings/form";
    }

    @GetMapping("/listings/{id}")
    public String detail(@PathVariable String id, Model model) {
        model.addAttribute("listing", service.get(id));
        return "listings/detail";
    }

    @GetMapping("/listings/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("listingForm", ListingForm.from(service.get(id)));
        return "listings/form";
    }

    @PostMapping("/listings/save")
    public String save(@ModelAttribute("listingForm") ListingForm form, BindingResult result,
                       Model model, RedirectAttributes flash, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(400);
            model.addAttribute("error", "Check the highlighted fields. Price must be a number.");
            return "listings/form";
        }
        try {
            Listing listing = service.save(form.getId(), form.getType(), form.getTitle(),
                    form.getCity(), form.getAddress(), form.getPrice(), form.getDescription());
            flash.addFlashAttribute("success", "Property saved.");
            return "redirect:/listings/" + listing.getId();
        } catch (AppException ex) {
            response.setStatus(ex.getStatus());
            model.addAttribute("error", ex.getMessage());
            return "listings/form";
        }
    }

    @PostMapping("/listings/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes flash) {
        try {
            service.delete(id);
            flash.addFlashAttribute("success", "Property deleted.");
            return "redirect:/listings";
        } catch (AppException ex) {
            if (ex.getStatus() != 409) { throw ex; }
            flash.addFlashAttribute("error", ex.getMessage());
            return "redirect:/listings/" + id;
        }
    }
}