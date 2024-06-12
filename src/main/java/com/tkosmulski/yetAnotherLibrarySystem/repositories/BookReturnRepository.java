package com.tkosmulski.yetAnotherLibrarySystem.repositories;

import com.tkosmulski.yetAnotherLibrarySystem.models.BookReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReturnRepository extends JpaRepository<BookReturn, Long> {
}
