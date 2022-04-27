package com.finnegan.web;

import com.finnegan.domain.Transaction;
import com.finnegan.domain.TransactionRespository;
import com.finnegan.domain.User;
import com.finnegan.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {
    @Autowired
    private TransactionRespository transactionRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public Iterable<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }

    @GetMapping("/me")
    public User getCurrentUser(Authentication auth) {
        var user = auth.getName();
        System.out.println(user);
        var me = userRepo.findByUsername(user);
        // test creating new transaction
        var t1 = new Transaction(me, 2000.00, "GROCERIES", "testing me " +
                "query", new Date());
        System.out.println(t1);
        transactionRepo.save(t1);
        return me;
    }
}
