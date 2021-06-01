package com.example.todo_again.demo.repository;

import com.example.todo_again.demo.model.Status;
import com.example.todo_again.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Modifying
    @Query("DELETE from Todo t WHERE t.status = :status")
    void deleteTodoByStatus(Status status);

    void deleteAllByStatus(Status status);

    @Modifying
    @Query("UPDATE Todo t SET t.status = ?1")
    void updateAllStatus(Status status);
}
