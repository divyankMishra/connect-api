package com.connect.api.controller;

import com.connect.api.dto.payload.request.PostPayloadDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.dto.post.PostDto;
import com.connect.api.service.PostService;
import com.connect.api.util.ErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;


    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    PageResponseDto<PostDto> getAllPosts(@PageableDefault(page = 0, size = 20) @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @GetMapping(path = "/{id}")
//@TODO Have to find the better way to give no. of likes and comments in all post call and first 3-5 comments in just post.
    PostDto getPost(@PathVariable(name = "id") Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    ResponseEntity<Object> createPost(@Valid @RequestBody PostPayloadDto postPayloadDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ErrorUtil.bindingErrors(bindingResult);
        return new ResponseEntity<>(postService.createPost(postPayloadDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<Object> updatePost(@Valid @RequestBody PostPayloadDto postPayloadDto, BindingResult bindingResult, @PathVariable(name = "id") Long id) {
        if (bindingResult.hasErrors()) return ErrorUtil.bindingErrors(bindingResult);
        return new ResponseEntity<>(postService.updatePost(postPayloadDto, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }
}
