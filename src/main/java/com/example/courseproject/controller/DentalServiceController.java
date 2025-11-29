package com.example.courseproject.controller;

import com.example.courseproject.model.DentalService;
import com.example.courseproject.repository.DentalServiceRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/services")
public class DentalServiceController {

    private final DentalServiceRepository dentalServiceRepository;

    public DentalServiceController(DentalServiceRepository dentalServiceRepository) {
        this.dentalServiceRepository = dentalServiceRepository;
    }

    // получить все услуги
    @GetMapping
    public List<DentalService> getAll() {
        return dentalServiceRepository.findAll();
    }

    // создать услугу
    @PostMapping
    public DentalService create(@RequestBody DentalService service) {
        return dentalServiceRepository.save(service);
    }

    // обновление услуги
    @PutMapping("/{id}")
    public DentalService update(
            @PathVariable Long id,
            @Valid @RequestBody DentalService updated
    ) {
        DentalService existing = dentalServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());

        return dentalServiceRepository.save(existing);
    }

    // удаление услуги
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dentalServiceRepository.deleteById(id);
    }

}
