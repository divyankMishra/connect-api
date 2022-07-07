package com.connect.api.util;

import com.connect.api.dto.payload.response.LikeResponseDto;
import com.connect.api.dto.post.PostDto;
import com.connect.api.repository.CommentRepository;
import com.connect.api.repository.LikeRepository;
import org.springframework.security.core.context.SecurityContextHolder;

public class Util {
    public static String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static PostDto populateCommentsAndLikes(PostDto el, CommentRepository commentRepository, LikeRepository likeRepository) {
        var comments = commentRepository.findFirst5DistinctByPost_IdOrderByUpdatedAtDescCreatedAtDesc(el.id())
                .stream()
                .map(ConverterUtil::getCommentDto)
                .toList();
        var like = likeRepository.findLikesByPost_IdAndUser_Username(el.id(), Util.getCurrentUserName());
        var likes = new LikeResponseDto(like.isPresent(), likeRepository.countLikesByPost_Id(el.id()));
        return new PostDto(el, likes, comments);}
}
