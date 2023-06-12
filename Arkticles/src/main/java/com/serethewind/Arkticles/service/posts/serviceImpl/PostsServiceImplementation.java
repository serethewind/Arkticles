package com.serethewind.Arkticles.service.posts.serviceImpl;

import com.serethewind.Arkticles.dto.comments.CommentResponseData;
import com.serethewind.Arkticles.dto.posts.PostsCreationDto;
import com.serethewind.Arkticles.dto.posts.PostsResponseDto;
import com.serethewind.Arkticles.entity.CommentsEntity;
import com.serethewind.Arkticles.entity.PostsEntity;
import com.serethewind.Arkticles.entity.UsersEntity;
import com.serethewind.Arkticles.repository.CommentsRepository;
import com.serethewind.Arkticles.repository.PostsRepository;
import com.serethewind.Arkticles.repository.UserRepository;
import com.serethewind.Arkticles.service.comments.serviceImpl.CommentsServiceImplementation;
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
    private UserRepository userRepository;
    private CommentsServiceImplementation commentsService;
    private PostsRepository postsRepository;

    @Override
    public List<PostsResponseDto> fetchAllPosts() {
        List<PostsEntity> postsList = postsRepository.findAll();

//        List<CommentResponseData> commentResponseDataList = commentsService.getCommentByPost()
        //creating comments dto

//        condition ? expression1 : expression2

        return postsList.stream().map(postsEntity -> PostsResponseDto.builder()
                        .id(postsEntity.getId())
                        .title(postsEntity.getTitle())
                        .content(postsEntity.getContent())
                        .userAuthorUsername(postsEntity.getUserAuthor() == null ? null : postsEntity.getUserAuthor().getUsername())
                        .commentResponseData(null)
                        .build()
                ).collect(Collectors.toList());

    }

    @Override
    public PostsResponseDto fetchPostById(Long id) {
        PostsEntity postsEntity = postsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<CommentResponseData> commentResponseDataList = commentsService.getCommentByPost(id);

        return PostsResponseDto.builder()
                .id(postsEntity.getId())
                .title(postsEntity.getTitle())
                .content(postsEntity.getContent())
                .userAuthorUsername(postsEntity.getUserAuthor() == null ? null : postsEntity.getUserAuthor().getUsername())
                .commentResponseData(commentResponseDataList)
                .build();

//        return modelMapper.map(postsEntity, PostsResponseDto.class);
    }

    @Override
    public PostsResponseDto createNewPost(PostsCreationDto postsCreationDto) {
        if (!userRepository.existsById(postsCreationDto.getUserAuthorId())){
            return PostsResponseDto.builder()
                    .title(null)
                    .content(null)
                    .userAuthorUsername(null)
                    .commentResponseData(null)
                    .build();
        }

        UsersEntity user = userRepository.findById(postsCreationDto.getUserAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//        PostsEntity postCreated = modelMapper.map(postsCreationDto, PostsEntity.class);

        PostsEntity postCreated = PostsEntity.builder()
                .title(postsCreationDto.getTitle())
                .content(postsCreationDto.getContent())
                .userAuthor(user)
                .build();
        postsRepository.save(postCreated);
        PostsResponseDto postsResponseDto = modelMapper.map(postCreated, PostsResponseDto.class);
        postsResponseDto.setUserAuthorUsername(user.getUsername());
        return postsResponseDto;
    }

    @Override
    public PostsResponseDto updatePostById(Long id, PostsCreationDto postsCreationDto) {
        if (!userRepository.existsById(postsCreationDto.getUserAuthorId()) || !postsRepository.existsById(id)){
            return PostsResponseDto.builder()
                    .title(null)
                    .content(null)
                    .build();
        }


        UsersEntity user = userRepository.findById(postsCreationDto.getUserAuthorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        PostsEntity foundPost = postsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        foundPost.setTitle(postsCreationDto.getTitle());
        foundPost.setContent(postsCreationDto.getContent());
        foundPost.setUserAuthor(foundPost.getUserAuthor());
        postsRepository.save(foundPost);
        PostsResponseDto postsResponseDto = modelMapper.map(foundPost, PostsResponseDto.class);
        postsResponseDto.setUserAuthorUsername(user.getUsername());
        return postsResponseDto;
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
