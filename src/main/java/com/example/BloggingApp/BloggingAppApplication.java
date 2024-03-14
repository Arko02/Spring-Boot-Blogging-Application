package com.example.BloggingApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BloggingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggingAppApplication.class, args);
    }

    @Bean // For Mapping Methods in IMPL class
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
