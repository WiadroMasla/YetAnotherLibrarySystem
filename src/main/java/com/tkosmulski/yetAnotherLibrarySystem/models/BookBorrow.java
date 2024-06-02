package com.tkosmulski.yetAnotherLibrarySystem.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
public class BookBorrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date borrowDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date dueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User borrower;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book borrowedBook;

    @OneToOne(mappedBy = "borrow")
    BookReturn bookReturn;

    public BookBorrow() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public Book getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(Book borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    public BookReturn getBookReturn() {
        return bookReturn;
    }

    public void setBookReturn(BookReturn bookReturn) {
        this.bookReturn = bookReturn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookBorrow that = (BookBorrow) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
