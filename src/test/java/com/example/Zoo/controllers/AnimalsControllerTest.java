package com.example.Zoo.controllers;

import com.example.Zoo.dto.AnimalsDto;
import com.example.Zoo.entities.Animals;
import com.example.Zoo.services.AnimalsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

class AnimalsControllerTest {

    @Mock
    private AnimalsService animalsService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AnimalsController animalsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(animalsController).build();
    }

    @Test
    void testGetAllAnimals() throws Exception {
        // Mock the behavior of AnimalsService
        Animals animal1 = new Animals();
        animal1.setSpecies("Lion");
        Animals animal2 = new Animals();
        animal2.setSpecies("Tiger");
        List<Animals> mockAnimals = List.of(animal1, animal2);
        when(animalsService.findAllAnimals()).thenReturn(mockAnimals);

        // Mock the behavior of ModelMapper
        AnimalsDto animalDto1 = new AnimalsDto();
        animalDto1.setSpecies("Lion");
        AnimalsDto animalDto2 = new AnimalsDto();
        animalDto2.setSpecies("Tiger");
        List<AnimalsDto> expectedDtos = List.of(animalDto1, animalDto2);
        when(modelMapper.map(any(Animals.class), eq(AnimalsDto.class))).thenReturn(animalDto1, animalDto2);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(expectedDtos)));
    }

    @Test
    void testGetAnimalsById() throws Exception {
        // Mock the behavior of AnimalsService
        Long id = 1L;
        Animals existingAnimals = new Animals();
        existingAnimals.setId(id);
        existingAnimals.setSpecies("Lion");
        when(animalsService.findAnimalsById(id)).thenReturn(existingAnimals);

        // Mock the behavior of ModelMapper
        AnimalsDto animalsDto = new AnimalsDto();
        animalsDto.setId(id);
        animalsDto.setSpecies("Lion");
        when(modelMapper.map(existingAnimals, AnimalsDto.class)).thenReturn(animalsDto);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/animals/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(animalsDto)));
    }

    @Test
    void testCreateAnimals() throws Exception {
        // Mock the behavior of ModelMapper
        AnimalsDto animalsDto = new AnimalsDto();
        animalsDto.setSpecies("Lion");

        Animals animals = new Animals();
        animals.setSpecies("Lion");

        when(modelMapper.map(animalsDto, Animals.class)).thenReturn(animals);

        // Mock the behavior of AnimalsService
        Animals savedAnimal = new Animals();
        savedAnimal.setId(1L);
        savedAnimal.setSpecies("Lion");

        when(animalsService.createAnimals(animals)).thenReturn(savedAnimal);

        AnimalsDto savedAnimalDto = new AnimalsDto();
        savedAnimalDto.setId(1L);
        savedAnimalDto.setSpecies("Lion");

        when(modelMapper.map(savedAnimal, AnimalsDto.class)).thenReturn(savedAnimalDto);

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(animalsDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(savedAnimalDto)));
    }

    @Test
    void testUpdateAnimals() throws Exception {
        Long animalId = 1L;
        Animals existingAnimals = new Animals();
        existingAnimals.setId(animalId);
        existingAnimals.setSpecies("Lion");

        when(animalsService.findAnimalsById(animalId)).thenReturn(existingAnimals);

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/animals/{id}", animalId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(existingAnimals)))
                .andExpect(status().isOk());

        verify(animalsService).updateAnimal(existingAnimals);
    }

    @Test
    void testDeleteAnimals() throws Exception {
        // Mock the behavior of AnimalsService
        Long animalId = 1L;
        Animals existingAnimals = new Animals();
        existingAnimals.setId(animalId);
        when(animalsService.findAnimalsById(animalId)).thenReturn(existingAnimals);

        // Perform the DELETE request and verify the response
        mockMvc.perform(delete("/api/animals/{id}", animalId))
                .andExpect(status().isOk());

        // Verify that the service method was called with the correct ID
        verify(animalsService).deleteAnimals(animalId);
    }

    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}