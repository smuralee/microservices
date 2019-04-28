package com.smuralee.service;

import com.smuralee.entity.User;
import com.smuralee.errors.DataNotFoundException;
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
        Optional<User> user = this.repository.findById(id);
        return user.orElseThrow(DataNotFoundException::new);
    }

    @PostMapping
    public User addUser(final @RequestBody User user) {
        log.info("Saving the new user");
        return this.repository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(final @RequestBody User user, final @PathVariable Long id) {
        log.info("Fetching the user by id");
        Optional<User> fetchedUser = this.repository.findById(id);

        log.info("Updating the user identified by the id");
        if (fetchedUser.isPresent()) {
            user.setId(id);
            return this.repository.save(user);
        } else {
            throw new DataNotFoundException();
        }
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting the user by id");
        this.repository.deleteById(id);
    }
}
