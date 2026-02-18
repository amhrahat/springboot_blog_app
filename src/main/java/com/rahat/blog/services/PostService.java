package com.rahat.blog.services;

import com.rahat.blog.domain.dtos.CreatePostDto;
import com.rahat.blog.domain.dtos.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PostService {

    PostDto createPost(CreatePostDto createPostDto);

    PostDto getPostById(UUID postId);

    List<PostDto> getPosts();

    PostDto updatePost(UUID postId, CreatePostDto updatePostDto);

    void deletePost(UUID postId);
}
