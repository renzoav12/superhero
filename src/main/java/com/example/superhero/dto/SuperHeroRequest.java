package com.example.superhero.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SuperHeroRequest {
    private String firstName;
    private String superHeroName;
}
