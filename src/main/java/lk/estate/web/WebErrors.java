package lk.estate.web;

import jakarta.servlet.http.HttpServletResponse;
import java.io.UncheckedIOException;
import lk.estate.service.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebErrors {
    private static final Logger LOG = LoggerFactory.getLogger(WebErrors.class);

    @ExceptionHandler(AppException.class)
    public String applicationError(AppException ex, Model model, HttpServletResponse response) {
        response.setStatus(ex.getStatus());
        model.addAttribute("errorCode", ex.getStatus());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(UncheckedIOException.class)
    public String storageError(UncheckedIOException ex, Model model, HttpServletResponse response) {
        LOG.error("Text-file storage failed", ex);
        response.setStatus(500);
        model.addAttribute("errorCode", 500);
        model.addAttribute("errorMessage", "Storage could not be read or written. Check the server log and data files.");
        return "error";
    }
}