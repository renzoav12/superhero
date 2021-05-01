package com.example.superhero.integration;


import com.example.superhero.dto.SuperHeroDTO;
import com.example.superhero.dto.SuperHeroRequest;
import com.example.superhero.repository.SuperHeroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrencyIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private SuperHeroRepository repository;

    private ObjectMapper mapper;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        mapper = new ObjectMapper();
    }

    @Test
    @SneakyThrows
    public void testCreateSuperHero() {
        ResultActions resultActions = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/superHeroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPostBody())).andExpect(status().isCreated());

        SuperHeroDTO response = mapper.readValue(resultActions.andReturn()
                .getResponse().getContentAsString(), SuperHeroDTO.class);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/superHeroes/{superheroId}", response.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @SneakyThrows
    private String createPostBody() {
        SuperHeroRequest superHeroRequest = SuperHeroRequest.builder()
                .firstName("Jose Heroe")
                .superHeroName("spiderman")
                .build();
        return mapper.writeValueAsString(superHeroRequest);
    }
}
