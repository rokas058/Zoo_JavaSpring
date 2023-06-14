package com.example.Zoo.dto;

import com.example.Zoo.enums.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnclosureDto {

    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String location;

    private List<String> objects;

    @JsonManagedReference
    private List<AnimalsDto> animals;
}
