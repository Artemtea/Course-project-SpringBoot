package com.example.courseproject.service;

import com.example.courseproject.dto.AppointmentRequestDto;
import com.example.courseproject.model.Appointment;
import com.example.courseproject.model.DentalService;
import com.example.courseproject.model.User;
import com.example.courseproject.repository.AppointmentRepository;
import com.example.courseproject.repository.DentalServiceRepository;
import com.example.courseproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.courseproject.dto.AppointmentResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DentalServiceRepository dentalServiceRepository;

    // Через конструктор Spring подставит нужные репозитории
    public AppointmentManager(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              DentalServiceRepository dentalServiceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.dentalServiceRepository = dentalServiceRepository;
    }

    public AppointmentResponseDto createAppointment(AppointmentRequestDto dto) {
        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        DentalService service = dentalServiceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setService(service);
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setStatus("NEW");

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    public List<AppointmentResponseDto> getAppointmentsForCurrentPatient(Long patientId) {
        // 1. Получаем текущего залогиненного пользователя
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName(); // username = email

        // 2. Находим пользователя по email
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // 3. Проверяем, что это пациент и что он запрашивает СВОЙ id
        if (!"PATIENT".equals(currentUser.getRole()) || !currentUser.getId().equals(patientId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        // 4. Возвращаем только его записи
        return appointmentRepository.findByPatientId(patientId).stream()
                .map(this::mapToResponse) // твой метод маппинга в AppointmentResponseDto
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDto> getAppointmentsForCurrentDoctor(Long doctorId) {
        // 1. Текущий пользователь из SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName();

        // 2. Находим пользователя по email
        User currentUser = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        // 3. Проверяем роль и id
        if (!"DOCTOR".equals(currentUser.getRole()) || !currentUser.getId().equals(doctorId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied");
        }

        // 4. Возвращаем только его приёмы
        return appointmentRepository.findByDoctorId(doctorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // Отмена записи: меняем статус на CANCELED
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("CANCELED");
        appointmentRepository.save(appointment);
    }

    // Теория: преобразуем сущность Appointment в удобный DTO для ответа
    private AppointmentResponseDto mapToResponse(Appointment appointment) {
        AppointmentResponseDto dto = new AppointmentResponseDto();
        dto.setId(appointment.getId());
        dto.setAppointmentDateTime(appointment.getAppointmentDateTime());
        dto.setStatus(appointment.getStatus());

        if (appointment.getPatient() != null) {
            dto.setPatientId(appointment.getPatient().getId());
            dto.setPatientName(appointment.getPatient().getFullName());
        }

        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
            dto.setDoctorName(appointment.getDoctor().getFullName());
        }

        if (appointment.getService() != null) {
            dto.setServiceId(appointment.getService().getId());
            dto.setServiceTitle(appointment.getService().getTitle());
        }

        return dto;
    }
}
