package com.petadoption.animalinformation.service;


import com.petadoption.animalinformation.entity.Animal;
import com.petadoption.animalinformation.repository.AnimalRepository;
import com.petadoption.animalinformation.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Animal getAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal not found"));
    }
    
    public List<Animal> searchAnimals(String breed, Integer age, String location) {
        return animalRepository.searchAnimals(breed, age, location);
    }
    
    public Animal addAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal updateAnimal(Long id, Animal animalDetails) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal not found"));
        animal.setName(animalDetails.getName());
        animal.setAge(animalDetails.getAge());
        animal.setSpecialNeeds(animalDetails.getSpecialNeeds());
        try {
            animal.setAdoptionStatus(animalDetails.getAdoptionStatus());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid adoption status value");
        }        animal.setBreed(animalDetails.getBreed());
        animal.setLocation(animalDetails.getLocation());
        return animalRepository.save(animal);
    }

    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Animal not found"));
        animalRepository.delete(animal);
    }
   
}
