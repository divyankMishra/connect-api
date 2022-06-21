package com.connect.api.service;

import com.connect.api.dto.payload.request.PostPayloadDto;
import com.connect.api.dto.post.PostDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();

    PostDto createPost(PostPayloadDto postPayloadDto);

    PostDto updatePost(PostPayloadDto postPayloadDto, Long id);

    PostDto getPost(Long id);

    void deletePost(Long id);
}
