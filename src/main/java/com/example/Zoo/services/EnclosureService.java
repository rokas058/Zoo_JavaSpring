package com.example.Zoo.services;

import com.example.Zoo.entities.Enclosure;
import com.example.Zoo.repositories.EnclosureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnclosureService {

    private final EnclosureRepository enclosureRepository;

    public List<Enclosure> findAllEnclosures() {
        return enclosureRepository.findAll();
    }

    public Enclosure findEnclosureById(Long id) {
        return enclosureRepository.findById(id).orElse(null);
    }

    public Enclosure createEnclosure(Enclosure enclosure) {
        enclosureRepository.save(enclosure);
        return enclosure;
    }

    public Enclosure updateEnclosure(Enclosure existingEnclosure) {
        return enclosureRepository.save(existingEnclosure);
    }

    public void deleteEnclosure(Long id) {
        enclosureRepository.deleteById(id);
    }
}
