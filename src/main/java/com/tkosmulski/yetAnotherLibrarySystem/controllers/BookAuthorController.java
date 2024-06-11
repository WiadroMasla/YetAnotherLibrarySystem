package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booksAuthors")
public class BookAuthorController {
    BookAuthorService bookAuthorService;

    @Autowired
    public BookAuthorController(BookAuthorService bookAuthorService) {
        this.bookAuthorService = bookAuthorService;
    }

    @PostMapping("/bookId/{bookId}/authorId/{authorId}")
    public ResponseEntity<Book> add(@PathVariable Long bookId, @PathVariable Long authorId) {
        return new ResponseEntity<>(bookAuthorService.addAuthor(bookId, authorId), HttpStatus.CREATED);
    }

    @DeleteMapping("/bookId/{bookId}/authorId/{authorId}")
    public ResponseEntity<Book> delete(@PathVariable Long bookId, @PathVariable Long authorId) {
        return new ResponseEntity<>(bookAuthorService.removeAuthor(bookId, authorId), HttpStatus.OK);
    }
}
