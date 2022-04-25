package com.finnegan;

import com.finnegan.domain.Transaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


@SpringBootApplication
public class FinneganApplication {
    private static final Logger logger =
            LoggerFactory.getLogger(FinneganApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FinneganApplication.class, args);
        logger.info("Hello Spring Boot");
        var t = new Transaction(100.00, "GROCERIES", "", new Date());
        logger.info(t.toString());
    }

}
