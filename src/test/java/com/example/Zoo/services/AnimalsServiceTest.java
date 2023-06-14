package com.example.Zoo.services;

import com.example.Zoo.entities.Animals;
import com.example.Zoo.repositories.AnimalsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class AnimalsServiceTest {

    @Mock
    private AnimalsRepository animalsRepository;

    @InjectMocks
    private AnimalsService animalsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllAnimals() {
        // Mock the behavior of the AnimalsRepository
        List<Animals> mockAnimals = new ArrayList<>();

        Animals lion = new Animals();
        lion.setSpecies("Lion");
        mockAnimals.add(lion);

        Animals tiger = new Animals();
        tiger.setSpecies("Tiger");
        mockAnimals.add(tiger);

        when(animalsRepository.findAll()).thenReturn(mockAnimals);

        // Call the method under test
        List<Animals> result = animalsService.findAllAnimals();

        // Assert the result
        assertEquals(2, result.size());
        assertEquals("Lion", result.get(0).getSpecies());
        assertEquals("Tiger", result.get(1).getSpecies());
    }

    @Test
    void testFindAnimalsById() {
        // Mock the behavior of the AnimalsRepository
        Animals mockAnimal = new Animals();
        mockAnimal.setId(1L);
        mockAnimal.setSpecies("Lion");
        when(animalsRepository.findById(1L)).thenReturn(Optional.of(mockAnimal));

        // Call the method under test
        Animals result = animalsService.findAnimalsById(1L);

        // Assert the result
        assertEquals(1L, result.getId());
        assertEquals("Lion", result.getSpecies());
    }

    @Test
    void testCreateAnimals() {
        // Create a mock Animals object
        Animals mockAnimal = new Animals();
        mockAnimal.setSpecies("Lion");

        // Call the method under test
        animalsService.createAnimals(mockAnimal);

        // Verify that save() method is called on the AnimalsRepository with the mock animal
        verify(animalsRepository).save(mockAnimal);
    }

    @Test
    void testUpdateAnimal() {
        // Create a mock Animals object
        Animals mockAnimal = new Animals();
        mockAnimal.setId(1L);
        mockAnimal.setSpecies("Lion");

        // Mock the behavior of the AnimalsRepository
        when(animalsRepository.save(mockAnimal)).thenReturn(mockAnimal);

        // Call the method under test
        Animals result = animalsService.updateAnimal(mockAnimal);

        // Assert the result
        assertEquals(1L, result.getId());
        assertEquals("Lion", result.getSpecies());

    }

    @Test
    void testDeleteAnimals() {
        // Call the method under test
        Long animalId = 1L;
        animalsService.deleteAnimals(animalId);

        // Verify that deleteById() method is called on the AnimalsRepository with the correct ID
        verify(animalsRepository).deleteById(animalId);

    }

    @Test
    void findAnimalsByIds() {
        // Create a list of animal IDs
        List<Long> animalIds = Arrays.asList(1L, 2L, 3L);

        // Create mock Animals objects
        Animals lion = new Animals();
        lion.setId(1L);
        lion.setSpecies("Lion");

        Animals tiger = new Animals();
        tiger.setId(2L);
        tiger.setSpecies("Tiger");

        Animals elephant = new Animals();
        elephant.setId(3L);
        elephant.setSpecies("Elephant");

        // Mock the behavior of the AnimalsRepository
        when(animalsRepository.findAllById(animalIds)).thenReturn(Arrays.asList(lion, tiger, elephant));

        // Call the method under test
        List<Animals> result = animalsService.findAnimalsByIds(animalIds);

        // Assert the result
        assertEquals(3, result.size());
        assertEquals("Lion", result.get(0).getSpecies());
        assertEquals("Tiger", result.get(1).getSpecies());
        assertEquals("Elephant", result.get(2).getSpecies());
    }
}