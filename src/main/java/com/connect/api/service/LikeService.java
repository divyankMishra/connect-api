package com.connect.api.service;

import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.dto.post.LikeDto;

public interface LikeService {
    PageResponseDto<LikeDto> getLikes(Long postId, Integer pageNo, Integer pageSize, String sortBy);

    boolean like(Long postId);


    Long getLikeCount(Long postId);
}
