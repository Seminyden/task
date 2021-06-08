package com.gmail.seminyden.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PlanetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllPlanetsCheckStatus() throws Exception {
        mockMvc.perform(get("/planets?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testGetAllPlanetsCheckTotalElements() throws Exception {
        mockMvc.perform(get("/planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalElements\":17")));
    }


    @Test
    void testGetAllPlanetsCheckTotalPages() throws Exception {
        mockMvc.perform(get("/planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalPages\":2")));
    }


    @ParameterizedTest
    @ValueSource(ints = {14,15,16,17,18,19,20,21,22,23})
    void testGetAllPlanetsCheckContent(int i) throws Exception {
        mockMvc.perform(get("/planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"id\":" + i)));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testGetAllPlanetsWhenNotFoundCheckStatus() throws Exception {
        mockMvc.perform(get("/planets?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testGetAllPlanetsWhenNotFoundCheckTotalElements() throws Exception {
        mockMvc.perform(get("/planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalElements\":0")));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testGetAllPlanetsWhenNotFoundCheckTotalPages() throws Exception {
        mockMvc.perform(get("/planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalPages\":0")));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreatePlanetCheckStatus() throws Exception {
        mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON)
                                                  .content("{\"name\":\"planet_test\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreatePlanetCheckContent() throws Exception {
        mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON)
                                                  .content("{\"name\":\"planet_test\"}"))
                .andDo(print())
                .andExpect(content().string(containsString("\"name\":\"planet_test\",\"lord\":null")));
    }


    @Test
    void testCreatePlanetWithEmptyNameCheckStatus() throws Exception {
        mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON)
                                                  .content("{\"name\":\"  \"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void testCreatePlanetWithEmptyNameCheckContent() throws Exception {
        mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON)
                                                  .content("{\"name\":\"  \"}"))
                .andDo(print())
                .andExpect(content().string(containsString("Planet name mustn't be blank")));
    }


    @ParameterizedTest
    @ValueSource(ints = {14,15,26,27,30})
    void testUpdatePlanetNameCheckStatus(int i) throws Exception {
        mockMvc.perform(put("/planets/" + i).contentType(MediaType.APPLICATION_JSON)
                                                      .content("{\"name\":\"planet_update_test\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @ValueSource(ints = {14,15,16,17,28,29,30})
    void testUpdatePlanetNameCheckContent(int i) throws Exception {
        mockMvc.perform(put("/planets/" + i).contentType(MediaType.APPLICATION_JSON)
                                                      .content("{\"name\":\"planet_update_test\"}"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":" + i + ",\"name\":\"planet_update_test\"")));
    }


    @Test
    void testUpdatePlanetNameWithEmptyNameCheckStatus() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"  \"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testUpdatePlanetNameWithEmptyNameCheckContent() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"  \"}"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":14,\"name\":\"planet1\",\"lord\":{\"id\":1,\"name\":\"lord1\",\"age\":11}}")));
    }


    @Test
    void testUpdatePlanetLordCheckStatus() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"lord\":{\"id\":\"2\"}}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testUpdatePlanetLordCheckContent() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"lord\":{\"id\":\"2\"}}"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":14,\"name\":\"planet1\",\"lord\":{\"id\":2,\"name\":\"lord2\",\"age\":15}}")));
    }


    @Test
    void testUpdatePlanetLordWithInvalidLordIdCheckStatus() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"lord\":{\"id\":\"123\"}}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testUpdatePlanetLordWithInvalidLordIdCheckContent() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"lord\":{\"id\":\"123\"}}"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":14,\"name\":\"planet1\",\"lord\":{\"id\":1,\"name\":\"lord1\",\"age\":11}}")));
    }


    @Test
    void testUpdatePlanetNameAndLordCheckStatus() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"planet_update_test\",\"lord\":{\"id\":2}}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testUpdatePlanetNameAndLordCheckContent() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"planet_update_test\",\"lord\":{\"id\":2}}"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":14,\"name\":\"planet_update_test\",\"lord\":{\"id\":2,\"name\":\"lord2\",\"age\":15}}")));
    }


    @Test
    void testUpdatePlanetNameAndLordWhenNameIsEmptyCheckStatus() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"  \",\"lord\":{\"id\":2}}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testUpdatePlanetNameAndLordWhenNameIsEmptyCheckContent() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"  \",\"lord\":{\"id\":2}}"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":14,\"name\":\"planet1\",\"lord\":{\"id\":2,\"name\":\"lord2\",\"age\":15}}")));
    }


    @Test
    void testUpdatePlanetNameAndLordWhenLordIdIsEmptyCheckStatus() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"planet_update_test\",\"lord\":{\"id\":-1}}"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testUpdatePlanetNameAndLordWhenLordIdIsEmptyCheckContent() throws Exception {
        mockMvc.perform(put("/planets/14").contentType(MediaType.APPLICATION_JSON)
                                                    .content("{\"name\":\"planet_update_test\",\"lord\":{\"id\":-1}}"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":14,\"name\":\"planet_update_test\",\"lord\":{\"id\":1,\"name\":\"lord1\",\"age\":11}}")));
    }


    @Test
    void testDeletePlanetCheckStatus() throws Exception {
        mockMvc.perform(delete("/planets/15"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testDeletePlanetCheckContent() throws Exception {
        mockMvc.perform(delete("/planets/15"))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":15,\"name\":\"planet2\",\"lord\":{\"id\":1,\"name\":\"lord1\",\"age\":11}}")));
    }


    @Test
    void testDeletePlanetWhenNotFoundCheckStatus() throws Exception {
        mockMvc.perform(delete("/planets/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}