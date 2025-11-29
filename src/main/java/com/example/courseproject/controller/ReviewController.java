package com.example.courseproject.controller;

import com.example.courseproject.model.Appointment;
import com.example.courseproject.model.Review;
import com.example.courseproject.repository.AppointmentRepository;
import com.example.courseproject.repository.ReviewRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;

    public ReviewController(ReviewRepository reviewRepository,
                            AppointmentRepository appointmentRepository) {
        this.reviewRepository = reviewRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // 1. Оставить отзыв по приёму
    @PostMapping("/{appointmentId}")
    public Review createReview(@PathVariable Long appointmentId,
                               @RequestBody Review review) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        review.setAppointment(appointment);
        return reviewRepository.save(review);
    }

    // 2. Получить все отзывы по врачу
    @GetMapping("/doctor/{doctorId}")
    public List<Review> getByDoctor(@PathVariable Long doctorId) {
        return reviewRepository.findByAppointment_Doctor_Id(doctorId);
    }

    // 3. (по желанию) Получить отзыв по конкретной записи
    @GetMapping("/appointment/{appointmentId}")
    public Review getByAppointment(@PathVariable Long appointmentId) {
        return reviewRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }
}
