package com.example.courseproject.dto;

import lombok.Data;

// Теория: ResponseDTO — только публичные данные, БЕЗ пароля
@Data
public class UserResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String role;
}
