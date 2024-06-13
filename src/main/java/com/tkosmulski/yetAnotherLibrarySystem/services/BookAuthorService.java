package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookAuthorService {
    Logger logger = LoggerFactory.getLogger(BookAuthorService.class);
    BookService bookService;
    AuthorService authorService;

    @Autowired
    public BookAuthorService(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public Book addAuthor(Long bookId, Long authorId) {
        Book book = bookService.findByIdOrThrow(bookId);
        Author author = authorService.findByIdOrThrow(authorId);
        book.getAuthors().add(author);
        bookService.edit(book, bookId);
        return book;
    }

    public Book removeAuthor(Long bookId, Long authorId) {
        Book book = bookService.findByIdOrThrow(bookId);
        Author author = authorService.findByIdOrThrow(authorId);
        book.getAuthors().remove(author);
        bookService.edit(book, bookId);
        return book;
    }

}
