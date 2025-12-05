package com.example.courseproject.service;

import com.example.courseproject.dto.UserResponseDto;
import com.example.courseproject.dto.UserRequestDto;
import com.example.courseproject.model.User;
import com.example.courseproject.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Теория: RequestDTO → Entity → save → ResponseDTO
    public UserResponseDto registerPatient(UserRequestDto requestDto) {
        User user = new User();
        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword()); // Позже хешировать!
        user.setPhone(requestDto.getPhone());
        user.setRole("PATIENT");

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    public UserResponseDto createDoctor(UserRequestDto requestDto) {
        User user = new User();
        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setPhone(requestDto.getPhone());
        user.setRole("DOCTOR");

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    public UserResponseDto createAdmin(UserRequestDto requestDto) {
        User user = new User();
        user.setFullName(requestDto.getFullName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setPhone(requestDto.getPhone());
        user.setRole("ADMIN");

        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    // Теория: конвертация Entity → ResponseDTO (скрываем password)
    private UserResponseDto mapToResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }

    // Теория: возвращаем готовый список ResponseDTO
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

}

