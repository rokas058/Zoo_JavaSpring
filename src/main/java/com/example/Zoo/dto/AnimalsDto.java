package com.example.Zoo.dto;

import com.example.Zoo.enums.Food;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalsDto {

    private Long id;

    private String species;

    @Enumerated(EnumType.STRING)
    private Food food;

    private Long amount;

    @JsonBackReference
    private EnclosureDto enclosure;
}
