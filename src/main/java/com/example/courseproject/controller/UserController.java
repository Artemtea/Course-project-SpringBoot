package com.example.courseproject.controller;

import com.example.courseproject.dto.UserRequestDto;
import com.example.courseproject.dto.UserResponseDto;
import com.example.courseproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Теория: импортируем List для возврата списка пользователей
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponseDto registerPatient(@Valid @RequestBody UserRequestDto requestDto) {
        return userService.registerPatient(requestDto);
    }

    @PostMapping("/doctors")
    public UserResponseDto createDoctor(@Valid @RequestBody UserRequestDto requestDto) {
        return userService.createDoctor(requestDto);
    }

    @PostMapping("/admin")
    public UserResponseDto createAdmin(@Valid @RequestBody UserRequestDto requestDto) {
        return userService.createAdmin(requestDto);
    }

    // Теория: вызываем метод сервиса, который уже возвращает List<UserResponseDto>
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }
}
