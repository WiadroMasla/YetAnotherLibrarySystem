package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.services.AuthorService;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Book> add(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.add(book), HttpStatus.CREATED);
    }
    @GetMapping("/id/{id}")
    ResponseEntity<Book> getById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.findByIdOrThrow(id), HttpStatus.OK);
    }

    @GetMapping("/isbn/{isbn}")
    ResponseEntity<Book> getByIsbn(@PathVariable String isbn) {
        return new ResponseEntity<>(bookService.findByIsbnOrThrow(isbn), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    ResponseEntity<List<Book>> getByTitle(@PathVariable String title) {
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<Book> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.deleteById(id), HttpStatus.OK);
    }

    @PatchMapping("/id/{id}")
    ResponseEntity<Book> patch(@PathVariable Long id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.edit(book, id), HttpStatus.OK);
    }

    @PostMapping("/id/{id}/amountChange/{amount}")
    ResponseEntity<Book> changeAmount(@PathVariable Long id, @PathVariable Long amount) {
        return new ResponseEntity<>(bookService.changeAmount(id, amount), HttpStatus.OK);
    }
}