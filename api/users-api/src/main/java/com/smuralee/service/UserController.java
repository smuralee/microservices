package com.smuralee.service;

import com.smuralee.entity.User;
import com.smuralee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(final @PathVariable Long id) {
        Optional<User> User = this.repository.findById(id);
        return User.get();
    }

    @PostMapping
    public User addUser(final @RequestBody User User) {
        return this.repository.save(User);
    }

    @PutMapping("/{id}")
    public User updateUser(final @RequestBody User User, final @PathVariable Long id) {
        Optional<User> fetchedUser = this.repository.findById(id);
        User updatedUser = null;
        if (fetchedUser.isPresent()) {
            User.setId(id);
            updatedUser = this.repository.save(User);
        }
        return updatedUser;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}
