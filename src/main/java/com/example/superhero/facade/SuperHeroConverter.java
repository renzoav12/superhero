package com.example.superhero.facade;

import com.example.superhero.dto.SuperHeroDTO;
import com.example.superhero.dto.SuperHeroesDTO;
import com.example.superhero.model.SuperHero;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SuperHeroConverter {

    /**
     * Convert SuperHeroes Data MODEL to DTO SuperHero response.
     */
    public static class SuperHeroCreateConvert implements Converter<SuperHero, SuperHeroDTO> {
            @Override
            public SuperHeroDTO convert(final SuperHero superHero) {
                return SuperHeroDTO.builder().id(superHero.getId())
                                .superHeroName(superHero.getSuperHeroName())
                                .firstName(superHero.getFirstName()).build();
            }
    }

    /**
     * Convert List SuperHeroes Data MODEL to DTO response.
     */
    public static class SuperHeroesDTOConverter implements Converter<List<SuperHero>, SuperHeroesDTO>{

        @Override
        public SuperHeroesDTO convert(final List<SuperHero> superHeroes) {
            List<SuperHeroDTO> superHeroesConvert =superHeroes.stream()
                    .map(superHero ->
                         new SuperHeroDTO(superHero.getId(), superHero.getFirstName(),
                                 superHero.getSuperHeroName())
            ).collect(Collectors.toList());
            return SuperHeroesDTO.builder().superHeroes(superHeroesConvert).build();
        }
    }


    public static Set<Converter> all() {
        return Set.of(
                new SuperHeroesDTOConverter(),
                new SuperHeroCreateConvert()
        );
    }

}


