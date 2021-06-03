package com.example.todo_again.demo;

import com.example.todo_again.demo.model.TodoAppUser;
import com.example.todo_again.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {


    private final UserRepository users;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository users) {
        this.users = users;
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        log.debug("initializing sample data...");

        users.save(TodoAppUser.builder()
                .username("wadkan")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER"))
                .build()
        );

        users.save(TodoAppUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build()
        );
        log.debug("printing all users...");
        users.findAll().forEach(v -> log.debug(" VehicleAppUser :" + v.toString()));
    }
}
