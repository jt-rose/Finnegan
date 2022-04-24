package com.finnegan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
public class FinneganApplication {
    private static final Logger logger =
            LoggerFactory.getLogger(FinneganApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FinneganApplication.class, args);
        logger.info("Hello Spring Boot");
    }

}
