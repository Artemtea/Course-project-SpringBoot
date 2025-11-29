package com.example.courseproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;              // ID отзыва

    private int rating;           // Оценка 1–5
    private String comment;       // Текст отзыва

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment; // К какому приёму относится отзыв
}


