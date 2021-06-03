package com.example.todo_again.demo.repository;

import com.example.todo_again.demo.model.TodoAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TodoAppUser, Long> {

    Optional<TodoAppUser> findByUsername(String username);

}
