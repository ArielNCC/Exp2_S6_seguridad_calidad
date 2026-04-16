package com.duoc.backend;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(Integer patientId);
}