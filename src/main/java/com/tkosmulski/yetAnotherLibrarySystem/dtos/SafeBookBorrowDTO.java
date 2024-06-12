package com.tkosmulski.yetAnotherLibrarySystem.dtos;

import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.models.BookBorrow;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SafeBookBorrowDTO {
    Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date borrowDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date dueDate;

    SafeUserDTO userDTO;

    Book book;

    public SafeBookBorrowDTO(BookBorrow bookBorrow) {
        id = bookBorrow.getId();
        borrowDate = bookBorrow.getBorrowDate();
        dueDate = bookBorrow.getDueDate();
        book = bookBorrow.getBorrowedBook();
        userDTO = new SafeUserDTO(bookBorrow.getBorrower());
    }

    public static List<SafeBookBorrowDTO> from(Collection<BookBorrow> bookBorrows) {
        return bookBorrows.stream().map(SafeBookBorrowDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public SafeUserDTO getUserDTO() {
        return userDTO;
    }

    public Book getBook() {
        return book;
    }
}
