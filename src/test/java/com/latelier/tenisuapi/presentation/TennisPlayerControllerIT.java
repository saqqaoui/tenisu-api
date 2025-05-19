package com.latelier.tenisuapi.presentation;

import com.latelier.tenisuapi.TenisuApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TennisPlayerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE = "/api/tennis-player";

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(BASE + "/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(get(BASE + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value("Player-A"))
                .andExpect(jsonPath("$.country.code").value("USA"));
    }

    @Test
    void getByIdNotFound() throws Exception {
        mockMvc.perform(get(BASE + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllByGenderFemale() throws Exception {
        mockMvc.perform(get(BASE + "/all-by-gender").param("gender", "F"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void getAllByGenderMaleNotFound() throws Exception {
        mockMvc.perform(get(BASE + "/all-by-gender").param("gender", "M"))
                .andExpect(status().isNotFound());
    }
}
