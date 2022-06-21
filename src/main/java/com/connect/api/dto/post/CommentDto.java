package com.connect.api.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto implements Serializable {
    private Long id;
    private Long postId;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private UserMinDto user;
}
