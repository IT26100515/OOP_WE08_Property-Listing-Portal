package lk.estate.domain;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import lk.estate.service.AppException;

/** Reusable invariant checks shared by constructors and services. */
public final class Rules {
    private Rules() { }

    public static String text(String value, String label, int maximum) {
        if (value == null || value.isBlank()) {
            throw new AppException(400, label + " is required.");
        }
        String result = value.strip();
        if (result.length() > maximum) {
            throw new AppException(400, label + " must have at most " + maximum + " characters.");
        }
        return result;
    }

    public static BigDecimal price(BigDecimal value) {
        if (value == null || value.signum() <= 0 || value.scale() > 2
                || value.compareTo(new BigDecimal("99999999999999.99")) > 0) {
            throw new AppException(400, "Price must be positive, with at most two decimal places.");
        }
        return value;
    }

    public static String email(String value) {
        String result = text(value, "Email", 254);
        if (!result.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new AppException(400, "Enter an email such as name@example.com.");
        }
        return result;
    }

    public static String meetingUrl(String value) {
        String result = text(value, "Meeting URL", 500);
        try {
            URI uri = new URI(result);
            if (!"https".equalsIgnoreCase(uri.getScheme()) || uri.getHost() == null
                    || uri.getUserInfo() != null) {
                throw new AppException(400, "Virtual meetings require an HTTPS URL with a host and no username/password.");
            }
        } catch (URISyntaxException exception) {
            throw new AppException(400, "Enter a valid HTTPS meeting URL.");
        }
        return result;
    }
}