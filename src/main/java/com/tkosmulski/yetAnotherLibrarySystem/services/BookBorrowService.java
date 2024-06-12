package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.IllegalNegativeValueException;
import com.tkosmulski.yetAnotherLibrarySystem.models.BaseUser;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.models.BookBorrow;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.BookBorrowRepository;
import com.tkosmulski.yetAnotherLibrarySystem.services.serviceInterfaces.CurrentDateProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookBorrowService {
    BookService bookService;
    UserService userService;
    BookBorrowRepository bookBorrowRepository;
    CurrentDateProvider currentDateProvider;

    @Autowired
    public BookBorrowService(BookService bookService, UserService userService, BookBorrowRepository bookBorrowRepository,
                             @Qualifier("clockService") CurrentDateProvider currentDateProvider) {
        this.bookService = bookService;
        this.userService = userService;
        this.bookBorrowRepository = bookBorrowRepository;
        this.currentDateProvider = currentDateProvider;
    }

    public BookBorrow borrow(Long bookId, Long userId, Integer duration) {
        BaseUser user = userService.findByIdOrThrow(userId);
        Book book = bookService.findByIdOrThrow(bookId);

        if(book.getAvailable() <= 0) {
            throw new IllegalNegativeValueException("available");
        }
        book.setAvailable(book.getAvailable() - 1);

        if(duration < 0) {
            throw new IllegalNegativeValueException("duration");
        }

        bookService.edit(book, bookId);

        BookBorrow bookBorrow = new BookBorrow();
        bookBorrow.setBorrower(user);
        bookBorrow.setBorrowedBook(book);

        Date currentDate = currentDateProvider.getCurrentDate();
        bookBorrow.setBorrowDate(currentDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, duration);
        bookBorrow.setDueDate(calendar.getTime());



        return bookBorrowRepository.save(bookBorrow);
    }

    public BookBorrow findByIdOrThrow(Long id) {
        return bookBorrowRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("BookBorrow", "id", id)
        );
    }

    public List<BookBorrow> findAll() {
        return bookBorrowRepository.findAll();
    }
}
