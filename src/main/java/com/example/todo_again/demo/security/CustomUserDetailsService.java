package com.example.todo_again.demo.security;

import com.example.todo_again.demo.model.TodoAppUser;
import com.example.todo_again.demo.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/*
 There must be a class that implements UserDetailsService otherwise Spring will create a default
 UserDetailsService (an instance of InMemoryUserDetailsManager, see UserDetailsServiceAutoConfiguration) that does not
 know where to find users for authentication.
*/
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository users;

    public CustomUserDetailsService(UserRepository users) {
        this.users = users;
    }

    /**
     * Loads the user from the DB and converts it to Spring Security's internal User oobject
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TodoAppUser user = users.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));

        return new User(user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
