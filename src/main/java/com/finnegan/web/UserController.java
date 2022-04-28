package com.finnegan.web;

import com.finnegan.domain.Transaction;
import com.finnegan.domain.TransactionRespository;
import com.finnegan.domain.User;
import com.finnegan.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
public class UserController {
    @Autowired
    private TransactionRespository transactionRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/user")
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

    @PostMapping("/user")
    public User createUser(@RequestBody User userInput) {
        var unhashedPassword = userInput.getPassword();
        userInput.setPassword(passwordEncoder.encode(unhashedPassword));
        userInput.setRole("USER");

        var user = userRepo.save(userInput);
        return user;
    }

//    @PutMapping("/user/{id}")
//    public User editUsername(@PathVariable("id") Long id,
//                         @RequestBody String username) {
//        var currentUser = userRepo.findById(id);
//        if (currentUser.isPresent()) {
//
//        }
//        currentUser.
//
//    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(Authentication auth,
                                           @PathVariable("id") Long id) {

            var authenticatedUser = auth.getName();
            var user = userRepo.findById(id);
            if (user.isPresent()) {
                if (!authenticatedUser.equals(user.get().getUsername())) {
                    System.out.println(authenticatedUser);
                    System.out.println(user.get().getUsername());
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
                userRepo.deleteById(id);
                // clear JWT on frontend
                return new ResponseEntity<User>(user.get(), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
