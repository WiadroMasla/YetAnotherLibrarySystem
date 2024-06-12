package com.tkosmulski.yetAnotherLibrarySystem.controllers;

import com.tkosmulski.yetAnotherLibrarySystem.dtos.SafeBookBorrowDTO;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookAuthorService;
import com.tkosmulski.yetAnotherLibrarySystem.services.BookBorrowService;
import com.tkosmulski.yetAnotherLibrarySystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookBorrows")
public class BookBorrowController {
    BookBorrowService bookBorrowService;
    UserService userService;

    @Autowired
    public BookBorrowController(BookBorrowService bookBorrowService, UserService userService) {
        this.bookBorrowService = bookBorrowService;
        this.userService = userService;
    }

    @PostMapping("/borrow/bookId/{bookId}/userId/{userId}/duration/{duration}")
    public ResponseEntity<SafeBookBorrowDTO> borrow(@PathVariable Long bookId, @PathVariable Long userId, @PathVariable Integer duration) {
        return new ResponseEntity<>(new SafeBookBorrowDTO(bookBorrowService.borrow(bookId, userId, duration)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SafeBookBorrowDTO>> get() {
        return new ResponseEntity<>(SafeBookBorrowDTO.from(bookBorrowService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<SafeBookBorrowDTO> getById(@PathVariable Long id) {
        return new ResponseEntity<>(new SafeBookBorrowDTO(bookBorrowService.findByIdOrThrow(id)), HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<SafeBookBorrowDTO>> getByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(SafeBookBorrowDTO.from(userService.findByIdOrThrow(userId).getBorrowedBooks()), HttpStatus.OK);
    }
}
