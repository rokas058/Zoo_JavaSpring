package com.example.Zoo.services;

import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.repositories.EnclosureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class EnclosureServiceTest {

    @Mock
    private EnclosureRepository enclosureRepository;

    @InjectMocks
    private EnclosureService enclosureService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllEnclosures() {
        // Create mock Enclosure objects
        Enclosure enclosure1 = new Enclosure();
        enclosure1.setId(1L);
        enclosure1.setName("Enclosure 1");

        Enclosure enclosure2 = new Enclosure();
        enclosure2.setId(2L);
        enclosure2.setName("Enclosure 2");

        // Mock the behavior of the EnclosureRepository
        when(enclosureRepository.findAll()).thenReturn(Arrays.asList(enclosure1, enclosure2));

        // Call the method under test
        List<Enclosure> result = enclosureService.findAllEnclosures();

        // Assert the result
        assertEquals(2, result.size());
        assertEquals("Enclosure 1", result.get(0).getName());
        assertEquals("Enclosure 2", result.get(1).getName());
    }

    @Test
    void testFindEnclosureById() {
        // Create a mock Enclosure object
        Enclosure enclosure = new Enclosure();
        enclosure.setId(1L);
        enclosure.setName("Enclosure 1");

        // Mock the behavior of the EnclosureRepository
        when(enclosureRepository.findById(1L)).thenReturn(Optional.of(enclosure));

        // Call the method under test
        Enclosure result = enclosureService.findEnclosureById(1L);

        // Assert the result
        assertEquals(enclosure, result);
    }

    @Test
    void testCreateEnclosure() {
        // Create a mock Enclosure object
        Enclosure enclosure = new Enclosure();
        enclosure.setId(1L);
        enclosure.setName("Enclosure 1");

        // Call the method under test
        Enclosure result = enclosureService.createEnclosure(enclosure);

        // Verify that the save method is called on the EnclosureRepository with the enclosure object
        verify(enclosureRepository).save(enclosure);

        // Assert that the returned object is the same as the input enclosure
        assertEquals(enclosure, result);
    }

    @Test
    void testUpdateAnimal() {
        // Create a mock Enclosure object
        Enclosure existingEnclosure = new Enclosure();
        existingEnclosure.setId(1L);
        existingEnclosure.setName("Enclosure 1");

        // Mock the behavior of the EnclosureRepository
        when(enclosureRepository.save(existingEnclosure)).thenReturn(existingEnclosure);

        // Call the method under test
        Enclosure result = enclosureService.updateEnclosure(existingEnclosure);

        // Verify that the save method is called on the EnclosureRepository with the existingEnclosure object
        verify(enclosureRepository).save(existingEnclosure);

        // Assert that the returned object is the same as the input existingEnclosure
        assertEquals(existingEnclosure, result);
    }

    @Test
    void testDeleteEnclosure() {
        // Define the enclosure ID to delete
        Long enclosureId = 1L;

        // Call the method under test
        enclosureService.deleteEnclosure(enclosureId);

        // Verify that the deleteById method is called on the EnclosureRepository with the specified ID
        verify(enclosureRepository).deleteById(enclosureId);
    }
}