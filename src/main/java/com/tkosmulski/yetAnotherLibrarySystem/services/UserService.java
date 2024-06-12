package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementAlreadyExistsException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.models.Book;
import com.tkosmulski.yetAnotherLibrarySystem.models.User;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("User", "id", id)
        );
    }

    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ElementNotFoundException("User", "email", email)
        );
    }

    public User add(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new ElementAlreadyExistsException("User", "email", user.getEmail());
        }
        userRepository.save(user);
        return user;
    }

    public User edit(User user) {
        if(!userRepository.existsById(user.getId())) {
            throw new ElementNotFoundException("User", "id", user.getId());
        }
        userRepository.save(user);
        return user;
    }
}
