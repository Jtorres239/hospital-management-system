package com.hospital.patient.service;

import com.hospital.patient.entity.Patient;
import com.hospital.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

    public Patient createPatient(Patient patient) {
        if (patientRepository.existsByEmail(patient.getEmail())) {
            throw new RuntimeException("Patient already exists with email: " + patient.getEmail());
        }
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existing = getPatientById(id);
        existing.setFirstName(updatedPatient.getFirstName());
        existing.setLastName(updatedPatient.getLastName());
        existing.setPhoneNumber(updatedPatient.getPhoneNumber());
        existing.setDateOfBirth(updatedPatient.getDateOfBirth());
        existing.setGender(updatedPatient.getGender());
        existing.setMedicalHistory(updatedPatient.getMedicalHistory());
        existing.setAddress(updatedPatient.getAddress());
        return patientRepository.save(existing);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    public List<Patient> searchByLastName(String lastName) {
        return patientRepository.findByLastNameContainingIgnoreCase(lastName);
    }
}
