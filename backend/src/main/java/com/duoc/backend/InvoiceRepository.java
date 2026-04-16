package com.duoc.backend;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
    List<Invoice> findByAppointmentId(Integer appointmentId);
}
