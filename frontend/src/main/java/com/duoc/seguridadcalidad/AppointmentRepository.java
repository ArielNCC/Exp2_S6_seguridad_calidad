package com.duoc.seguridadcalidad;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AppointmentRepository {

    private final List<Appointment> appointments = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(1);

    public List<Appointment> findAll() {
        return new ArrayList<>(appointments);
    }

    public Appointment save(Appointment appointment) {
        if (appointment.getId() == null) {
            appointment.setId(sequence.getAndIncrement());
        }
        appointments.add(appointment);
        return appointment;
    }
}
