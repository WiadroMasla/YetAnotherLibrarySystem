package com.tkosmulski.yetAnotherLibrarySystem.repositories;

import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("authorRepository")
public interface AuthorRepository extends JpaRepository<Author, Long>{
    boolean existsByNameAndSurname(String name, String surname);
}
