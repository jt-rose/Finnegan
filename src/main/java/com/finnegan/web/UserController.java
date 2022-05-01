package com.finnegan.web;

import com.finnegan.domain.User;
import com.finnegan.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public User getCurrentUser(Authentication auth) {
        var user = auth.getName();
        var me = userRepo.findByUsername(user);
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

    @PutMapping("/user/setgoal")
    public User setGoal(Authentication auth,
                                @Param("goal") Double goal,
                        @Param("date") @DateTimeFormat(iso =
                                DateTimeFormat.ISO.DATE) Date date) {
        // get user record
        var authenticatedUser = auth.getName();
        var user = userRepo.findByUsername(authenticatedUser);

        // update user goals
        user.setGoal(goal);
        user.setGoalDate(date);

        userRepo.save(user);

        // return updated user object
        return user;
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
