package com.example.courseproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @NotNull(message = "Patient is required")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @NotNull(message = "Doctor is required")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "service_id")
    @NotNull(message = "Service is required")
    private DentalService service;

    @NotNull(message = "Appointment date/time is required")
    @FutureOrPresent(message = "Appointment date/time cannot be in the past")
    private LocalDateTime appointmentDateTime;

    private String status;
}
