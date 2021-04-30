package com.example.superhero.controller;

import com.example.superhero.SuperheroApplication;
import com.example.superhero.facade.SuperHeroFacade;
import com.example.superhero.model.SuperHero;
import com.example.superhero.repository.SuperHeroRepository;
import com.example.superhero.service.SuperHeroService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(SuperheroApplication.class)
public class SuperHeroControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SuperHeroFacade superHeroFacade;

    @InjectMocks
    private SuperHeroController superHeroController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(superHeroController).build();
    }

    @Test
    private void getAllSuperHeroes() {

    }

    @Test
    private void getSuperHeroById() {

    }


    @Test
    private void updateSuperHero() {

    }


}
