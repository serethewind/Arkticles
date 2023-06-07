package com.serethewind.Arkticles.service.posts.serviceImpl;

import com.serethewind.Arkticles.dto.posts.PostsCreationDto;
import com.serethewind.Arkticles.dto.posts.PostsResponseDto;
import com.serethewind.Arkticles.entity.PostsEntity;
import com.serethewind.Arkticles.repository.PostsRepository;
import com.serethewind.Arkticles.service.posts.PostsServiceInterface;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostsServiceImplementation implements PostsServiceInterface {
    private ModelMapper modelMapper;

    private PostsRepository postsRepository;

    @Override
    public List<PostsResponseDto> fetchAllPosts() {
        List<PostsEntity> postsList = postsRepository.findAll();
        return postsList.stream().map(postsEntity -> modelMapper.map(postsEntity, PostsResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostsResponseDto fetchPostById(Long id) {
        PostsEntity postsEntity = postsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(postsEntity, PostsResponseDto.class);
    }

    @Override
    public PostsResponseDto createNewPost(PostsCreationDto postsCreationDto) {
        PostsEntity postCreated = modelMapper.map(postsCreationDto, PostsEntity.class);
        postsRepository.save(postCreated);
        return modelMapper.map(postCreated, PostsResponseDto.class);
    }

    @Override
    public PostsResponseDto updatePostById(Long id, PostsCreationDto postsCreationDto) {
        if (!postsRepository.existsById(id)){
            return PostsResponseDto.builder()
                    .title(null)
                    .content(null)
                    .build();
        }

        PostsEntity foundPost = postsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(postsCreationDto, foundPost);
        postsRepository.save(foundPost);
        return modelMapper.map(foundPost, PostsResponseDto.class);
    }

    @Override
    public String deletePost(Long id) {
        if (!postsRepository.existsById(id)){
            return "Delete operation unsuccessful. Post to be deleted doesn't exist";
        }
        postsRepository.deleteById(id);
        return "Post deleted";
    }
}
