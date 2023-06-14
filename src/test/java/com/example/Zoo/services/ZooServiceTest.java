package com.example.Zoo.services;

import com.example.Zoo.entities.Animals;
import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.enums.Food;
import com.example.Zoo.enums.Size;
import com.example.Zoo.repositories.EnclosureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class ZooServiceTest {

    @Mock
    private EnclosureRepository enclosureRepository;

    @InjectMocks
    private ZooService zooService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAnimalsGetEnclosure() {
        // Create a list of mock Animals
        List<Animals> animalsList = new ArrayList<>();
        Animals herbivoreAnimal = new Animals();
        herbivoreAnimal.setSpecies("Deer");
        herbivoreAnimal.setFood(Food.HERBIVORE);
        animalsList.add(herbivoreAnimal);

        Animals carnivoreAnimal = new Animals();
        carnivoreAnimal.setSpecies("Lion");
        carnivoreAnimal.setFood(Food.CARNIVORE);
        animalsList.add(carnivoreAnimal);

        // Create mock Enclosure objects
        Enclosure smallEnclosure = new Enclosure();
        smallEnclosure.setSize(Size.SMALL);

        Enclosure mediumEnclosure = new Enclosure();
        mediumEnclosure.setSize(Size.MEDIUM);

        // Mock the behavior of the enclosureRepository
        List<Enclosure> emptyEnclosures = new ArrayList<>();
        emptyEnclosures.add(smallEnclosure);
        emptyEnclosures.add(mediumEnclosure);
        when(enclosureRepository.findAll()).thenReturn(emptyEnclosures);

        // Call the method under test
        zooService.animalsGetEnclosure(animalsList);

        // Verify the expected behavior
        verify(enclosureRepository).save(smallEnclosure);
    }
}