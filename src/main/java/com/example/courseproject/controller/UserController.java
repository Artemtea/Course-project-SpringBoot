package com.example.courseproject.controller;

import com.example.courseproject.model.User;
import com.example.courseproject.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // регистрация пациента
    @PostMapping("/register")
    public User registerPatient(@Valid @RequestBody User user) {
        user.setRole("PATIENT");
        return userRepository.save(user);
    }

    // создание врача
    @PostMapping("/doctors")
    public User createDoctor(@Valid @RequestBody User user) {
        user.setRole("DOCTOR");
        return userRepository.save(user);
    }

    // создание администратора (пока без защиты)
    @PostMapping("/admin")
    public User createAdmin(@Valid @RequestBody User user) {
        user.setRole("ADMIN");
        return userRepository.save(user);
    }

    // временно: посмотреть всех пользователей
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
