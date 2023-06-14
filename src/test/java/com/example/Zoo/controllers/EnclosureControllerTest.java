package com.example.Zoo.controllers;

import com.example.Zoo.dto.EnclosureDto;
import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.enums.Size;
import com.example.Zoo.services.EnclosureService;
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


class EnclosureControllerTest {

    @Mock
    private EnclosureService enclosureService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EnclosureController enclosureController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(enclosureController).build();
    }

    @Test
    void testGetAllEnclosures() throws Exception {
        // Mock the behavior of EnclosureService
        Enclosure enclosure1 = new Enclosure();
        enclosure1.setId(1L);
        Enclosure enclosure2 = new Enclosure();
        enclosure2.setId(2L);
        List<Enclosure> mockEnclosures = List.of(enclosure1, enclosure2);
        when(enclosureService.findAllEnclosures()).thenReturn(mockEnclosures);

        // Mock the behavior of ModelMapper
        EnclosureDto enclosureDto1 = new EnclosureDto();
        enclosureDto1.setId(1L);
        EnclosureDto enclosureDto2 = new EnclosureDto();
        enclosureDto2.setId(2L);
        List<EnclosureDto> expectedDtos = List.of(enclosureDto1, enclosureDto2);
        when(modelMapper.map(any(Enclosure.class), eq(EnclosureDto.class))).thenReturn(enclosureDto1, enclosureDto2);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/enclosure"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(expectedDtos)));
    }

    @Test
    void testGetEnclosureById() throws Exception {
        Long enclosureId = 1L;

        // Mock the behavior of EnclosureService
        Enclosure existingEnclosure = new Enclosure();
        existingEnclosure.setId(enclosureId);
        when(enclosureService.findEnclosureById(enclosureId)).thenReturn(existingEnclosure);

        // Mock the behavior of ModelMapper
        EnclosureDto enclosureDto = new EnclosureDto();
        enclosureDto.setId(enclosureId);
        when(modelMapper.map(existingEnclosure, EnclosureDto.class)).thenReturn(enclosureDto);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/enclosure/{id}", enclosureId))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(enclosureDto)));
    }

    @Test
    void testCreateEnclosure() throws Exception {
        // Create a sample EnclosureDto for the request body
        EnclosureDto requestDto = new EnclosureDto();
        requestDto.setSize(Size.LARGE); // Set the size accordingly

        // Mock the behavior of ModelMapper
        Enclosure enclosure = new Enclosure();
        Enclosure savedEnclosure = new Enclosure();
        EnclosureDto savedEnclosureDto = new EnclosureDto();
        when(modelMapper.map(requestDto, Enclosure.class)).thenReturn(enclosure);
        when(enclosureService.createEnclosure(enclosure)).thenReturn(savedEnclosure);
        when(modelMapper.map(savedEnclosure, EnclosureDto.class)).thenReturn(savedEnclosureDto);

        // Perform the POST request and verify the response
        mockMvc.perform(post("/api/enclosure")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(savedEnclosureDto)));
    }

    @Test
    void testUpdateEnclosure() throws Exception {
        // Create a sample EnclosureDto for the request body
        EnclosureDto requestDto = new EnclosureDto();
        requestDto.setSize(Size.LARGE); // Set the size accordingly

        // Mock the behavior of ModelMapper
        Enclosure existingEnclosure = new Enclosure();
        Enclosure updatedEnclosure = new Enclosure();
        EnclosureDto updatedEnclosureDto = new EnclosureDto();
        when(enclosureService.findEnclosureById(anyLong())).thenReturn(existingEnclosure);
        when(enclosureService.updateEnclosure(existingEnclosure)).thenReturn(updatedEnclosure);
        when(modelMapper.map(updatedEnclosure, EnclosureDto.class)).thenReturn(updatedEnclosureDto);

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/enclosure/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(toJsonString(updatedEnclosureDto)));
    }

    @Test
    void testDeleteEnclosure() throws Exception {
        // Create a sample Enclosure object for testing
        Enclosure existingEnclosure = new Enclosure();

        // Mock the behavior of EnclosureService
        when(enclosureService.findEnclosureById(anyLong())).thenReturn(existingEnclosure);

        // Perform the DELETE request and verify the response
        mockMvc.perform(delete("/api/enclosure/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // Verify that the EnclosureService deleteEnclosure method was called with the correct ID
        verify(enclosureService).deleteEnclosure(1L);
    }

    private static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}