package com.borak.movieApp;

import com.borak.movieApp.domain.Category;
import com.borak.movieApp.domain.Role;
import com.borak.movieApp.domain.User;
import com.borak.movieApp.service.CategoryService;
import com.borak.movieApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class MovieAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, CategoryService categoryService) {
        return args -> {

            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));

            userService.saveUser(new User(null, "admin", "admin123", new ArrayList<>()));
            userService.saveUser(new User(null, "user", "user123", new ArrayList<>()));

            userService.addRoleToUser("admin", "ROLE_ADMIN");
            userService.addRoleToUser("user", "ROLE_USER");

            categoryService.saveCategory(new Category(null, "comedy"));

        };
    }

}
