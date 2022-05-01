package com.finnegan.web;

import com.finnegan.domain.RecurringTransaction;
import com.finnegan.domain.RecurringTransactionRepository;
import com.finnegan.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecurringTransactionController {
    @Autowired
    private RecurringTransactionRepository recurringRepo;

    @Autowired
    private UserRepository userRepo;


    // get all recurring transactions
    // this number should be small, so pagination is not needed
    @GetMapping("/recurring")
    public List<RecurringTransaction> getRecurringTransactions(
            Authentication auth) {
        var user = userRepo.findByUsername(auth.getName());
        return recurringRepo.findRecurringTransactionByOwner(user);
    }

    // create recurring transaction
    @PostMapping("/recurring")
    public RecurringTransaction createRecurringTransaction(Authentication auth, @RequestBody RecurringTransaction recurringInput) {
        var user = userRepo.findByUsername(auth.getName());
        recurringInput.setOwner(user);
        recurringRepo.save(recurringInput);
        return recurringInput;
    }

    // edit recurring transaction
    @PutMapping("/recurring/{id}")
    public ResponseEntity<RecurringTransaction> editRecurringTransaction(Authentication auth
            , @PathVariable("id") Long id,
                                                         @RequestBody RecurringTransaction recurringInput) {
        var user = userRepo.findByUsername(auth.getName());
        var recurringTransaction = recurringRepo.findById(id);
        if (recurringTransaction.isPresent()) {
            // check if transaction belongs to user
            if (!user.equals(recurringTransaction.get().getOwner())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            var update = recurringTransaction.get();
            update.setAmount(recurringInput.getAmount());
            update.setCategory(recurringInput.getCategory());
            update.setDate(recurringInput.getDate());
            update.setNote(recurringInput.getNote());
            recurringRepo.save(update);
            return new ResponseEntity<RecurringTransaction>(update, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // delete recurring transaction
    @DeleteMapping("/recurring/{id}")
    public ResponseEntity<RecurringTransaction> deleteRecurringTransaction(Authentication auth, @PathVariable("id") Long id) {
        var user = userRepo.findByUsername(auth.getName());
        var recurringTransaction = recurringRepo.findById(id);
        if (recurringTransaction.isPresent()) {
            // check if transaction belongs to user
            if (!user.equals(recurringTransaction.get().getOwner())) {

                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            recurringRepo.deleteById(id);
            // clear JWT on frontend
            return new ResponseEntity<RecurringTransaction>(recurringTransaction.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
