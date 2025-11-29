package com.example.courseproject.repository;

import com.example.courseproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // пока ничего не добавляем, хватит базовых методов
}
