package com.example.courseproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

// Теория: DTO для ВХОДА (регистрация, создание). Нет ID, нет пароля в ответе.
@Data
public class UserRequestDto {

    @NotBlank // Валидация: поле обязательно
    private String fullName;

    @NotBlank
    @Email    // Проверка формата email
    private String email;

    @NotBlank
    @Size(min = 6) // Минимум 6 символов
    private String password;

    @NotBlank
    private String phone;
}
