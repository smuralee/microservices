package com.smuralee.service;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import com.smuralee.client.IOrderClient;
import com.smuralee.config.AppConfig;
import com.smuralee.domain.Order;
import com.smuralee.domain.User;
import com.smuralee.entity.UserInfo;
import com.smuralee.errors.DataNotFoundException;
import com.smuralee.repository.UserRepository;
import com.smuralee.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@XRayEnabled
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    private final IOrderClient orderClient;

    private final Environment environment;

    private final AppConfig appConfig;

    public UserController(UserRepository repository, IOrderClient orderClient, Environment environment, AppConfig appConfig) {
        this.repository = repository;
        this.orderClient = orderClient;
        this.environment = environment;
        this.appConfig = appConfig;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all the users");
        final List<UserInfo> userInfos = this.repository.findAll();
        return Utils.getUsers(userInfos);
    }

    @GetMapping("/config")
    public AppConfig getConfigInfo() {
        log.info("Fetching the config info");
        return this.appConfig;
    }

    @GetMapping("/{id}/orders")
    public User getUserWithOrders(final @PathVariable Long id) {
        List<Order> orders = this.orderClient.getOrdersByUserId(id);
        final Optional<UserInfo> userInfo = this.repository.findById(id);
        if (userInfo.isPresent()) {
            User user = Utils.getUser(userInfo.get());
            user.setOrders(orders);
            return user;
        } else {
            throw new DataNotFoundException();
        }
    }

    @GetMapping("/{id}")
    public User getById(final @PathVariable Long id) {
        log.info("Getting the users by id");
        Optional<UserInfo> userInfo = this.repository.findById(id);
        if (userInfo.isPresent()) {
            return Utils.getUser(userInfo.get());
        } else {
            throw new DataNotFoundException();
        }
    }

    @PostMapping
    public User addUser(final @RequestBody UserInfo body) {
        log.info("Saving the new user");
        final UserInfo userInfo = this.repository.save(body);
        return Utils.getUser(userInfo);
    }

    @PutMapping("/{id}")
    public User updateUser(final @RequestBody UserInfo userInfo, final @PathVariable Long id) {
        log.info("Fetching the user by id");
        Optional<UserInfo> fetchedUser = this.repository.findById(id);

        log.info("Updating the user identified by the id");
        if (fetchedUser.isPresent()) {
            userInfo.setId(id);
            final UserInfo userInfoSaved = this.repository.save(userInfo);
            return Utils.getUser(userInfoSaved);
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
