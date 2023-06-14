package com.example.Zoo.services;

import com.example.Zoo.entities.Animals;
import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.enums.Food;
import com.example.Zoo.enums.Size;
import com.example.Zoo.repositories.EnclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZooService {

    private final EnclosureRepository enclosureRepository;

    public void animalsGetEnclosure(List<Animals> animalsList) {
        List<Animals> allAnimalsHerbivore = getAllAnimalsHerbivore(animalsList);
        List<Animals> allAnimalsCarnivore = getAllAnimalsCarnivore(animalsList);

        if ( !allAnimalsHerbivore.isEmpty() )
            herbivoreGetEnclosure(allAnimalsHerbivore);
        if ( !allAnimalsCarnivore.isEmpty() )
            carnivoreGetEnclosure(allAnimalsCarnivore);
    }

    private void herbivoreGetEnclosure(List<Animals> allAnimalsHerbivore) {
        List<Enclosure> allEmptyEnclosures = getAllEmptyEnclosures();

        List<Animals> unassignedCarnivores = unassignedAnimals(allAnimalsHerbivore);

        Size[] priorityOrder = {Size.HUGE, Size.LARGE, Size.MEDIUM, Size.SMALL};

        Optional<Enclosure> suitableEnclosure = Arrays.stream(priorityOrder)
                .map(size -> allEmptyEnclosures.stream()
                        .filter(enclosure -> enclosure.getSize().equals(size))
                        .findFirst()
                )
                .filter(Optional::isPresent)
                .findFirst()
                .orElse(Optional.empty());

        suitableEnclosure.ifPresent(enclosure -> {
            enclosure.addToAnimals(unassignedCarnivores);
            enclosureRepository.save(enclosure);
        });
    }

    private void carnivoreGetEnclosure(List<Animals> allAnimalsCarnivore) {
        List<Enclosure> allEmptyEnclosures = getAllEmptyEnclosures();

        List<Animals> unassignedAnimals = unassignedAnimals(allAnimalsCarnivore);

        Map<String, List<Animals>> carnivoreGroups = unassignedAnimals.stream()
                .collect(Collectors.groupingBy(Animals::getSpecies));

        for (List<Animals> group : carnivoreGroups.values()) {
            if (group.isEmpty()) {
                continue;
            }

            Enclosure enclosure = allEmptyEnclosures.remove(0);
            enclosure.addToAnimals(group);
            enclosureRepository.save(enclosure);
        }
    }

    private List<Enclosure> getAllEmptyEnclosures() {
        List<Enclosure> enclosureList = enclosureRepository.findAll();
        return enclosureList.stream()
                .filter(enclosure -> enclosure.getAnimals().size() == 0)
                .collect(Collectors.toList());
    }

    private List<Animals> getAllAnimalsCarnivore(List<Animals> animalsList) {
        return animalsList.stream()
                .filter(animal -> animal.getFood().equals(Food.CARNIVORE))
                .collect(Collectors.toList());
    }

    private List<Animals> getAllAnimalsHerbivore(List<Animals> animalsList) {
        return animalsList.stream()
                .filter(animal -> animal.getFood().equals(Food.HERBIVORE))
                .collect(Collectors.toList());
    }

    private List<Animals> unassignedAnimals(List<Animals> allAnimalsList) {
        return allAnimalsList.stream()
                .filter(animal -> animal.getEnclosure() == null)
                .toList();
    }
}
