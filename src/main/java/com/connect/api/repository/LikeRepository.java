package com.connect.api.repository;

import com.connect.api.model.post.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Page<Like> findLikesByPost_Id(Long postId, Pageable pageable);
    Optional<Like> findLikesByPost_IdAndUser_Username(Long id, String username);

    Long countLikesByPost_Id(Long postId);
}