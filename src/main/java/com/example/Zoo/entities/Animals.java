package com.example.Zoo.entities;

import com.example.Zoo.enums.Food;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animals")
public class Animals {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String species;

   @Enumerated(EnumType.STRING)
   private Food food;

   private Long amount;


   @ManyToOne
   @JoinColumn(name = "enclosure_id")
   private Enclosure enclosure;
}
