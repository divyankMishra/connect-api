package com.connect.api.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostDto(Long id, String title, String description, Date createdAt, Date updatedAt, UserMinDto user,
                      Long groupId, String groupName) implements Serializable {

}
