package com.tkosmulski.yetAnotherLibrarySystem.repositories;

import com.tkosmulski.yetAnotherLibrarySystem.models.BookBorrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBorrowRepository extends JpaRepository<BookBorrow, Long> {
}
