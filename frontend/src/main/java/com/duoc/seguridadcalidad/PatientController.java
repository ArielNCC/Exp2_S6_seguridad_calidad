package com.duoc.seguridadcalidad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PatientController {

    @GetMapping("/patients")
    public String listPatients() {
        return "patients";
    }

    @GetMapping("/patients/new")
    public String showCreateForm() {
        return "new_patient";
    }

    @PostMapping("/patients")
    public String savePatient() {
        return "redirect:/patients";
    }
}
