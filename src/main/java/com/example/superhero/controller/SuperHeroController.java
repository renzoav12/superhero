package com.example.superhero.controller;

import com.example.superhero.aop.LogExecutionTime;
import com.example.superhero.dto.SuperHeroDTO;
import com.example.superhero.dto.SuperHeroRequest;
import com.example.superhero.dto.SuperHeroesDTO;
import com.example.superhero.facade.SuperHeroFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/superHeroes")
public class SuperHeroController {

    private final SuperHeroFacade superHeroFacade;

    @LogExecutionTime
    @PostMapping
    public ResponseEntity<SuperHeroDTO> createSuperHero(@RequestBody SuperHeroRequest superheroRequest) {
          SuperHeroDTO superHeroDTO = superHeroFacade.createSuperHero(superheroRequest);
          return new ResponseEntity<>(superHeroDTO, HttpStatus.CREATED);
    }

    @LogExecutionTime
    @PutMapping("/{superHeroId}")
    public ResponseEntity<SuperHeroDTO> updateSuperhero(@PathVariable Long superHeroId,
                                     @Validated @RequestBody SuperHeroRequest superheroRequest) {
        superHeroFacade.updateSuperHero(superHeroId, superheroRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @LogExecutionTime
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SuperHeroDTO>> getSuperheroes() {
        SuperHeroesDTO superHeroesDTO = superHeroFacade.getSuperHeroes();
        return new ResponseEntity(superHeroesDTO, HttpStatus.OK);
    }

    @LogExecutionTime
    @GetMapping("/filterBy/{filterName}")
    public ResponseEntity<List<SuperHeroDTO>> getSuperheroes(@PathVariable String filterName) {
        SuperHeroesDTO superHeroesDTO = superHeroFacade.getSuperHeroesByName(filterName.toLowerCase());
        return new ResponseEntity(superHeroesDTO, HttpStatus.OK);
    }

    @LogExecutionTime
    @GetMapping("/{superheroId}")
    public ResponseEntity<SuperHeroDTO> getSuperheroById(@PathVariable Long superheroId) {
        SuperHeroDTO superHeroDTO = superHeroFacade.getSuperHeroById(superheroId);
        return new ResponseEntity(superHeroDTO, HttpStatus.OK);
    }

    @LogExecutionTime
    @DeleteMapping("/{superheroId}")
    public ResponseEntity<String> deleteSuperhero(@PathVariable Long superheroId) {
        superHeroFacade.deleteSuperHero(superheroId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
