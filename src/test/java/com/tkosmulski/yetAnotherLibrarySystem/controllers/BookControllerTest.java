package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.BookRepository;
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
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository bookRepository;

    public void postDefault() throws Exception {
        String payload = """
                {
                    \"id\" : 1,
                    \"title\" : "Krzyzacy",
                    \"isbn\" : "9780331623468"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(payload));
        payload = """
                {
                    \"id\" : 2,
                    \"title\" : "To be edited",
                    \"isbn\" : "1231231231231"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(payload));

        payload = """
                {
                    \"id\" : 3,
                    \"title\" : "To be deleted",
                    \"isbn\" : "333333333"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(payload));
    }

    @Test
    public void whenChangingAmount_thenIsOk() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/id/1/amountChange/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenChangingAmountForNonexistingBook_thenIsNotFound() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/id/-1/amountChange/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenSubstractingAmountTooMuch_thenIsConflict() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books/id/1/amountChange/-10000"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGettingBooks_thenIsOk() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingBookById_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/id/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingNonexistingAuthorById_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/id/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingBookByIsbn_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/isbn/9780331623468"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingNonexistingBookByIsbn_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/isbn/1111111111111"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingBooksByTitle_thenIsOk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/title/Dziady"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPostingBook_thenIsCreated() throws Exception {
        postDefault();
        String payload = """
                {
                    \"title\" : "Ogniem i Mieczem",
                    \"isbn\" : "9781911414221"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }


    @Test
    public void whenPostingExistingBook_thenIsConflict() throws Exception {
        postDefault();
        String payload = """
                {
                    \"title\" : "Krzyżacy",
                    \"isbn\" : "9780331623468"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenDeletingBookById_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/id/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenDeletingNonexistingBookById_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/id/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPatchingBookById_thenIsOk() throws Exception {
        postDefault();
        String payload = """
                {
                    \"title\" : "Dziady",
                    \"isbn\" : "9781911414001"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/books/id/2")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPatchingNonexistingAuthorById_thenIsNotFound() throws Exception {
        String payload = """
                {
                    \"title\" : "Dziady",
                    \"isbn\" : "9781911414001"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/books/id/-1")
                        .contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }
}