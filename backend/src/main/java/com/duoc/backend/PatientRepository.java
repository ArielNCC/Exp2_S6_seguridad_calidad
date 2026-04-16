package com.duoc.backend;

import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, Integer> {
    // Additional query methods can be added here if needed
}