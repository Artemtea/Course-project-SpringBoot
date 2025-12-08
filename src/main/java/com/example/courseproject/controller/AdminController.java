package com.example.courseproject.controller;

import com.example.courseproject.dto.UserResponseDto;
import com.example.courseproject.model.User;
import com.example.courseproject.repository.AppointmentRepository;
import com.example.courseproject.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public AdminController(UserRepository userRepository,
                           AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // 1. Список всех врачей
    @GetMapping("/doctors")
    public List<UserResponseDto> getAllDoctors() {
        return userRepository.findAll().stream()
                .filter(u -> "DOCTOR".equals(u.getRole()))
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // 2. Обновить данные врача (ФИО, телефон, email)
    @PatchMapping("/doctors/{id}")
    public UserResponseDto updateDoctor(@PathVariable Long id,
                                        @RequestBody UserResponseDto updateDto) {
        User doctor = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (updateDto.getFullName() != null) {
            doctor.setFullName(updateDto.getFullName());
        }
        if (updateDto.getEmail() != null) {
            doctor.setEmail(updateDto.getEmail());
        }
        if (updateDto.getPhone() != null) {
            doctor.setPhone(updateDto.getPhone());
        }
        // Роль специально не даём менять через этот метод

        User saved = userRepository.save(doctor);
        return mapToDto(saved);
    }

    // 3. Простая статистика по врачу: количество записей
    @GetMapping("/stats/doctors/{doctorId}")
    public Long getDoctorAppointmentsCount(@PathVariable Long doctorId) {
        return appointmentRepository.countByDoctorId(doctorId);
    }



    // Вспомогательный метод для преобразования в DTO
    private UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }
}
