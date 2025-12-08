package com.example.courseproject.controller;

import com.example.courseproject.dto.AppointmentRequestDto;
import com.example.courseproject.dto.AppointmentResponseDto;
import com.example.courseproject.service.AppointmentManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentManager appointmentManager;

    public AppointmentController(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
    }

    // POST /appointments
    @PostMapping
    public AppointmentResponseDto createAppointment(@Valid @RequestBody AppointmentRequestDto dto) {
        return appointmentManager.createAppointment(dto);
    }

    // PATCH /appointments/{id}/cancel
    @PatchMapping("/{id}/cancel")
    public void cancelAppointment(@PathVariable Long id) {
        appointmentManager.cancelAppointment(id);
    }

    // GET /appointments/patient/{patientId}
    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponseDto> getByPatient(@PathVariable Long patientId) {
        return appointmentManager.getAppointmentsByPatientId(patientId);
    }

    // GET /appointments/doctor/{doctorId}
    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponseDto> getByDoctor(@PathVariable Long doctorId) {
        return appointmentManager.getAppointmentsByDoctorId(doctorId);
    }
}
