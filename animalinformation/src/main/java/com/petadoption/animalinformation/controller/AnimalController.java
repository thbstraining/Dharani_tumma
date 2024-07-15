package com.petadoption.animalinformation.controller;


import com.petadoption.animalinformation.entity.AdoptionStatus;
import com.petadoption.animalinformation.entity.Animal;
import com.petadoption.animalinformation.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.getAllAnimals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id) {
        Animal animal = animalService.getAnimalById(id);
        return ResponseEntity.ok(animal);
    }
    @GetMapping("/animals/search")
    public List<Animal> searchAnimals(
    @RequestParam(required = false) String breed,
    @RequestParam(required = false) Integer age,
    @RequestParam(required = false) String location) {

   // Call the service method to search animals based on criteria
    return animalService.searchAnimals(breed, age, location);
    }

    @PostMapping
    public Animal addAnimal(@RequestBody Animal animal) {
    	if (!AdoptionStatus.isValidStatus(animal.getAdoptionStatus().name())) {
            throw new IllegalArgumentException("Invalid adoption status: " + animal.getAdoptionStatus());
        }
        Animal addedAnimal = animalService.addAnimal(animal);
        return animalService.addAnimal(animal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal animalDetails) {
        Animal updatedAnimal = animalService.updateAnimal(id, animalDetails);
        return ResponseEntity.ok(updatedAnimal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build();
    }
}
