package com.connect.api.controller;

import com.connect.api.dto.payload.request.CommentPayloadDto;
import com.connect.api.dto.post.CommentDto;
import com.connect.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    List<CommentDto> getAllComments(@PathVariable(name = "postId") Long id) {
        return commentService.getPostComments(id);
    }

    @PostMapping
    CommentDto createComment(@Valid @RequestBody CommentPayloadDto commentPayloadDto, @PathVariable(name = "postId") Long id) {
        return commentService.createComment(id, commentPayloadDto);
    }

    @PutMapping(path = "/{commentid}")
    CommentDto updateComment(@Valid @RequestBody CommentPayloadDto commentPayloadDto, @PathVariable(name = "postId") Long id, @PathVariable(name = "commentid") Long commentId) {
        return commentService.updatePostComment(id, commentId, commentPayloadDto);
    }

    @DeleteMapping(path = "/{commentid}")
    ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long id, @PathVariable(name = "commentid") Long commentId) {
        commentService.deletePostComment(id, commentId);
        return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
    }
}
