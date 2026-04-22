package com.sprint.BookPartnerApplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    // Password validation / encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // Soumya
                .requestMatchers("/api/v1/authors/**")
                    .hasAnyRole("SOUMYA", "ADMIN")

                .requestMatchers("/api/v1/publishers/**")
                    .hasAnyRole("SOUMYA", "ADMIN")

                // Hema
                .requestMatchers("/api/v1/titles/**")
                    .hasAnyRole("HEMA", "ADMIN")

                .requestMatchers("/api/v1/title-authors/**")
                    .hasAnyRole("HEMA", "ADMIN")

                // Harini
                .requestMatchers("/api/v1/stores/**")
                    .hasAnyRole("HARINI", "ADMIN")

                .requestMatchers("/api/v1/sales/**")
                    .hasAnyRole("HARINI", "ADMIN")

                // Pradeep
                .requestMatchers("/api/v1/royalties/**")
                    .hasAnyRole("PRADEEP", "ADMIN")

                .requestMatchers("/api/v1/discounts/**")
                    .hasAnyRole("PRADEEP", "ADMIN")

                // Subasri
                .requestMatchers("/api/v1/jobs/**")
                    .hasAnyRole("SUBASRI", "ADMIN")

                .requestMatchers("/api/v1/employees/**")
                    .hasAnyRole("SUBASRI", "ADMIN")

                // Reports
                .requestMatchers("/api/v1/reports/sales/by-title")
                    .hasAnyRole("HEMA", "ADMIN")

                .requestMatchers("/api/v1/reports/sales/by-publisher")
                    .hasAnyRole("SOUMYA", "ADMIN")

                .requestMatchers("/api/v1/reports/sales/date-range")
                    .hasAnyRole("HARINI", "ADMIN")

                .requestMatchers("/api/v1/reports/authors/royalties")
                    .hasAnyRole("PRADEEP", "ADMIN")

                .requestMatchers("/api/v1/reports/authors/top-selling")
                    .hasAnyRole("HARINI", "ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

        UserDetails soumya = User
                .withUsername("soumya")
                .password(passwordEncoder.encode("soumya123"))
                .roles("SOUMYA")
                .build();

        UserDetails hema = User
                .withUsername("hema")
                .password(passwordEncoder.encode("hema123"))
                .roles("HEMA")
                .build();

        UserDetails harini = User
                .withUsername("harini")
                .password(passwordEncoder.encode("harini123"))
                .roles("HARINI")
                .build();

        UserDetails pradeep = User
                .withUsername("pradeep")
                .password(passwordEncoder.encode("pradeep123"))
                .roles("PRADEEP")
                .build();

        UserDetails subasri = User
                .withUsername("subasri")
                .password(passwordEncoder.encode("subasri123"))
                .roles("SUBASRI")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(
                soumya,
                hema,
                harini,
                pradeep,
                subasri,
                admin
        );
    }
}