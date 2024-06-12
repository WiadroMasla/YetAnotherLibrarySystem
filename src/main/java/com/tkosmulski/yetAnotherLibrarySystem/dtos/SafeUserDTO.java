package com.tkosmulski.yetAnotherLibrarySystem.dtos;

import com.tkosmulski.yetAnotherLibrarySystem.enums.AccessLevel;
import com.tkosmulski.yetAnotherLibrarySystem.models.User;

import java.util.Collection;
import java.util.List;

public class SafeUserDTO {
    Long id;
    String email;
    AccessLevel accessLevel;


    public SafeUserDTO(User user) {
        id = user.getId();
        email = user.getEmail();
        accessLevel = user.getAccess();
    }

    public static List<SafeUserDTO> from(Collection<User> users) {
        return users.stream().map(SafeUserDTO::new).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }
}
