package com.example.todo_again.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
This class configures Spring Security.
By default Spring Security locks down all your endpoints and one must use HTTP Basic authentication to access anything,
we need to override this with our configuration.
*/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenServices jwtTokenServices;

    public SecurityConfig(JwtTokenServices jwtTokenServices) {
        this.jwtTokenServices = jwtTokenServices;
    }

    // By overriding this function and
    // adding the @Bean annotation we can inject the AuthenticationManager into other classes.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // The main point for configuring Spring Security.
    // In Spring Security every request goes trough a chain of filters; each filter checks the request for something
    // and if one fails the request will fail.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // By default Spring Security uses HTTP Basic authentication, we disable this filter.
                .csrf().disable() // Disable CSRF. Leaving it enabled would ignore GET, HEAD, TRACE, OPTIONS
                // Disable Tomcat's session management. This causes HttpSession to be null and no session cookie to be created
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/auth/signin")
                .defaultSuccessUrl("/index.html", true)
                .failureUrl("/login.html?error=true")
                .and()
                
                .authorizeRequests() // restrict access based on the config below:
                .antMatchers("/login").permitAll() // allowed by anyone
                .antMatchers("/auth/signin").permitAll() // allowed by anyone
                .antMatchers("/**").permitAll() // allowed by anyone

                .antMatchers("/list").authenticated() // allowed only when signed in
                .antMatchers(HttpMethod.POST, "/addTodo").authenticated() // allowed if signed in with ADMIN role
                .antMatchers(HttpMethod.DELETE, "/todos/completed").authenticated() // allowed if signed in with ADMIN role
                .antMatchers(HttpMethod.PUT, "/todos/toggle_all").authenticated() // allowed if signed in with ADMIN role
                .antMatchers(HttpMethod.PUT, "/todos/*").authenticated() // allowed if signed in with ADMIN role
                .antMatchers(HttpMethod.GET, "/todos/*").authenticated() // allowed if signed in with ADMIN role
                .antMatchers(HttpMethod.PUT, "/todos/*/toggle_status").authenticated() // allowed if signed in with ADMIN role

                .antMatchers(HttpMethod.POST, "/init").authenticated() // allowed only when signed in

                .anyRequest().denyAll() // anything else is denied; this is a safeguard in case we left something out.
                .and()
                // Here we define our custom filter that uses the JWT tokens for authentication.
                .addFilterBefore(new JwtTokenFilter(jwtTokenServices), UsernamePasswordAuthenticationFilter.class);
    }
}

