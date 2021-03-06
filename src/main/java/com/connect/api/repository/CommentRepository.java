package com.connect.api.repository;

import com.connect.api.model.post.Comment;
import com.connect.api.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findFirst5DistinctByPost_IdOrderByUpdatedAtDescCreatedAtDesc(Long id);



}