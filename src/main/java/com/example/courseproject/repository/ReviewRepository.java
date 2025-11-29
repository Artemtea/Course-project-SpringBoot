package com.example.courseproject.repository;

import com.example.courseproject.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Все отзывы по врачу
    List<Review> findByAppointment_Doctor_Id(Long doctorId);

    // Отзыв по конкретной записи
    Optional<Review> findByAppointment_Id(Long appointmentId);
}
