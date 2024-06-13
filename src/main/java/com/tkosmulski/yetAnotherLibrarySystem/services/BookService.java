package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementAlreadyExistsException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.IllegalNegativeValueException;
import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    Logger logger = LoggerFactory.getLogger(BookService.class);
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book findByIdOrThrow(Long id) {
        logger.info(String.format("Finding book with id %d.", id));
        return bookRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Book", "id", id)
        );
    }

    public Book findByIsbnOrThrow(String isbn) {
        logger.info(String.format("Finding book with isbn %s.", isbn));
        return bookRepository.findByIsbn(isbn).orElseThrow(
                () -> new ElementNotFoundException("Book", "isbn", isbn)
        );
    }

    public List<Book> findByTitle(String title) {
        logger.info(String.format("Finding book with id %s.", title));
        return bookRepository.findByTitle(title);
    }

    public List<Book> findAll() {
        logger.info("Finding all books.");
        return bookRepository.findAll();
    }

    public Book add(Book book) {
        logger.info("Adding book.");
        if(bookRepository.existsByIsbn(book.getIsbn())) {
            throw new ElementAlreadyExistsException("Book", "isbn",
                    book.getIsbn());
        }


        bookRepository.save(book);
        return book;
    }

    public Book deleteById(Long id) {
        logger.info(String.format("Deleting book with id %d.", id));
        Book out = findByIdOrThrow(id);
        bookRepository.deleteById(id);
        return out;
    }

    public Book edit(Book book, Long id) {
        logger.info(String.format("Editing book with id %d.", id));
        Book oldBook = findByIdOrThrow(id);
        book.setId(id);
        book.setAuthors(oldBook.getAuthors());
        bookRepository.save(book);
        return book;
    }

    public Book changeAmount(Long id, Long amount) {
        logger.info(String.format("Changing amount of book with id %d by %d.", id, amount));
        Book out = findByIdOrThrow(id);
        out.setTotal(out.getTotal() + amount);
        out.setAvailable(out.getAvailable() + amount);
        if(out.getAvailable() < 0) {
            throw new IllegalNegativeValueException("available");
        }
        bookRepository.save(out);
        return out;
    }
    
}
