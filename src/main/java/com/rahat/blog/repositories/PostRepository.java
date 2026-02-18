package com.rahat.blog.repositories;

import com.rahat.blog.domain.entities.Post;
import com.rahat.blog.domain.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    List<Post> findByStatus(PostStatus status);

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    Optional<Post> findById(UUID id);

    @EntityGraph(attributePaths = {"author", "category", "tags"})
    @Query("""
        SELECT p
        FROM Post p
        WHERE
            p.status = "PUBLISHED"
            OR
            (p.status = "DRAFT"
             AND p.author.id = :userId)
    """)
    List<Post> findVisiblePosts(UUID userId);
}

