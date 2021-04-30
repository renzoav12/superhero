package com.example.superhero.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SuperHeroesDTO {
    private List<SuperHeroDTO> superHeroes;
}
