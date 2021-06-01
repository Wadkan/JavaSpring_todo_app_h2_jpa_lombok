package com.example.todo_again.demo.repository;

import com.example.todo_again.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
