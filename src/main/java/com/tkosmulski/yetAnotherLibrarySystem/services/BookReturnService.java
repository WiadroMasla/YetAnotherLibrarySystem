package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementAlreadyExistsException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.IllegalNegativeValueException;
import com.tkosmulski.yetAnotherLibrarySystem.models.BaseUser;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.models.BookBorrow;
import com.tkosmulski.yetAnotherLibrarySystem.models.BookReturn;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.BookBorrowRepository;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.BookReturnRepository;
import com.tkosmulski.yetAnotherLibrarySystem.services.serviceInterfaces.CurrentDateProvider;
import com.tkosmulski.yetAnotherLibrarySystem.services.serviceInterfaces.LateFeeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BookReturnService {
    BookBorrowService bookBorrowService;
    CurrentDateProvider currentDateProvider;
    BookReturnRepository bookReturnRepository;
    LateFeeCalculator lateFeeCalculator;
    BookService bookService;

    @Autowired
    public BookReturnService(BookBorrowService bookBorrowService, @Qualifier("clockService") CurrentDateProvider currentDateProvider,
                             BookReturnRepository bookReturnRepository,
                             @Qualifier("linearFeeCalculatorService") LateFeeCalculator lateFeeCalculator,
                             BookService bookService) {
        this.bookBorrowService = bookBorrowService;
        this.currentDateProvider = currentDateProvider;
        this.bookReturnRepository = bookReturnRepository;
        this.lateFeeCalculator = lateFeeCalculator;
        this.bookService = bookService;
    }

    public BookReturn bookReturn(Long borrowId) {
        BookBorrow bookBorrow = bookBorrowService.findByIdOrThrow(borrowId);
        if(bookBorrow.getBookReturn() != null) {
            throw new ElementAlreadyExistsException("BookReturn", "borrowId", borrowId);
        }
        Date returnDate = currentDateProvider.getCurrentDate();
        Book book = bookBorrow.getBorrowedBook();

        book.setAvailable(book.getAvailable() + 1);



        bookService.edit(book, book.getId());

        BookReturn bookReturn = new BookReturn();
        bookReturn.setReturnDate(returnDate);
        bookReturn.setBorrow(bookBorrow);
        bookReturn.setLateFee(lateFeeCalculator.calculate(bookBorrow.getDueDate(),returnDate));

        return bookReturnRepository.save(bookReturn);
    }

    public BookReturn findByIdOrThrow(Long id) {
        return bookReturnRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("BookReturn", "id", id)
        );
    }

    public List<BookReturn> findAll() {
        return bookReturnRepository.findAll();
    }
}
