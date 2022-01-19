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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-before-each.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/clear-tables.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class LordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllLordsCheckStatus() throws Exception {
        mockMvc.perform(get("/lords?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testGetAllLordsCheckTotalElements() throws Exception {
        mockMvc.perform(get("/lords?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalElements\":13")));
    }


    @Test
    void testGetAllLordsCheckTotalPages() throws Exception {
        mockMvc.perform(get("/lords?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalPages\":2")));
    }


    @Test
    @Sql(value = {"/clear-tables.sql"})
    void testGetAllLordsWhenLordsNotFoundCheckStatus() throws Exception {
        mockMvc.perform(get("/lords?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Sql(value = {"/clear-tables.sql"})
    void testGetAllLordsWhenLordsNotFoundCheckTotalElements() throws Exception {
        mockMvc.perform(get("/lords?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalElements\":0")));
    }


    @Test
    @Sql(value = {"/clear-tables.sql"})
    void testGetAllLordsWhenLordsNotFoundCheckTotalPages() throws Exception {
        mockMvc.perform(get("/lords?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalPages\":0")));
    }


    @Test
    void testGetAllLordsWithoutPlanetsCheckStatus() throws Exception {
        mockMvc.perform(get("/lords/without_planets?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testGetAllLordsWithoutPlanetsCheckTotalElements() throws Exception {
        mockMvc.perform(get("/lords/without_planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalElements\":4")));
    }


    @Test
    void testGetAllLordsWithoutPlanetsCheckTotalPages() throws Exception {
        mockMvc.perform(get("/lords/without_planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalPages\":1")));
    }


    @ParameterizedTest
    @ValueSource(ints = {6,10,11,13})
    void testGetAllLordsWithoutPlanetsCheckContent(int i) throws Exception {
        mockMvc.perform(get("/lords/without_planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"id\":" + i + ",\"name\":\"lord" + i + "\"")));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testGetAllLordsWithoutPlanetsWhenNotFoundCheckStatus() throws Exception {
        mockMvc.perform(get("/lords/without_planets?page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testGetAllLordsWithoutPlanetsWhenNotFoundCheckTotalElements() throws Exception {
        mockMvc.perform(get("/lords/without_planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalElements\":0")));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testGetAllLordsWithoutPlanetsWhenNotFoundCheckTotalPages() throws Exception {
        mockMvc.perform(get("/lords/without_planets?page=0&size=10"))
                .andDo(print())
                .andExpect(content().string(containsString("\"totalPages\":0")));
    }


    @Test
    void testGetTopTenYoungestLordsCheckStatus() throws Exception {
        mockMvc.perform(get("/lords/youngest"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,10,12,13})
    void testGetTopTenYoungestLordsCheckContent(int i) throws Exception {
        mockMvc.perform(get("/lords/youngest"))
                .andDo(print())
                .andExpect(content().string(containsString("\"id\":" + i + ",\"name\":\"lord" + i + "\"")));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testGetTopTenYoungestLordsWhenNotFoundCheckStatus() throws Exception {
        mockMvc.perform(get("/lords/youngest"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreateLordCheckStatus() throws Exception {
        mockMvc.perform(post("/lords").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"lord_test\",\"age\":100}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreateLordCheckContent() throws Exception {
        mockMvc.perform(post("/lords").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"lord_test\",\"age\":100}"))
                .andDo(print())
                .andExpect(content().string(containsString("\"name\":\"lord_test\",\"age\":100,\"planets\":null")));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreateLordWithEmptyNameCheckStatus() throws Exception {
        mockMvc.perform(post("/lords").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"  \",\"age\":100}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreateLordWithEmptyNameCheckContent() throws Exception {
        mockMvc.perform(post("/lords").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"  \",\"age\":100}"))
                .andDo(print())
                .andExpect(content().string(containsString("Lord name mustn't be blank")));
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreateLordWithEmptyAgeCheckStatus() throws Exception {
        mockMvc.perform(post("/lords").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"lord_test\",\"age\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Sql(value = "/clear-tables.sql")
    void testCreateLordWithEmptyAgeCheckContent() throws Exception {
        mockMvc.perform(post("/lords").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"name\":\"lord_test\",\"age\":\"\"}"))
                .andDo(print())
                .andExpect(content().string(containsString("Lord age must be greater than 0")));
    }
}