package com.finnegan.web;

import com.finnegan.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/transactions/paginated")
    public Page<Transaction> getPaginatedTransactions(Authentication auth,
                                                      @Param("page") int page,
                                                      @Param("size") int size) {
        var user = userRepo.findByUsername(auth.getName());
        var pageable = PageRequest.of(page, size);
        return transactionRepo.findByOwner(user, pageable);
    }

    // get account sum
    @GetMapping("/transactions/sum")
    public double getTransactionsSum(Authentication auth) {
        var user = userRepo.findByUsername(auth.getName());
        return transactionRepo.findAccountSum(user);
    }

    // create transaction
    @PostMapping("/transactions")
    public Transaction createTransaction(Authentication auth,
                                         @RequestBody Transaction transaction) {
        var user = userRepo.findByUsername(auth.getName());
        transaction.setOwner(user);
        transactionRepo.save(transaction);
        return transaction;
    }

    // edit transaction
    @PutMapping("/transactions/{id}")
    public ResponseEntity<Transaction> editTransaction(Authentication auth,
                                                       @PathVariable("id") Long id, @RequestBody Transaction updatedTransaction) {
        var user = userRepo.findByUsername(auth.getName());
        var transaction = transactionRepo.findById(id);
        if (transaction.isPresent()) {
            // check if transaction belongs to user
            if (!user.equals(transaction.get().getOwner())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            var update = transaction.get();
            update.setAmount(updatedTransaction.getAmount());
            update.setCategory(updatedTransaction.getCategory());
            update.setDate(updatedTransaction.getDate());
            update.setNote(updatedTransaction.getNote());
            transactionRepo.save(update);
            return new ResponseEntity<Transaction>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete transaction
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<Transaction> deleteTransaction(Authentication auth,
                                         @PathVariable("id") Long id) {
        var user = userRepo.findByUsername(auth.getName());
        var transaction = transactionRepo.findById(id);
            if (transaction.isPresent()) {
                // check if transaction belongs to user
                if (!user.equals(transaction.get().getOwner())) {

                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            transactionRepo.deleteById(id);
            // clear JWT on frontend
            return new ResponseEntity<Transaction>(transaction.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
