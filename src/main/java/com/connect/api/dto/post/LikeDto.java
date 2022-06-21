package com.connect.api.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record LikeDto(Long id, UserMinDto user) implements Serializable {

}
