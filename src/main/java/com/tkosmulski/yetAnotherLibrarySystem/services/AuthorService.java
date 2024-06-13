package com.tkosmulski.yetAnotherLibrarySystem.services;

import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementAlreadyExistsException;
import com.tkosmulski.yetAnotherLibrarySystem.exceptions.ElementNotFoundException;
import com.tkosmulski.yetAnotherLibrarySystem.models.Author;
import com.tkosmulski.yetAnotherLibrarySystem.repositories.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    Logger logger = LoggerFactory.getLogger(AuthorService.class);
    AuthorRepository authorRepository;

    @Autowired
    public  AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findByIdOrThrow(Long id) {
        logger.info(String.format("Finding author with id %d.", id));
        return authorRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Author", "id", id)
        );
    }

    public List<Author> findAll() {
        logger.info("Finding all authors.");
        return authorRepository.findAll();
    }

    public Author add(Author author) {
        logger.info("Adding author.");
        if(authorRepository.existsByNameAndSurname(author.getName(), author.getSurname())) {
            throw new ElementAlreadyExistsException("Author", "(name, surname)",
                    "(" + author.getName() + ", " + author.getSurname() + ")");
        }
        authorRepository.save(author);
        return author;
    }

    public Author deleteById(Long id) {
        logger.info(String.format("Deleting author with id %d.", id));
        Author out = findByIdOrThrow(id);
        authorRepository.deleteById(id);
        return out;
    }

    public Author edit(Author author, Long id) {
        logger.info(String.format("Editing author with id %d.", id));
        if(!authorRepository.existsById(id)) {
            throw new ElementNotFoundException("Author", "id", id);
        }
        author.setId(id);
        authorRepository.save(author);
        return author;
    }
}
