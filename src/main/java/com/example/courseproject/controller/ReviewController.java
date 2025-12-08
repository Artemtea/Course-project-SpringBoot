package com.example.courseproject.controller;

import com.example.courseproject.dto.ReviewRequestDto;
import com.example.courseproject.dto.ReviewResponseDto;
import com.example.courseproject.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.courseproject.repository.AppointmentRepository; // если нужно
import java.util.List;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService,
                            AppointmentRepository appointmentRepository) {
        this.reviewService = reviewService;
    }

    // Оставить отзыв по приёму
    @PostMapping("/{appointmentId}")
    public ReviewResponseDto createReview(@PathVariable Long appointmentId,
                                          @Valid @RequestBody ReviewRequestDto dto) {
        return reviewService.createReview(appointmentId, dto);
    }

    // Все отзывы по врачу
    @GetMapping("/doctor/{doctorId}")
    public List<ReviewResponseDto> getByDoctor(@PathVariable Long doctorId) {
        return reviewService.getReviewsByDoctor(doctorId);
    }

    // Отзыв по конкретному приёму
    @GetMapping("/appointment/{appointmentId}")
    public ReviewResponseDto getByAppointment(@PathVariable Long appointmentId) {
        return reviewService.getByAppointment(appointmentId);
    }
}
