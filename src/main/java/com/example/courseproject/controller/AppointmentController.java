package com.example.courseproject.controller;

import com.example.courseproject.dto.AppointmentRequestDto;
import com.example.courseproject.model.Appointment;
import com.example.courseproject.service.AppointmentManager;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments") // базовый путь для всех операций с записями
public class AppointmentController {

    private final AppointmentManager appointmentManager;

    // Через конструктор Spring подставит AppointmentManager
    public AppointmentController(AppointmentManager appointmentManager) {
        this.appointmentManager = appointmentManager;
    }

    // POST /appointments — создание записи
    @PostMapping
    public Appointment createAppointment(@Valid @RequestBody AppointmentRequestDto dto) {
        return appointmentManager.createAppointment(dto);
    }

    // PATCH /appointments/{id}/cancel — отмена записи
    @PatchMapping("/{id}/cancel")   // важно: PATCH и именно такой путь
    public void cancelAppointment(@PathVariable Long id) {
        appointmentManager.cancelAppointment(id);
    }

    // GET /appointments/patient/{patientId} — все записи конкретного пациента
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        return appointmentManager.getAppointmentsByPatientId(patientId);
    }

    // GET /appointments/doctor/{doctorId} — все записи конкретного врача
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        return appointmentManager.getAppointmentsByDoctorId(doctorId);
    }
}
