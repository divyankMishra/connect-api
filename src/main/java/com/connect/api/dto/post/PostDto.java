package com.connect.api.dto.post;

import com.connect.api.dto.payload.response.LikeResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostDto(Long id, String title, String description, Date createdAt, Date updatedAt, UserMinDto user,
                      Long groupId, String groupName, LikeResponseDto likes, List<CommentDto> comments) implements Serializable {

    public PostDto(PostDto postDto,LikeResponseDto likeResponseDto, List<CommentDto> comments) {
        this(postDto.id(),postDto.title(),postDto.description(),postDto.createdAt(),postDto.updatedAt(),postDto.user(),postDto.groupId(),postDto.groupName(),likeResponseDto,comments);
    }
}
