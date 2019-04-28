package com.smuralee.service;

import com.smuralee.entity.User;
import com.smuralee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all the users");
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(final @PathVariable Long id) {
        log.info("Getting the users by id");
        Optional<User> User = this.repository.findById(id);
        return User.get();
    }

    @PostMapping
    public User addUser(final @RequestBody User User) {
        log.info("Saving the new user");
        return this.repository.save(User);
    }

    @PutMapping("/{id}")
    public User updateUser(final @RequestBody User User, final @PathVariable Long id) {
        log.info("Fetching the user by id");
        Optional<User> fetchedUser = this.repository.findById(id);
        User updatedUser = null;
        if (fetchedUser.isPresent()) {
            User.setId(id);
            updatedUser = this.repository.save(User);
        }
        log.info("Updated the user identified by the id");
        return updatedUser;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting the user by id");
        this.repository.deleteById(id);
    }
}
