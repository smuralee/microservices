package com.smuralee.service;

import com.smuralee.entity.Todo;
import com.smuralee.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @GetMapping
    public List<Todo> list() {
        return this.repository.findAll();
    }
}
