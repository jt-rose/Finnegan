package com.finnegan.web;

import com.finnegan.domain.Transaction;
import com.finnegan.domain.TransactionRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerController {
    @Autowired
    private TransactionRespository transactionRepo;

    @RequestMapping("/")
    public Iterable<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }
}
