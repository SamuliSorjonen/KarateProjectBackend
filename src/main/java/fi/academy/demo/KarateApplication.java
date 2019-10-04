package fi.academy.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class KarateApplication {

    public static void main(String[] args) {
        SpringApplication.run(KarateApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

}
