package com.example.Zoo.controllers;

import com.example.Zoo.dto.AnimalsDto;
import com.example.Zoo.entities.Animals;
import com.example.Zoo.services.AnimalsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animals")
public class AnimalsController {

    private final AnimalsService animalsService;

    private final ModelMapper modelMapper;

    @GetMapping
    public List<AnimalsDto> getAllAnimals() {
        List<Animals> allAnimals= animalsService.findAllAnimals();
        return allAnimals.stream()
                .map(animals -> modelMapper.map(animals, AnimalsDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalsDto> getAnimalsById(@PathVariable Long id){
        Animals existingAnimals = animalsService.findAnimalsById(id);

        if (existingAnimals == null) {
            return ResponseEntity.notFound().build();
        }

        AnimalsDto animalsDto = modelMapper.map(existingAnimals, AnimalsDto.class);
        return ResponseEntity.ok(animalsDto);

    }

    @PostMapping
    public ResponseEntity<AnimalsDto> createAnimals(@RequestBody AnimalsDto animalsDto) {
        Animals animals = modelMapper.map(animalsDto, Animals.class);
        Animals savedAnimal = animalsService.createAnimals(animals);
        AnimalsDto savedAnimalDto = modelMapper.map(savedAnimal, AnimalsDto.class);
        return ResponseEntity.ok(savedAnimalDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalsDto> updateAnimals(@PathVariable Long id, @RequestBody AnimalsDto animalsDto){
        Animals existingAnimals = animalsService.findAnimalsById(id);

        if (existingAnimals == null) {
            return ResponseEntity.notFound().build();
        }

        modelMapper.map(animalsDto, existingAnimals);
        Animals updatedAnimal = animalsService.updateAnimal(existingAnimals);
        AnimalsDto updatedAnimalDto = modelMapper.map(updatedAnimal, AnimalsDto.class);
        return ResponseEntity.ok(updatedAnimalDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnimals(@PathVariable Long id){
        Animals existingAnimals = animalsService.findAnimalsById(id);

        if (existingAnimals == null) {
            return ResponseEntity.notFound().build();
        }

        animalsService.deleteAnimals(id);
        return ResponseEntity.ok().build();
    }

}
