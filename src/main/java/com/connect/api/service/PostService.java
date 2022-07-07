package com.connect.api.service;

import com.connect.api.dto.payload.request.PostPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.dto.post.PostDto;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PageResponseDto<PostDto> getAllPosts(Pageable pageable);

    PostDto createPost(PostPayloadDto postPayloadDto);

    PostDto updatePost(PostPayloadDto postPayloadDto, Long id);

    PostDto getPost(Long id);

    void deletePost(Long id);
}
