package com.example.todo_again.demo.controller;

import com.example.todo_again.demo.model.Status;
import com.example.todo_again.demo.model.Todo;
import com.example.todo_again.demo.repository.TodoRepository;
import com.example.todo_again.demo.service.InitialTodoCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    InitialTodoCreator initialTodoCreator;

    private static final String SUCCESS = "{\"success\":true}";

    @PostMapping("/init")
    public List<Todo> init() {
        initialTodoCreator.createInitialTodos();
        return todoRepository.findAll();
    }

    @PostMapping("/addTodo")
    public Todo addTodo(@RequestParam("todo-title") String title) {
        Todo newTodo = Todo.builder()
                .title(title)
                .status(Status.ACTIVE)
                .build();
        todoRepository.save(newTodo);
        return newTodo;
    }

    @PostMapping("/list")
    public List<Todo> getTodoListByStatus(@RequestParam("status") String status_param) {
        List<Todo> todoList;
        if (status_param == "") {
            todoList = todoRepository.findAll();
        } else {
            todoList = todoRepository.findAll()
                    .stream().filter(todo -> todo.getStatus().toString().toLowerCase().equals(status_param))
                    .collect(Collectors.toList());
        }

        // set completed field
        for (Todo todo : todoList) {
            todo.fillCompletedField();
        }

        return todoList;
    }

    @Transactional
    @DeleteMapping("/todos/completed")
    public String removeCompleted() {
        todoRepository.deleteTodoByStatus(Status.COMPLETE);
        return SUCCESS;
    }

    @PutMapping("/todos/toggle_all")
    public String toggleAllStatus(@RequestParam("toggle-all") String complete) {
        Status newStatus = (complete.equals("true")) ? Status.COMPLETE : Status.ACTIVE;
        System.out.println(newStatus);

        List<Todo> todoList = todoRepository.findAll();
        for (Todo todo : todoList) {
            todo.setStatus(newStatus);
        }
        todoRepository.saveAllAndFlush(todoList);
        return SUCCESS;
    }

    @PutMapping("/todos/{id}")
    public String updateById(@PathVariable("id") Long id, @RequestParam("title") String title) {
        Todo todo = todoRepository.findById(id).stream().findFirst().orElseThrow();
        todo.setTitle(title);
        todoRepository.saveAndFlush(todo);
        return SUCCESS;
    }

    @GetMapping("/todos/{id}")
    public Todo fbi(@PathVariable("id") Long id) {
        return todoRepository.findById(id).stream().findFirst().orElseThrow();
    }

    @PutMapping("/todos/{id}/toggle_status")
    public String toggleStatusById(@PathVariable("id") Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow();
        todo.setStatus(todo.getStatus().equals(Status.COMPLETE) ? Status.ACTIVE : Status.COMPLETE);
        todoRepository.saveAndFlush(todo);

        return SUCCESS;
    }
}
