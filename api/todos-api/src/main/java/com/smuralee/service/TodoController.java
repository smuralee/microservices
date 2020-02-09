package com.smuralee.service;

import com.smuralee.entity.InstanceInfo;
import com.smuralee.entity.Todo;
import com.smuralee.errors.DataNotFoundException;
import com.smuralee.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @GetMapping
    public List<Todo> getAll() {
        log.info("Getting all the todos");
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

    @GetMapping("/{id}")
    public Todo getById(final @PathVariable Long id) {
        log.info("Getting the todos by id");
        Optional<Todo> todo = this.repository.findById(id);
        return todo.orElseThrow(DataNotFoundException::new);
    }

    @PostMapping
    public Todo addTodo(final @RequestBody Todo todo) {
        log.info("Saving the new todo");
        return this.repository.save(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(final @RequestBody Todo todo, final @PathVariable Long id) {
        log.info("Fetching the todo by id");
        Optional<Todo> fetchedTodo = this.repository.findById(id);

        log.info("Updating the todo identified by the id");
        if (fetchedTodo.isPresent()) {
            todo.setId(id);
            return this.repository.save(todo);
        } else {
            throw new DataNotFoundException();
        }
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting the todo by id");
        this.repository.deleteById(id);
    }
}
