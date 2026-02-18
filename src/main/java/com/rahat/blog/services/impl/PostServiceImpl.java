package com.rahat.blog.services.impl;

import com.rahat.blog.domain.dtos.CategoryDto;
import com.rahat.blog.domain.dtos.CreatePostDto;
import com.rahat.blog.domain.dtos.PostDto;
import com.rahat.blog.domain.dtos.TagDto;
import com.rahat.blog.domain.entities.*;
import com.rahat.blog.domain.enums.PostStatus;
import com.rahat.blog.exceptions.ResourceNotFoundException;
import com.rahat.blog.exceptions.ValidationException;
import com.rahat.blog.mappers.PostMapper;
import com.rahat.blog.repositories.CategoryRepository;
import com.rahat.blog.repositories.PostRepository;
import com.rahat.blog.repositories.TagRepository;
import com.rahat.blog.services.CurrentUserService;
import com.rahat.blog.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final CurrentUserService currentUserService;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository,
                          CategoryRepository categoryRepository,
                          TagRepository tagRepository,
                          CurrentUserService currentUserService,
                           PostMapper postMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.currentUserService = currentUserService;
        this.postMapper = postMapper;
    }

    @Override
    public PostDto createPost(CreatePostDto createPostDto) {

        validatePostInput(createPostDto);

        User currentUser = currentUserService.getCurrentUser();

        Category category = categoryRepository.findById(createPostDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + createPostDto.categoryId()));

        Set<Tag> tags = new HashSet<>();
        if (createPostDto.tagIds() != null && !createPostDto.tagIds().isEmpty()) {
            List<Tag> foundTags = tagRepository.findAllById(createPostDto.tagIds());

            if (foundTags.size() != createPostDto.tagIds().size()) {
                Set<UUID> foundIds = foundTags.stream().map(Tag::getId).collect(Collectors.toSet());
                Set<UUID> missingIds = createPostDto.tagIds().stream()
                        .filter(id -> !foundIds.contains(id))
                        .collect(Collectors.toSet());
                throw new ResourceNotFoundException("Tags not found with ids: " + missingIds);
            }

            tags = new HashSet<>(foundTags);
        }

        LocalDateTime now = LocalDateTime.now();

        Post post = new Post(
                null,
                createPostDto.title(),
                createPostDto.content(),
                createPostDto.status(),
                currentUser,
                category,
                tags,
                now,
                now
        );

        Post savedPost = postRepository.save(post);
        return postMapper.toDto(savedPost);
    }

    @Override
    public PostDto getPostById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        User currentUser = currentUserService.getCurrentUser();

        boolean isOwner = post.getAuthor() != null && post.getAuthor().getId().equals(currentUser.getId());
        if (post.getStatus() != PostStatus.PUBLISHED && !isOwner) {
            throw new AccessDeniedException("You can only view published posts or your own posts");
        }

        return postMapper.toDto(post);
    }

    @Override
    public List<PostDto> getPosts() {

        User currentUser = currentUserService.getCurrentUser();

        List<Post> visiblePosts;
        if (currentUser == null) {

            visiblePosts = postRepository.findByStatus(PostStatus.PUBLISHED);
        } else {

            visiblePosts = postRepository.findVisiblePosts(currentUser.getId());
        }
        return visiblePosts.stream()
                .map(postMapper::toDto)
                .toList();
    }



    @Override
    public PostDto updatePost(UUID postId, CreatePostDto updatePostDto) {
        validatePostInput(updatePostDto);

        User currentUser = currentUserService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));


        if (post.getAuthor() == null || !post.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only update your own posts");
        }

        Category category = categoryRepository.findById(updatePostDto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + updatePostDto.categoryId()));

        Set<Tag> tags = new HashSet<>();
        if (updatePostDto.tagIds() != null && !updatePostDto.tagIds().isEmpty()) {
            List<Tag> foundTags = tagRepository.findAllById(updatePostDto.tagIds());


            if (foundTags.size() != updatePostDto.tagIds().size()) {
                Set<UUID> foundIds = foundTags.stream().map(Tag::getId).collect(Collectors.toSet());
                Set<UUID> missingIds = updatePostDto.tagIds().stream()
                        .filter(id -> !foundIds.contains(id))
                        .collect(Collectors.toSet());
                throw new ResourceNotFoundException("Tags not found with ids: " + missingIds);
            }

            tags = new HashSet<>(foundTags);
        }

        post.setTitle(updatePostDto.title());
        post.setContent(updatePostDto.content());
        post.setStatus(updatePostDto.status());
        post.setCategory(category);
        post.setTags(tags);
        post.setUpdatedAt(LocalDateTime.now());

        Post updatedPost = postRepository.save(post);
        return postMapper.toDto(updatedPost);
    }

    @Override
    public void deletePost(UUID postId) {
        User currentUser = currentUserService.getCurrentUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));


        if (post.getAuthor() == null || !post.getAuthor().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You can only delete your own posts");
        }

        postRepository.delete(post);
    }

    private void validatePostInput(CreatePostDto createPostDto) {
        if (createPostDto.title() == null || createPostDto.title().trim().isEmpty()) {
            throw new ValidationException("Post title cannot be empty");
        }

        if (createPostDto.title().length() > 200) {
            throw new ValidationException("Post title cannot exceed 200 characters");
        }

        if (createPostDto.content() == null || createPostDto.content().trim().isEmpty()) {
            throw new ValidationException("Post content cannot be empty");
        }

        if (createPostDto.content().length() > 50000) {
            throw new ValidationException("Post content cannot exceed 50000 characters");
        }

        if (createPostDto.categoryId() == null) {
            throw new ValidationException("Category ID cannot be null");
        }

        if (createPostDto.status() == null) {
            throw new ValidationException("Post status cannot be null");
        }
    }
}
