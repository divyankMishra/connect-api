package com.connect.api.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LikeDto(Long id, UserMinDto user) implements Serializable {

}
