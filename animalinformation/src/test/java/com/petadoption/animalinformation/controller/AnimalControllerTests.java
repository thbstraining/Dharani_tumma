package com.petadoption.animalinformation.controller;

import com.petadoption.animalinformation.entity.AdoptionStatus;
import com.petadoption.animalinformation.entity.Animal;
import com.petadoption.animalinformation.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnimalControllerTests {

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private AnimalController animalController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAnimals() {
        Animal animal1 = new Animal();
        animal1.setId(1L);
        animal1.setName("Fluffy");
        animal1.setAge(3);
        animal1.setSpecialNeeds("None");
        animal1.setAdoptionStatus(AdoptionStatus.AVAILABLE);
        animal1.setBreed("Labrador");
        animal1.setLocation("Shelter A");

        Animal animal2 = new Animal();
        animal2.setId(2L);
        animal2.setName("Spot");
        animal2.setAge(5);
        animal2.setSpecialNeeds("None");
        animal2.setAdoptionStatus(AdoptionStatus.PENDING);
        animal2.setBreed("Beagle");
        animal2.setLocation("Shelter B");

        List<Animal> animals = Arrays.asList(animal1, animal2);
        when(animalService.getAllAnimals()).thenReturn(animals);

        List<Animal> result = animalController.getAllAnimals();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(animal1, result.get(0));
        assertEquals(animal2, result.get(1));
    }

    @Test
    public void testGetAnimalById() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Fluffy");
        when(animalService.getAnimalById(1L)).thenReturn(animal);

        ResponseEntity<Animal> response = animalController.getAnimalById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(animal, response.getBody());
    }

    @Test
    public void testSearchAnimals() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Fluffy");
        List<Animal> animals = Arrays.asList(animal);
        when(animalService.searchAnimals("Labrador", 3, "Shelter A")).thenReturn(animals);

        List<Animal> result = animalController.searchAnimals("Labrador", 3, "Shelter A");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(animal, result.get(0));
    }

    @Test
    public void testAddAnimal() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Fluffy");
        animal.setAge(3);
        animal.setSpecialNeeds("None");
        animal.setAdoptionStatus(AdoptionStatus.AVAILABLE);
        animal.setBreed("Labrador");
        animal.setLocation("Shelter A");

        when(animalService.addAnimal(animal)).thenReturn(animal);

        Animal result = animalController.addAnimal(animal);

        assertNotNull(result);
        assertEquals(animal, result);
    }

    @Test
    public void testAddAnimal_InvalidStatus() {
        Animal animal = new Animal();
        animal.setAdoptionStatus(null); 

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            animalController.addAnimal(animal);
        });
        assertEquals("Invalid adoption status: null", thrown.getMessage());
    }

    @Test
    public void testUpdateAnimal() {
        Animal existingAnimal = new Animal();
        existingAnimal.setId(1L);
        existingAnimal.setName("Fluffy");

        Animal updatedAnimal = new Animal();
        updatedAnimal.setId(1L);
        updatedAnimal.setName("Spot");

        when(animalService.updateAnimal(1L, updatedAnimal)).thenReturn(updatedAnimal);

        ResponseEntity<Animal> response = animalController.updateAnimal(1L, updatedAnimal);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedAnimal, response.getBody());
    }

    @Test
    public void testDeleteAnimal() {
        doNothing().when(animalService).deleteAnimal(1L);

        ResponseEntity<Void> response = animalController.deleteAnimal(1L);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }
}
