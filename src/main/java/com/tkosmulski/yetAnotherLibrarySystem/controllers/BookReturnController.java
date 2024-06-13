package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.dtos.SafeBookBorrowDTO;
import com.tkosmulski.yetAnotherLibrarySystem.dtos.SafeBookReturnDTO;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.models.BookReturn;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookAuthorService;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookBorrowService;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookReturnService;
import com.tkosmulski.yetAnotherLibrarySystem.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookReturns")
public class BookReturnController {
    Logger logger = LoggerFactory.getLogger(BookReturnController.class);
    BookReturnService bookReturnService;

    @Autowired
    public BookReturnController(BookReturnService bookReturnService) {
        this.bookReturnService = bookReturnService;
    }




    @PostMapping("/borrowId/{borrowId}")
    public ResponseEntity<SafeBookReturnDTO> returnBorrow(@PathVariable Long borrowId) {
        logger.info(String.format("Request for returning borrow with id %d.", borrowId));
        return new ResponseEntity<>(new SafeBookReturnDTO(bookReturnService.bookReturn(borrowId)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SafeBookReturnDTO>> get() {
        logger.info("Request for getting all bookReturns.");
        return new ResponseEntity<>(SafeBookReturnDTO.from(bookReturnService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SafeBookReturnDTO> getById(@PathVariable Long id) {
        logger.info(String.format("Request for getting bookReturn with id %d.", id));
        return new ResponseEntity<>(new SafeBookReturnDTO(bookReturnService.findByIdOrThrow(id)), HttpStatus.OK);
    }

}

