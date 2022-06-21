package com.connect.api.service.impl;

import com.connect.api.dto.payload.request.CommentPayloadDto;
import com.connect.api.dto.post.CommentDto;
import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.post.Comment;
import com.connect.api.repository.CommentRepository;
import com.connect.api.repository.PostRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.CommentService;
import com.connect.api.util.ConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentPayloadDto comment) {
        Comment newComment = Comment.builder()
                .comment(comment.comment())
                .createdAt(new Date())
                .user(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()))
                .post(postRepository.findById(postId).orElseThrow())
                .build();
        Comment savedComment = commentRepository.save(newComment);
        return ConverterUtil.getCommentDto(savedComment);
    }

    @Override
    public List<CommentDto> getPostComments(Long id) {
        return commentRepository.findByPost(postRepository.findById(id).orElseThrow())
                .stream()
                .map(ConverterUtil::getCommentDto)
                .toList();
    }

    @Override
    public CommentDto updatePostComment(Long id, Long commentId, CommentPayloadDto commentPayloadDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (isOwnerUser(comment)) {
            comment.setComment(commentPayloadDto.comment());
            comment.setUpdatedAt(new Date());
            return ConverterUtil.getCommentDto(commentRepository.save(comment));
        } else throw new RuntimeException("This user does not have authority to edit this comment.");
    }

    @Override
    public void deletePostComment(Long id, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (isOwnerUser(comment)) {
            commentRepository.delete(comment);
        } else throw new RuntimeException("This user does not have authority to delete this comment.");
    }

    private boolean isOwnerUser(Comment comment) {
        return comment.getUser().equals(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    }


}
