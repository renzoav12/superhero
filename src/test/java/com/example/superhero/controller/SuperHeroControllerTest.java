package com.example.superhero.controller;

import com.example.superhero.dto.SuperHeroDTO;
import com.example.superhero.dto.SuperHeroesDTO;
import com.example.superhero.exception.SuperHeroNotFoundException;
import com.example.superhero.facade.SuperHeroFacade;
import com.example.superhero.model.SuperHero;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SuperHeroController.class)
public class SuperHeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperHeroFacade superHeroFacade;

    private ObjectMapper mapper;

    @Before
    public void setup() throws Exception {
        mapper = new ObjectMapper();
    }

    @Test
    @SneakyThrows
    public void whenGetAllSuperHeroesThenReturnListOfSuperHeroes() {
        List<SuperHeroDTO> superHeroes = getHeroes()
                .stream().map(superHero -> new SuperHeroDTO(superHero.getId(),superHero.getFirstName(),
                        superHero.getSuperHeroName())).collect(Collectors.toList());

        // return array of superheroes size 3
        Mockito.when(superHeroFacade.getSuperHeroes()).thenReturn(new SuperHeroesDTO(superHeroes));


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/superHeroes")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        SuperHeroesDTO response = mapper.readValue(resultActions.andReturn()
                .getResponse().getContentAsString(), SuperHeroesDTO.class);

        Assertions.assertEquals(response.getSuperHeroes().size(), 3);
        Assertions.assertEquals(response.getSuperHeroes().get(0).getSuperHeroName(), "Spiderman");
    }


    @Test
    @SneakyThrows
    public void whenGeSuperHeroByIdThenReturnAnSuperHero() {
        Mockito.when(superHeroFacade.getSuperHeroById(Mockito.anyLong()))
                .thenReturn(new SuperHeroDTO(1l, "Peter", "Spiderman"));


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/superHeroes/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        SuperHeroDTO response = mapper.readValue(resultActions.andReturn()
                .getResponse().getContentAsString(), SuperHeroDTO.class);

        Assertions.assertEquals(response.getFirstName(), "Peter");
        Assertions.assertEquals(response.getSuperHeroName(), "Spiderman");
    }

    @Test
    @SneakyThrows
    public void whenGeSuperHeroFilterByNameThenReturnSuperHerMatchNameFilter() {

        List<SuperHeroDTO> superHeroes = getHeroes()
                .stream().map(superHero -> new SuperHeroDTO(superHero.getId(),superHero.getFirstName(),
                        superHero.getSuperHeroName())).collect(Collectors.toList());

        Mockito.when(superHeroFacade.getSuperHeroesByName(Mockito.anyString()))
                .thenReturn(new SuperHeroesDTO(superHeroes));


        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/superHeroes/filterBy/man")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        SuperHeroesDTO response = mapper.readValue(resultActions.andReturn()
                .getResponse().getContentAsString(), SuperHeroesDTO.class);

        // 3 superHeroes contains man
        Assertions.assertEquals(response.getSuperHeroes().size(), 3);
    }

    @Test
    @SneakyThrows
    public void whenGeSuperHeroByIdAndNotExistsThenReturnNotFound() {

        Mockito.when(superHeroFacade.getSuperHeroById(Mockito.anyLong()))
                .thenThrow(new SuperHeroNotFoundException(Mockito.anyLong()));

       mockMvc.perform(MockMvcRequestBuilders.get("/api/superHeroes/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }


    private List<SuperHero> getHeroes() {
        List<SuperHero> superheroList = new ArrayList<SuperHero>();
        superheroList.add(new SuperHero(1L, "Pablo", "Spiderman"));
        superheroList.add(new SuperHero(2L, "Martin", "Superman"));
        superheroList.add(new SuperHero(3L, "Manolo", "Manolito el dormido"));
        return superheroList;
    }

}
