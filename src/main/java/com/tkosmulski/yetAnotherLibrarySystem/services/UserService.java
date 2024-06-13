package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementAlreadyExistsException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.models.BaseUser;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<BaseUser> findAll() {
        logger.info("Finding all users");
        return userRepository.findAll();
    }

    public BaseUser findByIdOrThrow(Long id) {
        logger.info(String.format("Finding user with id %d.", id));
        return userRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("User", "id", id)
        );
    }

    public BaseUser findByEmailOrThrow(String email) {
        logger.info(String.format("Finding user with email %s.", email));
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ElementNotFoundException("User", "email", email)
        );
    }

    public BaseUser add(BaseUser user) {
        logger.info("Adding user.");
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new ElementAlreadyExistsException("User", "email", user.getEmail());
        }
        userRepository.save(user);
        return user;
    }

    public BaseUser edit(BaseUser user) {
        logger.info(String.format("Editing user with id %d.", user.getId()));
        if(!userRepository.existsById(user.getId())) {
            throw new ElementNotFoundException("User", "id", user.getId());
        }
        userRepository.save(user);
        return user;
    }
}
