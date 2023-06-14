package com.example.Zoo.services;

import com.example.Zoo.entities.Animals;
import com.example.Zoo.repositories.AnimalsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalsService {

    private final AnimalsRepository animalsRepository;


    public List<Animals> findAllAnimals() {
        return animalsRepository.findAll();
    }

    public Animals findAnimalsById(Long id) {
        return animalsRepository.findById(id).orElse(null);
    }

    public Animals createAnimals(Animals animals) {
        animalsRepository.save(animals);
        return animals;
    }

    public Animals updateAnimal(Animals animals) {
        return animalsRepository.save(animals);
    }

    public void deleteAnimals(Long id) {
        animalsRepository.deleteById(id);
    }

    public List<Animals> findAnimalsByIds(List<Long> animalIds) {
        return animalsRepository.findAllById(animalIds);
    }

}
