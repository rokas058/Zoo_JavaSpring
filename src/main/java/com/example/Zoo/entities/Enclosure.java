package com.example.Zoo.entities;

import com.example.Zoo.enums.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enclosure")
public class Enclosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String location;

    private List<String> objects;

    @OneToMany(mappedBy = "enclosure", cascade = CascadeType.ALL)
    private List<Animals> animals = new ArrayList<>();

    public void addToAnimals(List<Animals> listAnimals) {
        for (Animals animals : listAnimals) {
            animals.setEnclosure(this);
            this.animals.add(animals);
        }
    }
}
