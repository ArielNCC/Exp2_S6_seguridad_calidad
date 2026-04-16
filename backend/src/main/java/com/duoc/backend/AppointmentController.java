package com.duoc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            if (appointment.getPatientId() == null || !patientRepository.existsById(appointment.getPatientId())) {
                return ResponseEntity.badRequest().body("Patient not found for patientId: " + appointment.getPatientId());
            }
            appointmentRepository.save(appointment);
            return ResponseEntity.status(HttpStatus.CREATED).body(appointment);

            //return ResponseEntity.ok("Appointment scheduled successfully");
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al registrar: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
            //return ResponseEntity.badRequest().body("Error scheduling appointment: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Appointment>> getAllAppointments() {
        try {
            List<Appointment> appointments = (List<Appointment>) appointmentRepository.findAll();
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        try {
            Optional<Appointment> appointment = appointmentRepository.findById(id);
            return appointment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsForPatient(@PathVariable Integer patientId) {
        try {
            if (!patientRepository.existsById(patientId)) {
                return ResponseEntity.notFound().build();
            }
            List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable Integer id, @RequestBody Appointment appointment) {
        try {
            if (!appointmentRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            if (appointment.getPatientId() == null || !patientRepository.existsById(appointment.getPatientId())) {
                return ResponseEntity.badRequest().body("Patient not found for patientId: " + appointment.getPatientId());
            }
            appointment.setId(id);
            appointmentRepository.save(appointment);
            return ResponseEntity.ok("Appointment updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating appointment: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable Integer id) {
        try {
            if (!appointmentRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            appointmentRepository.deleteById(id);
            return ResponseEntity.ok("Appointment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting appointment: " + e.getMessage());
        }
    }
}
