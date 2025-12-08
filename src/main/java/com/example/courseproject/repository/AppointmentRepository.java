package com.example.courseproject.repository;

import com.example.courseproject.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // найти все записи по id пациента
    List<Appointment> findByPatientId(Long patientId);

    // найти все записи по id врача
    List<Appointment> findByDoctorId(Long doctorId);

    Long countByDoctorId(Long doctorId);

    List<Appointment> findByDoctorIdAndAppointmentDateTimeBetween(
            Long doctorId,
            LocalDateTime start,
            LocalDateTime end
    );
}


