package com.duoc.backend;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface PetRepository extends CrudRepository<Pet, Integer> {
    List<Pet> findByStatus(String status);
    List<Pet> findBySpecies(String species);
    List<Pet> findByGender(String gender);
    List<Pet> findByLocation(String location);
    List<Pet> findByAge(Integer age);
    List<Pet> findBySpeciesAndStatus(String species, String status);
    List<Pet> findByGenderAndStatus(String gender, String status);
    List<Pet> findByLocationAndStatus(String location, String status);
    List<Pet> findByAgeAndStatus(Integer age, String status);
    List<Pet> findBySpeciesAndGender(String species, String gender);
    List<Pet> findBySpeciesAndLocation(String species, String location);
    List<Pet> findBySpeciesAndAge(String species, Integer age);
    List<Pet> findByGenderAndLocation(String gender, String location);
    List<Pet> findByGenderAndAge(String gender, Integer age);
    List<Pet> findByLocationAndAge(String location, Integer age);
    List<Pet> findBySpeciesAndGenderAndStatus(String species, String gender, String status);
    List<Pet> findBySpeciesAndLocationAndStatus(String species, String location, String status);
    List<Pet> findBySpeciesAndAgeAndStatus(String species, Integer age, String status);
    List<Pet> findByGenderAndLocationAndStatus(String gender, String location, String status);
    List<Pet> findByGenderAndAgeAndStatus(String gender, Integer age, String status);
    List<Pet> findByLocationAndAgeAndStatus(String location, Integer age, String status);
    List<Pet> findBySpeciesAndGenderAndLocation(String species, String gender, String location);
    List<Pet> findBySpeciesAndGenderAndAge(String species, String gender, Integer age);
    List<Pet> findBySpeciesAndLocationAndAge(String species, String location, Integer age);
    List<Pet> findByGenderAndLocationAndAge(String gender, String location, Integer age);
    List<Pet> findBySpeciesAndGenderAndLocationAndStatus(String species, String gender, String location, String status);
    List<Pet> findBySpeciesAndGenderAndAgeAndStatus(String species, String gender, Integer age, String status);
    List<Pet> findBySpeciesAndLocationAndAgeAndStatus(String species, String location, Integer age, String status);
    List<Pet> findByGenderAndLocationAndAgeAndStatus(String gender, String location, Integer age, String status);
    List<Pet> findBySpeciesAndGenderAndLocationAndAge(String species, String gender, String location, Integer age);
    List<Pet> findBySpeciesAndGenderAndLocationAndAgeAndStatus(String species, String gender, String location, Integer age, String status);
}
