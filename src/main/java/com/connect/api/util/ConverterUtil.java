package com.connect.api.util;

import com.connect.api.dto.post.CommentDto;
import com.connect.api.dto.post.LikeDto;
import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.post.Comment;
import com.connect.api.model.post.Like;

public class ConverterUtil {

    public static CommentDto getCommentDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .user(new UserMinDto(comment.getUser().getUsername(),
                        comment.getUser().getFirstname(),
                        comment.getUser().getLastname()))
                .postId(comment.getPost().getId())
                .build();
    }

    public static LikeDto getLikeDto(Like like) {
        return new LikeDto(like.getId(),
                new UserMinDto(like.getUser().getUsername(),
                        like.getUser().getFirstname(),
                        like.getUser().getLastname()));
    }
}
