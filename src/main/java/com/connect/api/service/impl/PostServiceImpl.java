package com.connect.api.service.impl;

import com.connect.api.dto.payload.request.PostPayloadDto;
import com.connect.api.dto.post.PostDto;
import com.connect.api.model.post.Post;
import com.connect.api.repository.GroupRepository;
import com.connect.api.repository.PostRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.PostService;
import com.connect.api.util.ConverterUtil;
import com.connect.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;


    @Autowired
    public PostServiceImpl(PostRepository postRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDto getPost(Long id) {
        return ConverterUtil.getPostDto(postRepository.findById(id).orElseThrow());
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(ConverterUtil::getPostDto).toList();
    }

    @Override
    public PostDto createPost(PostPayloadDto postPayloadDto) {
        Post post = Post.builder()
                .createdAt(new Date())
                .title(postPayloadDto.getTitle())
                .description(postPayloadDto.getDescription())
                .group(postPayloadDto.getGroupId() == null
                        ? null
                        : groupRepository.findById(postPayloadDto.getGroupId()).orElse(null))
                .user(userRepository.findByUsername(Util.getCurrentUserName()))
                .build();
        return ConverterUtil.getPostDto(postRepository.save(post));
    }

    @Override
    public PostDto updatePost(PostPayloadDto postPayloadDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        if (isOwnerUser(post)) throw new RuntimeException("User does not have authority to edit this post.");
        if (postPayloadDto.getTitle() != null) post.setTitle(postPayloadDto.getTitle());
        if (postPayloadDto.getDescription() != null) post.setDescription(postPayloadDto.getDescription());
        post.setUpdatedAt(new Date());
        return ConverterUtil.getPostDto(postRepository.save(post));
    }


    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        if (isOwnerUser(post)) throw new RuntimeException("User does not have authority to edit this post.");
        postRepository.delete(post);
    }

    private boolean isOwnerUser(Post post) {
        return post.getUser().equals(userRepository.findByUsername(Util.getCurrentUserName()));
    }
}
