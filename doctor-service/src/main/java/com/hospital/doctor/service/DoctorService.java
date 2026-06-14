package com.hospital.doctor.service;

import com.hospital.doctor.entity.Doctor;
import com.hospital.doctor.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public Doctor createDoctor(Doctor doctor) {
        if (doctorRepository.existsByEmail(doctor.getEmail())) {
            throw new RuntimeException("Doctor already exists with email: " + doctor.getEmail());
        }
        if (doctorRepository.existsByLicenseNumber(doctor.getLicenseNumber())) {
            throw new RuntimeException("Doctor already exists with license: " + doctor.getLicenseNumber());
        }
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Doctor existing = getDoctorById(id);
        existing.setFirstName(updatedDoctor.getFirstName());
        existing.setLastName(updatedDoctor.getLastName());
        existing.setPhoneNumber(updatedDoctor.getPhoneNumber());
        existing.setSpecialization(updatedDoctor.getSpecialization());
        existing.setYearsOfExperience(updatedDoctor.getYearsOfExperience());
        existing.setDepartment(updatedDoctor.getDepartment());
        existing.setAvailable(updatedDoctor.getAvailable());
        return doctorRepository.save(existing);
    }

    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationIgnoreCase(specialization);
    }

    public List<Doctor> getAvailableDoctors() {
        return doctorRepository.findByAvailableTrue();
    }
}
