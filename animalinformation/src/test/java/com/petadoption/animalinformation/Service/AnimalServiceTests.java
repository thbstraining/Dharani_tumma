package com.petadoption.animalinformation.Service;

import com.petadoption.animalinformation.entity.AdoptionStatus;
import com.petadoption.animalinformation.entity.Animal;
import com.petadoption.animalinformation.exception.ResourceNotFoundException;
import com.petadoption.animalinformation.repository.AnimalRepository;
import com.petadoption.animalinformation.service.AnimalService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AnimalServiceTests {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

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
        when(animalRepository.findAll()).thenReturn(animals);

        List<Animal> result = animalService.getAllAnimals();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(animal1, result.get(0));
        assertEquals(animal2, result.get(1));
    }

    @Test
    public void testGetAnimalById_Success() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Fluffy");
        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));

        Animal result = animalService.getAnimalById(1L);

        assertNotNull(result);
        assertEquals(animal, result);
    }

    @Test
    public void testGetAnimalById_NotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            animalService.getAnimalById(1L);
        });
        assertEquals("Animal not found", thrown.getMessage());
    }

    @Test
    public void testSearchAnimals() {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Fluffy");
        List<Animal> animals = Arrays.asList(animal);
        when(animalRepository.searchAnimals("Labrador", 3, "Shelter A")).thenReturn(animals);

        List<Animal> result = animalService.searchAnimals("Labrador", 3, "Shelter A");

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

        when(animalRepository.save(animal)).thenReturn(animal);

        Animal result = animalService.addAnimal(animal);

        assertNotNull(result);
        assertEquals(animal, result);
    }

    @Test
    public void testUpdateAnimal_Success() {
        Animal existingAnimal = new Animal();
        existingAnimal.setId(1L);
        existingAnimal.setName("Fluffy");
        
        Animal updatedAnimal = new Animal();
        updatedAnimal.setId(1L);
        updatedAnimal.setName("Spot");
        updatedAnimal.setAge(4);
        updatedAnimal.setSpecialNeeds("None");
        updatedAnimal.setAdoptionStatus(AdoptionStatus.ADOPTED);
        updatedAnimal.setBreed("Beagle");
        updatedAnimal.setLocation("Shelter B");

        when(animalRepository.findById(1L)).thenReturn(Optional.of(existingAnimal));
        when(animalRepository.save(updatedAnimal)).thenReturn(updatedAnimal);

        Animal result = animalService.updateAnimal(1L, updatedAnimal);

        assertNotNull(result);
        assertEquals(updatedAnimal, result);
    }

    @Test
    public void testUpdateAnimal_NotFound() {
        Animal updatedAnimal = new Animal();
        updatedAnimal.setId(1L);

        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            animalService.updateAnimal(1L, updatedAnimal);
        });
        assertEquals("Animal not found", thrown.getMessage());
    }

    @Test
    public void testDeleteAnimal_Success() {
        Animal animal = new Animal();
        animal.setId(1L);

        when(animalRepository.findById(1L)).thenReturn(Optional.of(animal));
        doNothing().when(animalRepository).delete(animal);

        animalService.deleteAnimal(1L);

        verify(animalRepository, times(1)).delete(animal);
    }

    @Test
    public void testDeleteAnimal_NotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            animalService.deleteAnimal(1L);
        });
        assertEquals("Animal not found", thrown.getMessage());
    }
}
