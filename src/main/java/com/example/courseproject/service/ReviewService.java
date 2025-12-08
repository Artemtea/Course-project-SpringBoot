package com.example.courseproject.service;

import com.example.courseproject.dto.ReviewRequestDto;
import com.example.courseproject.dto.ReviewResponseDto;
import com.example.courseproject.model.Appointment;
import com.example.courseproject.model.Review;
import com.example.courseproject.repository.AppointmentRepository;
import com.example.courseproject.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

// Сервис (менеджер) для работы с отзывами
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         AppointmentRepository appointmentRepository) {
        this.reviewRepository = reviewRepository;
        this.appointmentRepository = appointmentRepository;
    }

    // Создать отзыв по приёму
    public ReviewResponseDto createReview(Long appointmentId, ReviewRequestDto dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setAppointment(appointment);

        Review saved = reviewRepository.save(review);
        return mapToResponse(saved);
    }

    // Все отзывы по врачу
    public List<ReviewResponseDto> getReviewsByDoctor(Long doctorId) {
        return reviewRepository.findByAppointment_Doctor_Id(doctorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Отзыв по конкретной записи
    public ReviewResponseDto getByAppointment(Long appointmentId) {
        Review review = reviewRepository.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));
        return mapToResponse(review);
    }

    // Маппинг Entity -> DTO
    private ReviewResponseDto mapToResponse(Review review) {
        ReviewResponseDto dto = new ReviewResponseDto();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());

        if (review.getAppointment() != null) {
            dto.setAppointmentId(review.getAppointment().getId());

            if (review.getAppointment().getDoctor() != null) {
                dto.setDoctorId(review.getAppointment().getDoctor().getId());
                dto.setDoctorName(review.getAppointment().getDoctor().getFullName());
            }

            if (review.getAppointment().getPatient() != null) {
                dto.setPatientId(review.getAppointment().getPatient().getId());
                dto.setPatientName(review.getAppointment().getPatient().getFullName());
            }
        }

        return dto;
    }
}
