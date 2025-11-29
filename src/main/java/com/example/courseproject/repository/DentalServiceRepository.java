package com.example.courseproject.repository;

import com.example.courseproject.model.DentalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DentalServiceRepository extends JpaRepository<DentalService, Long> {
    // пока дополнительных методов не нужно
}
