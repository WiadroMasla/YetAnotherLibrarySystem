package com.tkosmulski.yetAnotherLibrarySystem.controllers;


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
public class BookBorrowControllerTest {
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


    }

    @Test
    public void whenBorrowing_thenIsCreated() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookBorrows/borrow/bookId/1/userId/1/duration/7"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenBorrowingNegativeDuration_thenIsConflict() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookBorrows/borrow/bookId/1/userId/1/duration/-1"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenBorrowingWithNonexistentUser_thenIsNotFound() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookBorrows/borrow/bookId/1/userId/-1/duration/7"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenBorrowingWithNonexistentBook_thenIsNotFound() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookBorrows/borrow/bookId/-1/userId/1/duration/7"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenBorrowingWithNotAvailableBook_thenIsConflict() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookBorrows/borrow/bookId/2/userId/1/duration/7"))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGettingBookBorrows_thenIsOk() throws Exception {
        postDefault();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookBorrows"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingBookBorrowById_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookBorrows"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookBorrows/id/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingNonexistingBookBorrowById_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/id/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingBookBorrowsByUserId_thenIsOk() throws Exception {
        postDefault();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookBorrows/userId/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void whenGettingNonexistingBookBorrowsByUserId_thenIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookBorrows/userId/-1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

}
