package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.dtos.SafeBookBorrowDTO;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookAuthorService;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookBorrowService;
import com.tkosmulski.yetAnotherLibrarySystem.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookBorrows")
public class BookBorrowController {
    Logger logger = LoggerFactory.getLogger(BookBorrowController.class);
    BookBorrowService bookBorrowService;
    UserService userService;

    @Autowired
    public BookBorrowController(BookBorrowService bookBorrowService, UserService userService) {
        this.bookBorrowService = bookBorrowService;
        this.userService = userService;
    }

    @PostMapping("/borrow/bookId/{bookId}/userId/{userId}/duration/{duration}")
    public ResponseEntity<SafeBookBorrowDTO> borrow(@PathVariable Long bookId, @PathVariable Long userId, @PathVariable Integer duration) {
        logger.info(String.format("Request for borrowing book with id %d by user with id %d for %d days.", bookId, userId, duration));
        return new ResponseEntity<>(new SafeBookBorrowDTO(bookBorrowService.borrow(bookId, userId, duration)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SafeBookBorrowDTO>> get() {
        logger.info("Request for getting all bookBorrows.");
        return new ResponseEntity<>(SafeBookBorrowDTO.from(bookBorrowService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SafeBookBorrowDTO> getById(@PathVariable Long id) {
        logger.info(String.format("Request for getting bookBorrow with id %d.", id));
        return new ResponseEntity<>(new SafeBookBorrowDTO(bookBorrowService.findByIdOrThrow(id)), HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<SafeBookBorrowDTO>> getByUserId(@PathVariable Long userId) {
        logger.info(String.format("Request for getting bookBorrows of user with id %d.", userId));
        return new ResponseEntity<>(SafeBookBorrowDTO.from(userService.findByIdOrThrow(userId).getBorrowedBooks()), HttpStatus.OK);
    }
}
