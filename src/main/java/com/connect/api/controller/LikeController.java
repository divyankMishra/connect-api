package com.connect.api.controller;


import com.connect.api.dto.payload.response.LikeResponseDto;
import com.connect.api.dto.payload.response.PageResponseDto;
import com.connect.api.dto.post.LikeDto;
import com.connect.api.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    PageResponseDto<LikeDto> getLikes(@PathVariable(name = "postId") Long postId,
                                      @RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "id") String sortBy) {
        return likeService.getLikes(postId, pageNo, pageSize, sortBy);
    }

    @GetMapping("/count")
    LikeResponseDto getLikeCount(@PathVariable(name = "postId") Long postId) {
        return likeService.getLikeCount(postId);
    }


    @PostMapping("/like")
    LikeResponseDto addRemoveLike(@PathVariable(name = "postId") Long postId) {
        likeService.like(postId);
        return likeService.getLikeCount(postId);
    }
}
