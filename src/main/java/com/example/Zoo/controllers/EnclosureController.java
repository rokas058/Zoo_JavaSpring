package com.example.Zoo.controllers;

import com.example.Zoo.dto.EnclosureDto;
import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.services.EnclosureService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enclosure")
public class EnclosureController {

    private final EnclosureService enclosureService;

    private final ModelMapper modelMapper;

    @GetMapping
    public List<EnclosureDto> getAllEnclosures() {
        List<Enclosure> allEnclosures= enclosureService.findAllEnclosures();
        return allEnclosures.stream()
                .map(enclosure -> modelMapper.map(enclosure, EnclosureDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnclosureDto> getEnclosureById(@PathVariable Long id){
        Enclosure existingEnclosure = enclosureService.findEnclosureById(id);

        if (existingEnclosure == null) {
            return ResponseEntity.notFound().build();
        }

        EnclosureDto enclosureDto = modelMapper.map(existingEnclosure, EnclosureDto.class);
        return ResponseEntity.ok(enclosureDto);
    }

    @PostMapping
    public ResponseEntity<EnclosureDto> createEnclosure(@RequestBody EnclosureDto enclosureDto) {
        Enclosure enclosure = modelMapper.map(enclosureDto, Enclosure.class);
        Enclosure savedEnclosure = enclosureService.createEnclosure(enclosure);
        EnclosureDto savedEnclosureDto = modelMapper.map(savedEnclosure, EnclosureDto.class);
        return ResponseEntity.ok(savedEnclosureDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnclosureDto> updateEnclosure(@PathVariable Long id, @RequestBody EnclosureDto enclosureDto){
        Enclosure existingEnclosure = enclosureService.findEnclosureById(id);

        if (existingEnclosure == null) {
            return ResponseEntity.notFound().build();
        }

        modelMapper.map(enclosureDto, existingEnclosure);
        Enclosure updatedEnclosure = enclosureService.updateEnclosure(existingEnclosure);
        EnclosureDto updatedEnclosureDto = modelMapper.map(updatedEnclosure, EnclosureDto.class);
        return ResponseEntity.ok(updatedEnclosureDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnclosure(@PathVariable Long id){
        Enclosure existingEnclosure = enclosureService.findEnclosureById(id);

        if (existingEnclosure == null) {
            return ResponseEntity.notFound().build();
        }

        enclosureService.deleteEnclosure(id);
        return ResponseEntity.ok().build();
    }
}
