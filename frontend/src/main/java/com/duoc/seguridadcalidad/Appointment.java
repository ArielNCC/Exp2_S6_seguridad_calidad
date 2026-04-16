package com.duoc.seguridadcalidad;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private Long id;
    private Long patientId;
    private LocalDate date;
    private LocalTime time;
    private String reason;
    private String veterinarian;

    public Appointment() {
    }

    public Appointment(Long id, Long patientId, LocalDate date, LocalTime time, String reason, String veterinarian) {
        this.id = id;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.veterinarian = veterinarian;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getVeterinarian() {
        return veterinarian;
    }

    public void setVeterinarian(String veterinarian) {
        this.veterinarian = veterinarian;
    }
}
