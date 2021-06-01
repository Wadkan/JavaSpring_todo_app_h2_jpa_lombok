package com.example.todo_again.demo.repository;

import com.example.todo_again.demo.model.Status;
import com.example.todo_again.demo.model.Todo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AllRepositoryTest {
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

}