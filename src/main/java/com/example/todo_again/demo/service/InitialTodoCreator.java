package com.example.todo_again.demo.service;

import com.example.todo_again.demo.model.Status;
import com.example.todo_again.demo.model.Todo;
import com.example.todo_again.demo.repository.TodoRepository;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitialTodoCreator {
    @Autowired
    TodoRepository todoRepository;

    public void createInitialTodos() {
        Todo todo = Todo.builder()
                .status(Status.ACTIVE)
                .title("First todo")
                .build();

        Todo todo2 = Todo.builder()
                .status(Status.ACTIVE)
                .title("Second todo")
                .build();

        Todo todo3 = Todo.builder()
                .status(Status.ACTIVE)
                .title("Third todo")
                .build();

        Todo todo4 = Todo.builder()
                .status(Status.ACTIVE)
                .title("Forth todo")
                .build();
        todoRepository.saveAll(Lists.newArrayList(todo, todo2, todo3, todo4));

    }
}
