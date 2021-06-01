package com.example.todo_again.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty   // todo: not empty...
    @Column(nullable = false, unique = true)
    private String title;

    private Status status;
    private static int _idCounter = 0;
}
