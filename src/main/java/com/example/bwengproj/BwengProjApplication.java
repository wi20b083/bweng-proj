package com.example.bwengproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.bwengproj.repository")
public class BwengProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(BwengProjApplication.class, args);
    }

}
