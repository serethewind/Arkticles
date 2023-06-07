package com.serethewind.Arkticles.controller;

import com.serethewind.Arkticles.dto.posts.PostsCreationDto;
import com.serethewind.Arkticles.dto.posts.PostsResponseDto;
import com.serethewind.Arkticles.service.posts.serviceImpl.PostsServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/arkticles/v1/posts")
@AllArgsConstructor
public class PostsController {

    private PostsServiceImplementation postsService;

    @GetMapping
    public ResponseEntity<List<PostsResponseDto>> fetchAllPosts(){
        return ResponseEntity.ok(postsService.fetchAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostsResponseDto> fetchSinglePost(@PathVariable("id") Long id){
        return ResponseEntity.ok(postsService.fetchPostById(id));
    }

    @PostMapping
    public ResponseEntity<PostsResponseDto> createNewPost(@RequestBody PostsCreationDto postsCreationDto){
        return ResponseEntity.ok(postsService.createNewPost(postsCreationDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostsResponseDto> updateSinglePost(@PathVariable("id") Long id, @RequestBody PostsCreationDto postsCreationDto){
        return ResponseEntity.ok(postsService.updatePostById(id, postsCreationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSinglePost(@PathVariable("id") Long id){
        return ResponseEntity.ok(postsService.deletePost(id));
    }
}
