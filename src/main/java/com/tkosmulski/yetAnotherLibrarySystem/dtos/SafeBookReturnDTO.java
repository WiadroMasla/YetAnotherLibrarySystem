package com.tkosmulski.yetAnotherLibrarySystem.dtos;

import com.tkosmulski.yetAnotherLibrarySystem.models.BookReturn;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SafeBookReturnDTO {
    Long id;
    BigDecimal lateFee;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date returnDate;
    SafeBookBorrowDTO borrowDTO;

    public SafeBookReturnDTO(BookReturn bookReturn) {
        id = bookReturn.getId();
        lateFee = bookReturn.getLateFee();
        returnDate = bookReturn.getReturnDate();
        borrowDTO = new SafeBookBorrowDTO(bookReturn.getBorrow());
    }

    public static List<SafeBookReturnDTO> from(Collection<BookReturn> bookReturns) {
        return bookReturns.stream().map(SafeBookReturnDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public SafeBookBorrowDTO getBorrowDTO() {
        return borrowDTO;
    }
}
