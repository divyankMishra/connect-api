package com.connect.api.dto.post;

import com.connect.api.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserMinDto(String username, String firstname, String lastname) implements Serializable {
    public UserMinDto(User user) {
        this(user.getUsername(), user.getFirstname(), user.getLastname());
    }
}
