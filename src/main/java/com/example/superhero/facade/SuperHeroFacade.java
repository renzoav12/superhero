package com.example.superhero.facade;

import com.example.superhero.dto.SuperHeroDTO;
import com.example.superhero.dto.SuperHeroRequest;
import com.example.superhero.dto.SuperHeroesDTO;
import com.example.superhero.exception.SuperHeroNotFoundException;
import com.example.superhero.model.SuperHero;
import com.example.superhero.service.SuperHeroService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SuperHeroFacade {

    private final SuperHeroService superHeroService;

    private final ConversionService conversionService;


    public SuperHeroDTO createSuperHero(final SuperHeroRequest superHeroBody) {
        Assert.notNull(superHeroBody, "The create SuperHero body cannot be null");
        Assert.hasLength(superHeroBody.getSuperHeroName(), "the superHeroName cannot be empty");

        SuperHero superHero = superHeroService.createSuperHero(superHeroBody);
        return conversionService.convert(superHero, SuperHeroDTO.class);
    }

    public void updateSuperHero(final Long superHeroId, final SuperHeroRequest superHeroBody) {
        SuperHero superHero = getOptionalSuperHero(superHeroService.findByIdOptional(superHeroId), superHeroId);
        saveSuperHero(superHero, superHeroBody);
    }

    public SuperHeroesDTO getSuperHeroes() {
        List<SuperHero> availability = superHeroService.findAllSuperHeroes();
        return conversionService.convert(availability, SuperHeroesDTO.class);
    }

    public SuperHeroDTO getSuperHeroById(final Long id) {
        SuperHero superHero = superHeroService.findSuperHeroById(id);
        return conversionService.convert(superHero, SuperHeroDTO.class);
    }

    public SuperHeroesDTO getSuperHeroesByName(final String nameMatch) {
        List<SuperHero> superHero = superHeroService.findSuperHeroesWithMatchInName(nameMatch);
        return conversionService.convert(superHero, SuperHeroesDTO.class);
    }

    public void deleteSuperHero(final Long id) {
        superHeroService.deleteSuperHero(id);
    }

    private void saveSuperHero(final SuperHero superHero, final SuperHeroRequest superHeroRequest) {

        SuperHeroRequest superHeroUpdate =
                new SuperHeroRequest(superHeroRequest.getFirstName() == null
                        ? superHero.getFirstName() : superHeroRequest.getFirstName(),
                        superHeroRequest.getSuperHeroName() == null ? superHero.getSuperHeroName()
                                : superHeroRequest.getSuperHeroName());

        superHeroService.updateSuperHero(superHero.getId(), superHeroUpdate);

    }

    private SuperHero getOptionalSuperHero(final Optional<SuperHero> superHeroOptional, final Long superHeroId) {

        if (superHeroOptional.isEmpty()) {
            throw new SuperHeroNotFoundException(superHeroId);
        }
        SuperHero superHero = superHeroOptional.get();
        return superHero;
    }


}
