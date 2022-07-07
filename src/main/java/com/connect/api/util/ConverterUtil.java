package com.connect.api.util;

import com.connect.api.dto.connection.ConnectionRequestDto;
import com.connect.api.dto.group.GroupDto;
import com.connect.api.dto.post.CommentDto;
import com.connect.api.dto.post.LikeDto;
import com.connect.api.dto.post.PostDto;
import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.connection.ConnectionRequest;
import com.connect.api.model.group.Group;
import com.connect.api.model.post.Comment;
import com.connect.api.model.post.Like;
import com.connect.api.model.post.Post;

public class ConverterUtil {
    public static CommentDto getCommentDto(Comment comment) {
        return CommentDto
                .builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .user(new UserMinDto(comment.getUser()))
                .postId(comment.getPost().getId())
                .build();
    }

    public static LikeDto getLikeDto(Like like) {
        return new LikeDto(like.getId(),
                new UserMinDto(like.getUser()));
    }

    public static GroupDto getGroupDto(Group group) {
        return new GroupDto(group.getId(),
                new UserMinDto(group.getAdmin())
                , group.getCreatedAt()
                , group.getName()
                , group.getDescription(), null);
    }

    public static PostDto getPostDto(Post post) {
        return new PostDto(post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                new UserMinDto(post.getUser()),
                post.getGroup() != null
                        ? post.getGroup().getId()
                        : null,
                post.getGroup() != null
                        ? post.getGroup().getName()
                        : null,
                null,null
        );
    }

    public static ConnectionRequestDto getConnectionRequestDto(boolean populateSender, ConnectionRequest content) {
        return new ConnectionRequestDto(content.getId(),
                populateSender ? new UserMinDto(content.getSender()) : null, !populateSender ? new UserMinDto(content.getReceiver()) : null,
                content.getCreatedAt(), content.getStatus());
    }
}
