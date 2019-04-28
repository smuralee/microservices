package com.smuralee.service;

import com.smuralee.entity.Todo;
import com.smuralee.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public Todo getById(final @PathVariable Long id) {
        log.info("Getting the todos by id");
        Optional<Todo> Todo = this.repository.findById(id);
        return Todo.get();
    }

    @PostMapping
    public Todo addTodo(final @RequestBody Todo Todo) {
        log.info("Saving the new todo");
        return this.repository.save(Todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(final @RequestBody Todo Todo, final @PathVariable Long id) {
        log.info("Fetching the todo by id");
        Optional<Todo> fetchedTodo = this.repository.findById(id);
        Todo updatedTodo = null;
        if (fetchedTodo.isPresent()) {
            Todo.setId(id);
            updatedTodo = this.repository.save(Todo);
        }
        log.info("Updated the todo identified by the id");
        return updatedTodo;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        log.info("Deleting the todo by id");
        this.repository.deleteById(id);
    }
}
