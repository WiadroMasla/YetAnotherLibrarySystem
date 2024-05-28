package com.tkosmulski.yetAnotherLibrarySystem.repositories;

import com.tkosmulski.yetAnotherLibrarySystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
