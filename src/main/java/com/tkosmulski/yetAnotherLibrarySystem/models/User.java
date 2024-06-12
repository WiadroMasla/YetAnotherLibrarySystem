package com.tkosmulski.yetAnotherLibrarySystem.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tkosmulski.yetAnotherLibrarySystem.enums.AccessLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String email;

    @NotNull
    String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    AccessLevel access = AccessLevel.BASIC;

    @JsonIgnore
    @OneToMany(mappedBy = "borrower")
    Set<BookBorrow> borrowedBooks = new HashSet<>();

    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<BookBorrow> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<BookBorrow> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public AccessLevel getAccess() {
        return access;
    }

    public void setAccess(AccessLevel access) {
        this.access = access;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
