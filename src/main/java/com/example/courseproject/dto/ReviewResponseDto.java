package com.example.courseproject.dto;

import lombok.Data;

// DTO для ответа по отзыву (то, что отдаём наружу)
@Data
public class ReviewResponseDto {

    private Long id;
    private int rating;
    private String comment;

    private Long appointmentId;

    private Long doctorId;
    private String doctorName;

    private Long patientId;
    private String patientName;
}
