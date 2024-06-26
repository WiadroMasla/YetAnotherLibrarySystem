package com.tkosmulski.yetAnotherLibrarySystem.repositories;

import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByTitle(String title);
}
