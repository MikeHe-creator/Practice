package com.mikelearner.nationaltesttool;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NationalTestDataToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(NationalTestDataToolApplication.class, args);
    }

    @Bean
    public ApplicationRunner run(NationalData nationalData) {
        return args -> {
            nationalData.dataReader();
        };
    }

}
