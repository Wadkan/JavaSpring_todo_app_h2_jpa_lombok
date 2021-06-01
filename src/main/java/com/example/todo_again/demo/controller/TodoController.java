package com.example.todo_again.demo.controller;

import com.example.todo_again.demo.model.Status;
import com.example.todo_again.demo.model.Todo;
import com.example.todo_again.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    private static final String SUCCESS = "{\"success\":true}";

    @PostMapping("/addTodo")
    public Todo addTodo(@RequestBody @Valid Todo todo) {
        Todo newTodo = Todo.builder()
                .title(todo.getTitle())
                .status(Status.ACTIVE)
                .build();
        todoRepository.save(newTodo);
        return newTodo;
    }

    @GetMapping("/list")
    public List<Todo> getTodoListByStatus(@RequestParam("status") Status status) {
        List<Todo> todoList = todoRepository.findAll()
                .stream().filter(todo -> todo.getStatus().equals(status))
                .collect(Collectors.toList());
        return todoList;
    }

    @DeleteMapping("/todos/completed")
    public String removeCompleted() {
        return SUCCESS;
    }

}
