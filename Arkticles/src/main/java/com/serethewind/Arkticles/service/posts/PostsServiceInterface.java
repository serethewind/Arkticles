package com.serethewind.Arkticles.service.posts;


import com.serethewind.Arkticles.dto.posts.PostsCreationDto;
import com.serethewind.Arkticles.dto.posts.PostsResponseDto;

import java.util.List;

public interface PostsServiceInterface {

    List<PostsResponseDto> fetchAllPosts();
    PostsResponseDto fetchPostById(Long id);
    PostsResponseDto createNewPost(PostsCreationDto postsCreationDto);
    PostsResponseDto updatePostById(Long id, PostsCreationDto postsCreationDto);

    String deletePost(Long postId, Long userAuthorId);

}
