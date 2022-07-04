package com.connect.api.repository;

import com.connect.api.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
            select distinct p from Post p
            where p.user.id = ?1 or p.user.id in ?2 or p.group.id in ?3
            order by p.createdAt DESC""")
    Page<Post> findDistinctByUser_IdOrUser_IdInOrGroup_IdInOrderByCreatedAtDesc(@NonNull Long id, @Nullable Collection<Long> ids, @Nullable Collection<Long> ids1, Pageable pageable);
}