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
import java.util.Map;

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

    // may refactor later to use single query
    // return new JWT with user
    @PutMapping("/user/{id}")
    public ResponseEntity<User> editUsername(Authentication auth,
                               @PathVariable("id") Long id,
                         @RequestBody Map<String, String> username) {
        var authenticatedUser = auth.getName();
        var user = userRepo.findById(id);
        if (user.isPresent()) {
            if (!authenticatedUser.equals(user.get().getUsername())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            var updatedUser = user.get();
            updatedUser.setUsername(username.get("username"));
            System.out.println(username.get("username"));
            System.out.println(updatedUser.toString());
            userRepo.save(updatedUser);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(Authentication auth,
                                           @PathVariable("id") Long id) {

            var authenticatedUser = auth.getName();
            var user = userRepo.findById(id);
            if (user.isPresent()) {
                if (!authenticatedUser.equals(user.get().getUsername())) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
                userRepo.deleteById(id);
                // clear JWT on frontend
                return new ResponseEntity<User>(user.get(), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
