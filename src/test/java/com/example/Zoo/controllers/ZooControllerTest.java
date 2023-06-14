package com.example.Zoo.controllers;

import com.example.Zoo.dto.AnimalsDto;
import com.example.Zoo.entities.Animals;
import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.services.AnimalsService;
import com.example.Zoo.services.EnclosureService;
import com.example.Zoo.services.ZooService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class ZooControllerTest {
    @Mock
    private AnimalsService animalsService;

    @Mock
    private ZooService zooService;

    @Mock
    private EnclosureService enclosureService;

    @InjectMocks
    private ZooController zooController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(zooController).build();
    }

    @Test
    void testAnimalsInEnclosure() throws Exception {
        // Create a sample list of AnimalsDto objects for testing
        List<AnimalsDto> animalsDtoList = new ArrayList<>();
        AnimalsDto animalDto1 = new AnimalsDto();
        animalDto1.setId(1L);
        AnimalsDto animalDto2 = new AnimalsDto();
        animalDto2.setId(2L);
        animalsDtoList.add(animalDto1);
        animalsDtoList.add(animalDto2);

        // Create a sample list of Animals objects for testing
        List<Animals> animalsList = new ArrayList<>();
        Animals animal1 = new Animals();
        animal1.setId(1L);
        Animals animal2 = new Animals();
        animal2.setId(2L);
        animalsList.add(animal1);
        animalsList.add(animal2);

        // Mock the behavior of AnimalsService
        when(animalsService.findAnimalsByIds(anyList())).thenReturn(animalsList);

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/zoo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(animalsDtoList)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // Verify that the animalsGetEnclosure method of ZooService was called with the correct animalsList
        verify(zooService).animalsGetEnclosure(animalsList);
    }

    @Test
    void testUpdateAnimalsEnclosure() throws Exception {
        // Create a sample Animals object for testing
        Animals existingAnimals = new Animals();
        existingAnimals.setId(1L);

        // Create a sample Enclosure object for testing
        Enclosure enclosure = new Enclosure();
        enclosure.setId(1L);

        // Mock the behavior of AnimalsService
        when(animalsService.findAnimalsById(anyLong())).thenReturn(existingAnimals);
        when(enclosureService.findEnclosureById(anyLong())).thenReturn(enclosure);
        when(animalsService.createAnimals(any(Animals.class))).thenReturn(existingAnimals);

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/zoo/{animalsId}", 1L)
                        .param("enclosureId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // Verify that the findAnimalsById and findEnclosureById methods were called with the correct IDs
        verify(animalsService).findAnimalsById(1L);
        verify(enclosureService).findEnclosureById(1L);

        // Verify that the createAnimals method was called with the updated Animals object
        verify(animalsService).createAnimals(existingAnimals);
    }

    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}