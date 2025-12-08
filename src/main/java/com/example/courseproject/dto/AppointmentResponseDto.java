package com.example.courseproject.dto;

import lombok.Data;

import java.time.LocalDateTime;

// Теория: DTO для ОТВЕТА по записи на приём
@Data
public class AppointmentResponseDto {

    private Long id;                      // ID записи
    private LocalDateTime appointmentDateTime; // Дата и время приёма
    private String status;                // NEW / CANCELED / DONE

    private Long patientId;               // ID пациента
    private String patientName;           // ФИО пациента (по желанию)

    private Long doctorId;                // ID врача
    private String doctorName;            // ФИО врача

    private Long serviceId;               // ID услуги
    private String serviceTitle;          // Название услуги
}
