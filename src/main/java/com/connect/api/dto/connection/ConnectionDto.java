package com.connect.api.dto.connection;

import com.connect.api.dto.post.UserMinDto;

import java.io.Serializable;
import java.util.Date;

public record ConnectionDto(Long id, UserMinDto connection, Date createdAt) implements Serializable {
}
