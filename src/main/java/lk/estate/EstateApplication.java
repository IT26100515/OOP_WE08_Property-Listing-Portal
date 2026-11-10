
package lk.estate;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class EstateApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EstateApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {

        return builder.sources(EstateApplication.class);
    }

    @Bean
    Clock applicationClock() {
        return Clock.system(ZoneId.of("Asia/Colombo"));
    }
}
