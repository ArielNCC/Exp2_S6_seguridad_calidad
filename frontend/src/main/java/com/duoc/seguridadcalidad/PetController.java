package com.duoc.seguridadcalidad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetController {

    @GetMapping("/pets")
    public String listPets() {
        return "pets";
    }

    @GetMapping("/pets/new")
    public String showCreateForm() {
        return "new_pet";
    }
}
