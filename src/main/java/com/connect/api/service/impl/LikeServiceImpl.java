package com.connect.api.service.impl;

import com.connect.api.dto.payload.response.LikeResponseDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.dto.post.LikeDto;
import com.connect.api.model.post.Like;
import com.connect.api.repository.LikeRepository;
import com.connect.api.repository.PostRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.LikeService;
import com.connect.api.util.ConverterUtil;
import com.connect.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Autowired
    public LikeServiceImpl(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PageResponseDto<LikeDto> getLikes(Long postId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageableReq = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC, sortBy);
        Page<Like> page = likeRepository.findLikesByPost_Id(postId, pageableReq);
        String username = Util.getCurrentUserName();
        Optional<Like> like = likeRepository.findLikesByPost_IdAndUser_Username(postId, username);
        return getLikeDtoPageResponseDto(page, like);
    }

    private PageResponseDto<LikeDto> getLikeDtoPageResponseDto(Page<Like> page, Optional<Like> like) {
        return new PageResponseDto<>(page.isLast(),
                like.isPresent(),
                page.getTotalElements(),
                (long) page.getNumberOfElements(),
                (long) page.getTotalPages(),
                (long) page.getNumber(),
                page.getContent().stream().map(ConverterUtil::getLikeDto).toList()
        );
    }


    @Override
    public boolean like(Long postId) {
        String username = Util.getCurrentUserName();
        Optional<Like> like = likeRepository.findLikesByPost_IdAndUser_Username(postId, username);
        if (like.isPresent()) {
            likeRepository.delete(like.get());
            return false;
        }
        Like newLike = Like.builder()
                .createdAt(new Date())
                .post(postRepository.findById(postId).orElseThrow())
                .user(userRepository.findByUsername(username))
                .build();
        likeRepository.save(newLike);
        return true;
    }

    @Override
    public LikeResponseDto getLikeCount(Long postId) {
        String username = Util.getCurrentUserName();
        Optional<Like> like = likeRepository.findLikesByPost_IdAndUser_Username(postId, username);
        return new LikeResponseDto(like.isPresent(), likeRepository.countLikesByPost_Id(postId));
    }
}
