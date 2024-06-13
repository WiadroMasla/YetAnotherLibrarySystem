package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import com.tkosmulski.yetAnotherLibrarySystem.services.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    Logger logger = LoggerFactory.getLogger(AuthorController.class);
    AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        logger.info("Request for getting all authors.");
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Author> add(@RequestBody Author author) {
        logger.info("Request for adding author.");
        return new ResponseEntity<>(authorService.add(author), HttpStatus.CREATED);
    }
    @GetMapping("/id/{id}")
    ResponseEntity<Author> getById(@PathVariable Long id) {
        logger.info(String.format("Request for getting author with id %d.", id));
        return new ResponseEntity<>(authorService.findByIdOrThrow(id), HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    ResponseEntity<Author> deleteById(@PathVariable Long id) {
        logger.info(String.format("Request for deleting author with id %d.", id));
        return new ResponseEntity<>(authorService.deleteById(id), HttpStatus.OK);
    }

    @PatchMapping("/id/{id}")
    ResponseEntity<Author> patch(@PathVariable Long id, @RequestBody Author author) {
        logger.info(String.format("Request for editing author with id %d.", id));
        return new ResponseEntity<>(authorService.edit(author, id), HttpStatus.OK);
    }
}
