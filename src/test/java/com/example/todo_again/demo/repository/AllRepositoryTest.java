package com.example.todo_again.demo.repository;

import com.example.todo_again.demo.model.Status;
import com.example.todo_again.demo.model.Todo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.security.cert.TrustAnchor;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AllRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    TodoRepository todoRepository;

    @Test
    public void sameOneSimple() {
        Todo todo = Todo.builder()
                .status(Status.ACTIVE)
                .title("First todo")
                .build();
        todoRepository.save(todo);

        assertEquals(todoRepository.findAll().size(), 1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void saveUniqueFieldTwice() {
        Todo todo = Todo.builder()
                .status(Status.ACTIVE)
                .title("First todo")
                .build();
        todoRepository.save(todo);

        Todo todo2 = Todo.builder()
                .status(Status.ACTIVE)
                .title("First todo")
                .build();
        todoRepository.saveAndFlush(todo2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void titleShouldBeNotNull() {
        Todo todo = Todo.builder()
                .status(Status.ACTIVE)
                .build();
        todoRepository.save(todo);
    }

    @Test
    public void deleteTodoByStatus() {
        Todo todo = Todo.builder()
                .status(Status.ACTIVE)
                .title("First todo")
                .build();
        todoRepository.save(todo);

        assertEquals(todoRepository.findAll().size(), 1);

        todoRepository.deleteTodoByStatus(Status.ACTIVE);
        assertEquals(todoRepository.findAll().size(), 0);
    }

    @Test
    public void deleteAllByStatus() {
        Todo todo = Todo.builder()
                .status(Status.ACTIVE)
                .title("First todo")
                .build();
        todoRepository.save(todo);

        assertEquals(todoRepository.findAll().size(), 1);

        todoRepository.deleteAllByStatus(Status.ACTIVE);
        assertEquals(todoRepository.findAll().size(), 0);
    }

}