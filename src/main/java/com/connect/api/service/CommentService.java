package com.connect.api.service;

import com.connect.api.dto.payload.request.CommentPayloadDto;
import com.connect.api.dto.post.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentPayloadDto comment);

    List<CommentDto> getPostComments(Long id);

    CommentDto updatePostComment(Long id, Long commentId, CommentPayloadDto commentPayloadDto);

    void deletePostComment(Long id, Long commentId);
}
