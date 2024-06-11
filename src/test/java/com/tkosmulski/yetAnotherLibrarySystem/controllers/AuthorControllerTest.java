package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.repositories.AuthorRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorRepository authorRepository;

    public void postDefault() throws Exception {
        String payload = """
                {
                    \"id\" : 1,
                    \"name\" : "Henryk",
                    \"surname\" : "Sienkiewicz"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON).content(payload));

        payload = """
                {
                    \"id\" : 2,
                    \"name\" : "Edited",
                    \"surname\" : "Sienkiewicz"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON).content(payload));

        payload = """
                {
                    \"id\" : 3,
                    \"name\" : "Deleted",
                    \"surname\" : "Sienkiewicz"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON).content(payload));

    }

    @Test
    public void whenGettingAuthors_thenIsOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingAuthorById_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/id/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingNonexistingAuthorById_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors/id/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPostingAuthor_thenIsCreated() throws Exception {
        String payload = """
                {
                    \"name\" : "Adam",
                    \"surname\" : "Roman"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }


    @Test
    public void whenPostingExistingAuthor_thenIsConflict() throws Exception {
        postDefault();
        String payload = """
                {
                    \"name\" : "Henryk",
                    \"surname\" : "Sienkiewicz"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenDeletingAuthorById_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/id/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenDeletingNonexistingAuthorById_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/id/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPatchingAuthorById_thenIsOk() throws Exception {
        postDefault();
        String payload = """
                {
                    \"name\" : "Adam",
                    \"surname\" : "Mickiewicz"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/authors/id/2")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPatchingNonexistingAuthorById_thenIsNotFound() throws Exception {
        String payload = """
                {
                    \"name\" : "Adam",
                    \"surname\" : "Mickiewicz"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/authors/id/-1")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }
}
