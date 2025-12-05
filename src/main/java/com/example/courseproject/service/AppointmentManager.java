package com.example.courseproject.service;

import com.example.courseproject.dto.AppointmentRequestDto;
import com.example.courseproject.model.Appointment;
import com.example.courseproject.model.DentalService;
import com.example.courseproject.model.User;
import com.example.courseproject.repository.AppointmentRepository;
import com.example.courseproject.repository.DentalServiceRepository;
import com.example.courseproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentManager {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DentalServiceRepository dentalServiceRepository;

    // Через конструктор Spring подставит нужные репозитории
    public AppointmentManager(AppointmentRepository appointmentRepository,
                              UserRepository userRepository,
                              DentalServiceRepository dentalServiceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.dentalServiceRepository = dentalServiceRepository;
    }

    // Создание записи: берём ID из DTO, находим сущности и сохраняем Appointment
    public Appointment createAppointment(AppointmentRequestDto dto) {
        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User doctor = userRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        DentalService service = dentalServiceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setService(service);
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setStatus("NEW");

        return appointmentRepository.save(appointment);
    }

    // Отмена записи: меняем статус на CANCELED
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("CANCELED");
        appointmentRepository.save(appointment);
    }

    // Все записи пациента
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    // Все записи врача
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}
