package com.smuralee.service;

import com.smuralee.entity.InstanceInfo;
import com.smuralee.entity.User;
import com.smuralee.errors.DataNotFoundException;
import com.smuralee.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    private final RestTemplate restTemplate;

    public UserController(UserRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all the users");
        return this.repository.findAll();
    }

    @GetMapping("/info")
    public InstanceInfo getInfo() throws IOException {
        log.info("Fetching the instance info");
        InstanceInfo instanceInfo = new InstanceInfo();
        InetAddress localhost = InetAddress.getLocalHost();

        instanceInfo.setHostIpAddress(localhost.getHostAddress());
        instanceInfo.setHostname(localhost.getHostName());

        URL url = new URL("http://bot.whatismyipaddress.com");
        BufferedReader sc = new BufferedReader(new InputStreamReader(url.openStream()));
        instanceInfo.setPublicIpAddress(sc.readLine().trim());

        return instanceInfo;
    }

    @GetMapping("/todos")
    public String getTodos() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return this.restTemplate.exchange("http://todos-api:9002/todos", HttpMethod.GET, entity, String.class).getBody();
    }

    @GetMapping("/product-orders")
    public String getProductOrders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return this.restTemplate.exchange("http://product-orders-api:9001/product-orders", HttpMethod.GET, entity, String.class).getBody();
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
