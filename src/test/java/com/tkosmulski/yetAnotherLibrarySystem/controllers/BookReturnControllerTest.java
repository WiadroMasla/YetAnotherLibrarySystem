package com.tkosmulski.yetAnotherLibrarySystem.controllers;


import com.tkosmulski.yetAnotherLibrarySystem.repositories.BookRepository;
import org.junit.jupiter.api.Order;
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
public class BookReturnControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    public void postDefault() throws Exception {
        String payload = """
                {
                    \"id\" : 1,
                    \"title\" : "Krzyzacy",
                    \"isbn\" : "9780331623468",
                    \"available\" : 10,
                    \"total\" : 10
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(payload));
        payload = """
                {
                    \"id\" : 2,
                    \"title\" : "Księga Dżungli",
                    \"isbn\" : "000000000000"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(payload));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookBorrows/borrow/bookId/1/userId/1/duration/7"));

    }

    @Test
    @Order(1)
    public void whenReturning_thenIsCreated() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookReturns/borrowId/1"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    public void whenReturningAlreadyReturned_thenIsConflict() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookReturns/borrowId/1"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(3)
    public void whenReturningNonexistentBorrow_thenIsNotFound() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookReturns/borrowId/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(4)
    public void whenGettingReturns_thenIsOk() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookReturns"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(5)
    public void whenGettingReturnWithId_thenIsOk() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookReturns/id/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(6)
    public void whenGettingReturnWithNonexistentId_thenIsNotFound() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookReturns/id/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
