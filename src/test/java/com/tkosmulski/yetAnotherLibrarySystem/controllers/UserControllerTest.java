package com.tkosmulski.yetAnotherLibrarySystem.controllers;


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
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    public void postDefault() throws Exception {
        String payload = """
                {
                    \"id\" : 1,
                    \"email\" : "normal@gmail.com",
                    \"password\" : "passwd"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON).content(payload));

        payload = """
                {
                    \"id\" : 2,
                    \"email\" : "toBeEdited@gmail.com",
                    \"password\" : "passwd"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON).content(payload));

    }

    @Test
    public void whenGettingUsers_thenIsOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingUserById_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/id/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingNonexistingUserById_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/id/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPostingUser_thenIsCreated() throws Exception {
        String payload = """
                {
                    \"email\" : "Roman@gmail.com",
                    \"password\" : "passwd"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }


    @Test
    public void whenPostingExistingUser_thenIsConflict() throws Exception {
        postDefault();
        String payload = """
                {
                    \"id\" : 1,
                    \"email\" : "normal@gmail.com",
                    \"password\" : "passwd"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }



    @Test
    public void whenPatchingUser_thenIsOk() throws Exception {
        postDefault();
        String payload = """
                {   \"id\" : 2,
                    \"email\" : "Nowy@gmail.com",
                    \"password\" : "passwd"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPatchingNonexistingUser_thenIsNotFound() throws Exception {
        String payload = """
                {
                    \"id\" : -1,
                    \"email\" : "Nowy@gmail.com",
                    \"password\" : "passwd"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }
}
