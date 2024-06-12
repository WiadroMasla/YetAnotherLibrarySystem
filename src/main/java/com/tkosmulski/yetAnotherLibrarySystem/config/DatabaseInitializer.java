package com.tkosmulski.yetAnotherLibrarySystem.config;

import com.tkosmulski.yetAnotherLibrarySystem.enums.AccessLevel;
import com.tkosmulski.yetAnotherLibrarySystem.models.BaseUser;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer{
    @Autowired
    UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationEvent(ApplicationReadyEvent event) {
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