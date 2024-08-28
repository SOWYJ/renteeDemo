package com.example.activityservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ActivityServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityServicesApplication.class, args);
    }

}
