package com.connect.api.service.impl;

import com.connect.api.dto.payload.request.PostPayloadDto;
import com.connect.api.dto.post.PostDto;
import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.post.Post;
import com.connect.api.repository.GroupRepository;
import com.connect.api.repository.PostRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.PostService;
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
        return getPostDto(postRepository.findById(id).orElseThrow());
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(post -> getPostDto(post)).toList();
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
                .user(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()))
                .build();
        return getPostDto(postRepository.save(post));
    }

    @Override
    public PostDto updatePost(PostPayloadDto postPayloadDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        if (isOwnerUser(post)) throw new RuntimeException("User does not have authority to edit this post.");
        if (postPayloadDto.getTitle() != null) post.setTitle(postPayloadDto.getTitle());
        if (postPayloadDto.getDescription() != null) post.setDescription(postPayloadDto.getDescription());
        post.setUpdatedAt(new Date());
        return getPostDto(postRepository.save(post));
    }


    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        if (isOwnerUser(post)) throw new RuntimeException("User does not have authority to edit this post.");
        postRepository.delete(post);
    }

    private boolean isOwnerUser(Post post) {
        return post.getUser().equals(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    private PostDto getPostDto(Post post) {
        return new PostDto(post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                new UserMinDto(
                        post.getUser().getUsername(),
                        post.getUser().getFirstname(),
                        post.getUser().getLastname()),
                post.getGroup() != null
                        ? post.getGroup().getId()
                        : null,
                post.getGroup() != null
                        ? post.getGroup().getName()
                        : null
        );
    }
}
