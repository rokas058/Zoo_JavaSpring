package com.example.Zoo.controllers;

import com.example.Zoo.dto.AnimalsDto;
import com.example.Zoo.dto.EnclosureDto;
import com.example.Zoo.entities.Animals;
import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.services.AnimalsService;
import com.example.Zoo.services.EnclosureService;
import com.example.Zoo.services.ZooService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/zoo")
public class ZooController {

    private final AnimalsService animalsService;

    private final ZooService zooService;

    private final EnclosureService enclosureService;


    @PostMapping
    public ResponseEntity<?> animalsInEnclosure(@RequestBody List<AnimalsDto> animalsDtoListId){
        List<Long> animalIds = animalsDtoListId.stream()
                .map(AnimalsDto::getId)
                .collect(Collectors.toList());

        List<Animals> animalsList = animalsService.findAnimalsByIds(animalIds);
        zooService.animalsGetEnclosure(animalsList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{animalsId}")
    public ResponseEntity<?> updateAnimalsEnclosure(
            @PathVariable Long animalsId,
            @RequestParam Long enclosureId) {
        Animals existingAnimals = animalsService.findAnimalsById(animalsId);
        Enclosure enclosure = enclosureService.findEnclosureById(enclosureId);

        if (existingAnimals == null) {
            return ResponseEntity.notFound().build();
        }

        if (enclosure == null) {
            return ResponseEntity.notFound().build();
        }

        existingAnimals.setEnclosure(enclosure);
        animalsService.createAnimals(existingAnimals);

        return ResponseEntity.ok().build();
    }
}
