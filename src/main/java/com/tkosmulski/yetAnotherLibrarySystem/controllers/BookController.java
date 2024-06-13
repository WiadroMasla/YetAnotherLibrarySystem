package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.services.AuthorService;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    Logger logger = LoggerFactory.getLogger(BookController.class);
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        logger.info("Request for getting all books.");
        return new ResponseEntity<>(bookService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Book> add(@RequestBody Book book) {
        logger.info("Request for adding a new book.");
        return new ResponseEntity<>(bookService.add(book), HttpStatus.CREATED);
    }
    @GetMapping("/id/{id}")
    ResponseEntity<Book> getById(@PathVariable Long id) {
        logger.info(String.format("Request for getting book with id %d.", id));
        return new ResponseEntity<>(bookService.findByIdOrThrow(id), HttpStatus.OK);
    }

    @GetMapping("/isbn/{isbn}")
    ResponseEntity<Book> getByIsbn(@PathVariable String isbn) {
        logger.info(String.format("Request for getting book with isbn %s.", isbn));
        return new ResponseEntity<>(bookService.findByIsbnOrThrow(isbn), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    ResponseEntity<List<Book>> getByTitle(@PathVariable String title) {
        logger.info(String.format("Request for getting books with title %s.", title));
        return new ResponseEntity<>(bookService.findByTitle(title), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<Book> deleteById(@PathVariable Long id) {
        logger.info(String.format("Request for deleting book with id %d.", id));
        return new ResponseEntity<>(bookService.deleteById(id), HttpStatus.OK);
    }

    @PatchMapping("/id/{id}")
    ResponseEntity<Book> patch(@PathVariable Long id, @RequestBody Book book) {
        logger.info(String.format("Request for editing book with id %d.", id));
        return new ResponseEntity<>(bookService.edit(book, id), HttpStatus.OK);
    }

    @PostMapping("/id/{id}/amountChange/{amount}")
    ResponseEntity<Book> changeAmount(@PathVariable Long id, @PathVariable Long amount) {
        logger.info(String.format("Request for changing amount of book with id %d by %d.", id, amount));
        return new ResponseEntity<>(bookService.changeAmount(id, amount), HttpStatus.OK);
    }
}