package com.connect.api.service.impl;

import com.connect.api.dto.home.HomeDto;
import com.connect.api.dto.post.PostDto;
import com.connect.api.dto.post.UserMinDto;
import com.connect.api.model.post.Post;
import com.connect.api.repository.ConnectionRepository;
import com.connect.api.repository.PostRepository;
import com.connect.api.repository.UserRepository;
import com.connect.api.service.HomeService;
import com.connect.api.util.ConverterUtil;
import com.connect.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final ConnectionRepository connectionRepository;

    @Autowired
    public HomeServiceImpl(PostRepository postRepository, UserRepository userRepository, ConnectionRepository connectionRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.connectionRepository = connectionRepository;
    }

    @Override
    public HomeDto getHomePage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = postRepository.findDistinctByUser_IdOrUser_IdInOrGroup_IdInOrderByCreatedAtDesc(userRepository.findByUsername(Util.getCurrentUserName()).getId(), connectionRepository.findDistinctByUser_Username(Util.getCurrentUserName()), null, pageable);
        List<PostDto> firstPosts = posts.getContent().stream().map(ConverterUtil::getPostDto).toList();
        return new HomeDto(new UserMinDto(userRepository.findByUsername(Util.getCurrentUserName())), firstPosts, null);
    }
}
