package com.smuralee.service;

import com.smuralee.entity.Todo;
import com.smuralee.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @GetMapping
    public List<Todo> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    public Todo getById(final @PathVariable Long id) {
        Optional<Todo> Todo = this.repository.findById(id);
        return Todo.get();
    }

    @PostMapping
    public Todo addTodo(final @RequestBody Todo Todo) {
        return this.repository.save(Todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(final @RequestBody Todo Todo, final @PathVariable Long id) {
        Optional<Todo> fetchedTodo = this.repository.findById(id);
        Todo updatedTodo = null;
        if (fetchedTodo.isPresent()) {
            Todo.setId(id);
            updatedTodo = this.repository.save(Todo);
        }
        return updatedTodo;
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}
