package com.example.superhero.service;

import com.example.superhero.dto.SuperHeroRequest;
import com.example.superhero.exception.SuperHeroNotFoundException;
import com.example.superhero.model.SuperHero;
import com.example.superhero.repository.SuperHeroRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuperHeroServiceTest {

    private SuperHeroRepository superHeroRepository;

    private SuperHeroService superHeroService;

    private static final String MATCH_FILTER_NAME_BY_MAN = "man";

    @Before
    public void setup(){
        superHeroRepository = Mockito.mock(SuperHeroRepository.class);
        superHeroService = new SuperHeroService(superHeroRepository);
    }

    @Test
    public void whenFindAllSuperHeroesThenReturnAllSuperHeroesTest() {
        List<SuperHero> superheroList = getSuperHeroes();
        Mockito.when(superHeroRepository.findAll()).thenReturn(superheroList);

        List<SuperHero> superHeroes = superHeroService.findAllSuperHeroes();

        Assertions.assertEquals(superHeroes.get(0).getSuperHeroName(), "Spiderman");
        Assertions.assertEquals(superHeroes.get(1).getSuperHeroName(), "Superman");
        Assertions.assertEquals(superHeroes.get(2).getSuperHeroName(), "Manolito el dormido");
    }


    @Test
    public void whenFindSuperHeroByIdThenReturnSuperHero() {
        SuperHero superHero = new SuperHero(10L, "FirstName1", "SuperHeroName1");
        Mockito.when(superHeroRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(superHero));

        SuperHero getSuperHero = superHeroService.findSuperHeroById(10L);

        Assertions.assertEquals(getSuperHero.getSuperHeroName(), "SuperHeroName1");
        Assertions.assertEquals(getSuperHero.getFirstName(), "FirstName1");
    }

    @Test
    public void whenFindSuperHeroesWithNameFilterMatchThenReturnSuperHeroes() {
        List<SuperHero> superheroList = getSuperHeroes();
        Mockito.when(superHeroRepository.findSuperHeroesWithMatchName(MATCH_FILTER_NAME_BY_MAN))
                .thenReturn(superheroList);

        List<SuperHero> superHeroes = superHeroService
                .findSuperHeroesWithMatchInName(MATCH_FILTER_NAME_BY_MAN);

        Assertions.assertEquals(superHeroes.get(0).getSuperHeroName(), "Spiderman");
        Assertions.assertEquals(superHeroes.get(1).getSuperHeroName(), "Superman");
        Assertions.assertEquals(superHeroes.get(2).getSuperHeroName(), "Manolito el dormido");
    }

    @Test
    public void wenCreateAnSuperheroThenReturnSuperHeroCreatedTest() {
        SuperHeroRequest superheroRequest =
                new SuperHeroRequest("Firstname 1", "Superheroname 1");
        SuperHero superhero = new SuperHero(1L, "Firstname 1", "Superheroname 1");

        Mockito.when(superHeroRepository.save(Mockito.any(SuperHero.class))).thenReturn(superhero);

        SuperHero result = superHeroService.createSuperHero(superheroRequest);

        Assertions.assertEquals(result.getFirstName(), "Firstname 1");
        Assertions.assertEquals(result.getSuperHeroName(), "Superheroname 1");
    }

    @Test
    public void whenFindSuperHeroByIdAndNotExistThenReturnSuperHeroesNotFoundException() {
        Mockito.when(superHeroRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(SuperHeroNotFoundException.class,
                () -> superHeroService.findSuperHeroById(20L));
    }

    @Test
    public void whenFindSuperHeroesWithNameContainsManAndNotExistThenReturnSuperHeroesEmpty() {

        List<SuperHero> emptyList = List.of();

        Mockito.when(superHeroRepository.findSuperHeroesWithMatchName(Mockito.anyString()))
                .thenReturn(Mockito.anyList());

        Assertions.assertEquals(superHeroService.findSuperHeroesWithMatchInName(""), emptyList);

    }

    private List<SuperHero> getSuperHeroes() {
        List<SuperHero> superheroList = new ArrayList<SuperHero>();
        superheroList.add(new SuperHero(1L, "Pablo", "Spiderman"));
        superheroList.add(new SuperHero(2L, "Martin", "Superman"));
        superheroList.add(new SuperHero(3L, "Manolo", "Manolito el dormido"));
        return superheroList;
    }

}
