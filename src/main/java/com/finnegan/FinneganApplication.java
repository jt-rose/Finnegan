package com.finnegan;

import com.finnegan.domain.Transaction;
import com.finnegan.domain.TransactionRespository;
import com.finnegan.domain.User;
import com.finnegan.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Date;


@SpringBootApplication
public class FinneganApplication {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TransactionRespository transactionRepo;
    private static final Logger logger =
            LoggerFactory.getLogger(FinneganApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FinneganApplication.class, args);
        logger.info("Hello Spring Boot");
        var t = new Transaction(100.00, "GROCERIES", "", new Date());
        logger.info(t.toString());
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            var item = new Transaction(100.00, "GROCERIES", "",
                    new Date());
            var item2 = new Transaction(800.00, "ELECTRICITY", "",
                    new Date());
            var item3 = new Transaction(300.00, "SHOPPING", "new shoes",
                    new Date());
            transactionRepo.save(item);
            transactionRepo.save(item2);
            transactionRepo.save(item3);

            System.out.println(transactionRepo.findByCategory("SHOPPING"));
            System.out.println(transactionRepo.findByAmountOrCategoryOrderByAmountDesc(800.00, "GROCERIES"));
            System.out.println(transactionRepo.findByAmount(200.00));

            var user1 = new User("fake@fake.com", "secret");
            userRepo.save(user1);
            System.out.println(userRepo.findAll());
        };
    }

}
