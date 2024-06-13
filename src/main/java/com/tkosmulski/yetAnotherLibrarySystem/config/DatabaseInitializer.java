package com.tkosmulski.yetAnotherLibrarySystem.config;

import com.tkosmulski.yetAnotherLibrarySystem.enums.AccessLevel;
import com.tkosmulski.yetAnotherLibrarySystem.models.BaseUser;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer{
    Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);
    @Autowired
    UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Database intialization started.");
        addAdminUser();
    }

    public void addAdminUser() {
        BaseUser user = new BaseUser();
        user.setEmail("admin@gmail.com");
        user.setPassword("admin");
        user.setAccess(AccessLevel.ADMIN);
        userRepository.save(user);
    }
}
