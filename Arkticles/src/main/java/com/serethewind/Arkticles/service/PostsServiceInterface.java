package com.serethewind.Arkticles.service;


import com.serethewind.Arkticles.dto.PostsCreationDto;
import com.serethewind.Arkticles.dto.PostsResponseDto;

import java.util.List;

public interface PostsServiceInterface {

    List<PostsResponseDto> fetchAllPosts();
    PostsResponseDto fetchPostById(Long id);
    PostsResponseDto createNewPost(PostsCreationDto postsCreationDto);
    PostsResponseDto updatePostById(Long id, PostsCreationDto postsCreationDto);

    String deletePost(Long id);

}
