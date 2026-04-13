package com.sprint.BookPartnerApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookPartnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookPartnerApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("Hello from HemaHamsaveni's branch! Spring Boot started successfully.");
        };
    }

}
