package com.finnegan.web;

import com.finnegan.domain.Transaction;
import com.finnegan.domain.TransactionRespository;
import com.finnegan.domain.UserRepository;
import com.finnegan.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    private TransactionRespository transactionRepo;

    @Autowired
    private UserRepository userRepo;

    // get transactions with pagination / filter
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(Authentication auth) {
        var user = userRepo.findByUsername(auth.getName());
        return transactionRepo.findByOwner(user);
    }

    // get account sum
    @GetMapping("transactions/sum")
    public double getTransactionsSum(Authentication auth) {
        var user = userRepo.findByUsername(auth.getName());
        return transactionRepo.findAccountSum(user);
    }

    // create transaction

    // edit transaction

    // delete transaction
}
