package com.example.superhero.service;

import com.example.superhero.dto.SuperHeroRequest;
import com.example.superhero.exception.SuperHeroNotFoundException;
import com.example.superhero.model.SuperHero;
import com.example.superhero.repository.SuperHeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springfox.documentation.annotations.Cacheable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuperHeroService {

    private final SuperHeroRepository superHeroRepository;


    public SuperHero createSuperHero(final SuperHeroRequest superHeroRequest) {

        SuperHero superHero = new SuperHero();
        superHero.setFirstName(superHeroRequest.getFirstName());
        superHero.setSuperHeroName(superHeroRequest.getSuperHeroName());
        return superHeroRepository.save(superHero);
    }

    public SuperHero updateSuperHero(final Long superHeroId,
                                     final SuperHeroRequest superHeroRequest) {

        return superHeroRepository.findById(superHeroId).map(superHero -> {
            superHero.setSuperHeroName(superHeroRequest.getSuperHeroName());
            superHero.setFirstName(superHeroRequest.getFirstName());
            return superHeroRepository.save(superHero);
        }).orElseThrow(() -> new SuperHeroNotFoundException(superHeroId));

    }

    public List<SuperHero> findAllSuperHeroes() {
        return superHeroRepository.findAll();
    }

    @Cacheable("superhero")
    public SuperHero findSuperHeroById(final Long id){
        return superHeroRepository.findById(id)
                .orElseThrow(() -> new SuperHeroNotFoundException(id));
    }

    public List<SuperHero> findSuperHeroesWithMatchInName(final String searchMan){
        return superHeroRepository.findSuperHeroesWithMatchName(searchMan);
    }

    public void deleteSuperHero(final Long superHeroId) {

        SuperHero superHero = superHeroRepository.findById(superHeroId)
                .orElseThrow(() -> new SuperHeroNotFoundException(superHeroId));
        superHeroRepository.delete(superHero);
    }

    public Optional<SuperHero> findByIdOptional(final Long supeHeroId) {
        return superHeroRepository.findById(supeHeroId);
    }
}
