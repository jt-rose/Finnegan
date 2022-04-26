package com.finnegan;

import com.finnegan.domain.*;
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
    private OwnerRepository ownerRepo;
    @Autowired
    private TransactionRespository transactionRepo;
    private static final Logger logger =
            LoggerFactory.getLogger(FinneganApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FinneganApplication.class, args);
        logger.info("Hello Spring Boot");
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            // add owners
            var owner1 = new Owner("fake@fake.com", "secret");
            ownerRepo.save(owner1);
            var owner2 = new Owner("fake2@fake.com", "secret");
            ownerRepo.save(owner2);
            System.out.println(ownerRepo.findAll());

            var item = new Transaction(owner1, 100.00, "GROCERIES", "",
                    new Date());
            var item2 = new Transaction(owner1, 800.00, "ELECTRICITY", "",
                    new Date());
            var item3 = new Transaction(owner2, 300.00, "SHOPPING", "new " +
                    "shoes",
                    new Date());
            transactionRepo.save(item);
            transactionRepo.save(item2);
            transactionRepo.save(item3);

            System.out.println(transactionRepo.findByCategory("SHOPPING"));
            System.out.println(transactionRepo.findByAmountOrCategoryOrderByAmountDesc(800.00, "GROCERIES"));
            System.out.println(transactionRepo.findByAmount(200.00));

            // username: user, password: password
            var user1 = new User("user", "$2a$10$xw9sBPJ/cRwGSapvznh09" +
                    ".c//wwfY8rnaPkkNeXcKhN19vRuveDIC", "USER");
            // username: admin, password: password
            var user2 = new User("admin", "$2a$10$7iIqaMZTYx8jjG" +
                    ".Jobe1KOUSGAbviNiYPBT/1ykzQtvPxUcsGHP0q", "ADMIN");

            userRepo.save(user1);
            userRepo.save(user2);
            System.out.println(userRepo.findAll());
        };
    }

}
