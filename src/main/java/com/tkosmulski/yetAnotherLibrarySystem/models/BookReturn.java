package com.tkosmulski.yetAnotherLibrarySystem.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class BookReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    BigDecimal lateFee;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date returnDate;

    @OneToOne
    @JoinColumn(name="borrow_id")
    BookBorrow borrow;

    public BookReturn() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BookBorrow getBorrow() {
        return borrow;
    }

    public void setBorrow(BookBorrow borrow) {
        this.borrow = borrow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookReturn that = (BookReturn) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
