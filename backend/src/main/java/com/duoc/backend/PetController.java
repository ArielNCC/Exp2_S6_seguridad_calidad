package com.duoc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @PostMapping("/pets")
    public ResponseEntity<?> createPet(@RequestBody Pet pet) {
        try {
            petRepository.save(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(pet);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al registrar mascota: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/pets")
    public ResponseEntity<Iterable<Pet>> getAllPets() {
        try {
            Iterable<Pet> pets = petRepository.findAll();
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/pets/available")
    public ResponseEntity<List<Pet>> getAvailablePets() {
        try {
            List<Pet> pets = petRepository.findByStatus("available");
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer id) {
        try {
            Optional<Pet> pet = petRepository.findById(id);
            if (pet.isPresent()) {
                return ResponseEntity.ok(pet.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/pets/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Integer id, @RequestBody Pet petDetails) {
        try {
            Optional<Pet> pet = petRepository.findById(id);
            if (pet.isPresent()) {
                Pet existingPet = pet.get();
                if (petDetails.getName() != null) {
                    existingPet.setName(petDetails.getName());
                }
                if (petDetails.getSpecies() != null) {
                    existingPet.setSpecies(petDetails.getSpecies());
                }
                if (petDetails.getBreed() != null) {
                    existingPet.setBreed(petDetails.getBreed());
                }
                if (petDetails.getAge() != null) {
                    existingPet.setAge(petDetails.getAge());
                }
                if (petDetails.getGender() != null) {
                    existingPet.setGender(petDetails.getGender());
                }
                if (petDetails.getLocation() != null) {
                    existingPet.setLocation(petDetails.getLocation());
                }
                if (petDetails.getPhotos() != null) {
                    existingPet.setPhotos(petDetails.getPhotos());
                }
                if (petDetails.getStatus() != null) {
                    existingPet.setStatus(petDetails.getStatus());
                }
                petRepository.save(existingPet);
                return ResponseEntity.ok(existingPet);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al actualizar mascota: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/pets/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Integer id) {
        try {
            Optional<Pet> pet = petRepository.findById(id);
            if (pet.isPresent()) {
                petRepository.deleteById(id);
                Map<String, String> response = new HashMap<>();
                response.put("message", "Mascota eliminada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Error al eliminar mascota: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/pets/search")
    public ResponseEntity<List<Pet>> searchPets(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Integer age,
            @RequestParam(defaultValue = "available") String status) {
        try {
            List<Pet> pets;

            // Caso 1: Todos los criterios
            if (species != null && gender != null && location != null && age != null) {
                pets = petRepository.findBySpeciesAndGenderAndLocationAndAgeAndStatus(species, gender, location, age, status);
            }
            // Caso 2: Especie, Género, Ubicación
            else if (species != null && gender != null && location != null) {
                pets = petRepository.findBySpeciesAndGenderAndLocationAndStatus(species, gender, location, status);
            }
            // Caso 3: Especie, Género, Edad
            else if (species != null && gender != null && age != null) {
                pets = petRepository.findBySpeciesAndGenderAndAgeAndStatus(species, gender, age, status);
            }
            // Caso 4: Especie, Ubicación, Edad
            else if (species != null && location != null && age != null) {
                pets = petRepository.findBySpeciesAndLocationAndAgeAndStatus(species, location, age, status);
            }
            // Caso 5: Género, Ubicación, Edad
            else if (gender != null && location != null && age != null) {
                pets = petRepository.findByGenderAndLocationAndAgeAndStatus(gender, location, age, status);
            }
            // Caso 6: Especie, Género
            else if (species != null && gender != null) {
                pets = petRepository.findBySpeciesAndGenderAndStatus(species, gender, status);
            }
            // Caso 7: Especie, Ubicación
            else if (species != null && location != null) {
                pets = petRepository.findBySpeciesAndLocationAndStatus(species, location, status);
            }
            // Caso 8: Especie, Edad
            else if (species != null && age != null) {
                pets = petRepository.findBySpeciesAndAgeAndStatus(species, age, status);
            }
            // Caso 9: Género, Ubicación
            else if (gender != null && location != null) {
                pets = petRepository.findByGenderAndLocationAndStatus(gender, location, status);
            }
            // Caso 10: Género, Edad
            else if (gender != null && age != null) {
                pets = petRepository.findByGenderAndAgeAndStatus(gender, age, status);
            }
            // Caso 11: Ubicación, Edad
            else if (location != null && age != null) {
                pets = petRepository.findByLocationAndAgeAndStatus(location, age, status);
            }
            // Caso 12: Solo Especie
            else if (species != null) {
                pets = petRepository.findBySpeciesAndStatus(species, status);
            }
            // Caso 13: Solo Género
            else if (gender != null) {
                pets = petRepository.findByGenderAndStatus(gender, status);
            }
            // Caso 14: Solo Ubicación
            else if (location != null) {
                pets = petRepository.findByLocationAndStatus(location, status);
            }
            // Caso 15: Solo Edad
            else if (age != null) {
                pets = petRepository.findByAgeAndStatus(age, status);
            }
            // Caso 16: Solo Status (disponibles)
            else {
                pets = petRepository.findByStatus(status);
            }

            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
