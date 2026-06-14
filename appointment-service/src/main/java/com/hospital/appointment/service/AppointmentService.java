package com.hospital.appointment.service;

import com.hospital.appointment.entity.Appointment;
import com.hospital.appointment.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = getAppointmentById(id);
        existing.setAppointmentDate(updated.getAppointmentDate());
        existing.setReason(updated.getReason());
        existing.setNotes(updated.getNotes());
        existing.setStatus(updated.getStatus());
        return appointmentRepository.save(existing);
    }

    public Appointment updateStatus(Long id, Appointment.Status status) {
        Appointment existing = getAppointmentById(id);
        existing.setStatus(status);
        return appointmentRepository.save(existing);
    }

    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByStatus(Appointment.Status status) {
        return appointmentRepository.findByStatus(status);
    }
}
