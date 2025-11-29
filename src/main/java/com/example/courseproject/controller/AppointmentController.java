package com.example.courseproject.controller;

import com.example.courseproject.model.Appointment;
import com.example.courseproject.repository.AppointmentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // 1. Создать запись на приём
    @PostMapping
    public Appointment createAppointment(@Valid @RequestBody Appointment appointment) {
        // пока без дополнительной логики
        appointment.setStatus("NEW");
        return appointmentRepository.save(appointment);
    }

    // 2. Получить все записи (для проверки, потом можно ограничить)
    @GetMapping
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    // 3. Получить записи по пациенту
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // 4. Получить записи по врачу
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // отменить запись
    @PatchMapping("/{id}/cancel")
    public Appointment cancelAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("CANCELED");
        return appointmentRepository.save(appointment);
    }

    @GetMapping("/doctor/{doctorId}/free-slots")
    public List<LocalTime> getFreeSlots(
            @PathVariable Long doctorId,
            @RequestParam LocalDate date
    ) {
        // 10:00–18:00, шаг 30 минут
        LocalDateTime start = date.atTime(10, 0);
        LocalDateTime end = date.atTime(18, 0);

        List<Appointment> appointments =
                appointmentRepository.findByDoctorIdAndAppointmentDateTimeBetween(doctorId, start, end);

        // список занятых времён
        Set<LocalTime> busyTimes = appointments.stream()
                .map(a -> a.getAppointmentDateTime().toLocalTime())
                .collect(Collectors.toSet());

        // генерируем все слоты
        List<LocalTime> result = new ArrayList<>();
        LocalTime time = LocalTime.of(10, 0);
        while (!time.isAfter(LocalTime.of(18, 0))) {
            if (!busyTimes.contains(time)) {
                result.add(time);
            }
            time = time.plusMinutes(30);
        }

        return result;
    }


}
