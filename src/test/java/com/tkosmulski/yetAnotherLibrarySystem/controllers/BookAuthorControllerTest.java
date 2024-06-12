package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.AuthorRepository;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.BookRepository;
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
public class BookAuthorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

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
                    \"id\" : 1,
                    \"title\" : "Krzyzacy",
                    \"isbn\" : "9780331623468",
                    \"available\" : 10,
                    \"total\" : 10
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(payload));

    }


    @Test
    public void whenPosting_thenIsCreated() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/booksAuthors/bookId/1/authorId/1"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPostingNonexistentBook_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/booksAuthors/bookId/-1/authorId/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenPostingNonexistentAuthor_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/booksAuthors/bookId/1/authorId/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }


    @Test
    public void whenDeleting_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/authors"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/booksAuthors/bookId/1/authorId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenDeletingNonexistentBook_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/booksAuthors/bookId/-1/authorId/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenDeletingNonexistentAuthor_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/booksAuthors/bookId/1/authorId/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

}

