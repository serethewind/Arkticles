package com.serethewind.Arkticles.service.comments.serviceImpl;

import com.serethewind.Arkticles.dto.comments.CommentResponseData;
import com.serethewind.Arkticles.dto.comments.CommentsRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentsResponseDto;
import com.serethewind.Arkticles.entity.CommentsEntity;
import com.serethewind.Arkticles.entity.PostsEntity;
import com.serethewind.Arkticles.repository.CommentsRepository;
import com.serethewind.Arkticles.repository.PostsRepository;
import com.serethewind.Arkticles.repository.UserRepository;
import com.serethewind.Arkticles.service.comments.CommentsServiceInterface;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CommentsServiceImplementation implements CommentsServiceInterface {

    private CommentsRepository commentsRepository;
    private PostsRepository postsRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto) {
        //check if post exist
        if (!postsRepository.existsById(commentsRequestDto.getPostId())) {
            return CommentsResponseDto.builder()
                    .content(null)
                    .postTitle(null)
                    .build();
            //replace with proper exception handling
        }

        if (commentsRequestDto.getUserAuthorId() != null && !userRepository.existsById(commentsRequestDto.getUserAuthorId())) {
            return CommentsResponseDto.builder().username(null).content(null).postTitle(null).build();
        }

        //        CommentsEntity createdEntity = modelMapper.map(commentsRequestDto, CommentsEntity.class);
        PostsEntity posts = postsRepository.findById(commentsRequestDto.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CommentsEntity createdEntity = CommentsEntity.builder()
                .content(commentsRequestDto.getContent())
                .posts(posts)
                .build();

        if (commentsRequestDto.getUserAuthorId() == null) {
            createdEntity.setUserAuthor(null);
        } else {
            createdEntity.setUserAuthor(userRepository.findById(commentsRequestDto.getUserAuthorId()).orElseThrow((() -> new ResponseStatusException(HttpStatus.NOT_FOUND))));
        }
        commentsRepository.save(createdEntity);

        CommentsResponseDto commentsResponseDto = new CommentsResponseDto();

        if (createdEntity.getUserAuthor() == null) {
            commentsResponseDto.setUsername("Anonymous User");
        } else {
            commentsResponseDto.setUsername(createdEntity.getUserAuthor().getUsername());
        }

        commentsResponseDto.setContent(createdEntity.getContent());
        commentsResponseDto.setPostTitle(posts.getTitle());
        return commentsResponseDto;
    }

    @Override
    public CommentsResponseDto updateComment(Long id, CommentsRequestDto commentsRequestDto) {
        //neither post nor comment exist
        if (!postsRepository.existsById(commentsRequestDto.getPostId()) || !commentsRepository.existsById(id)) {
            return CommentsResponseDto.builder()
                    .username(null)
                    .content(null)
                    .postTitle(null).build();
        }

        PostsEntity posts = postsRepository.findById(commentsRequestDto.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        CommentsEntity entity = commentsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //user making the comment is not null. so user should be registered but the user's details doesn't match the person who created the entity in the first place.
        if (commentsRequestDto.getUserAuthorId() != null && !commentsRequestDto.getUserAuthorId().equals(entity.getUserAuthor().getId())) {
            return CommentsResponseDto.builder()
                    .username(null)
                    .content(null)
                    .postTitle(null).build();
        }

        if (commentsRequestDto.getUserAuthorId() == null && entity.getUserAuthor().getId() != null) {
            return CommentsResponseDto.builder()
                    .username(null)
                    .content(null)
                    .postTitle(null).build();
        }

        entity.setContent(commentsRequestDto.getContent());
        entity.setPosts(entity.getPosts());
        commentsRepository.save(entity);
        return CommentsResponseDto.builder()
                .username(entity.getUserAuthor().getUsername())
                .content(entity.getContent())
                .postTitle(posts.getTitle())
                .build();
    }

    @Override
    public String deleteComment(Long id, CommentsRequestDto commentsRequestDto) {

        //user who owns the post can delete comment.
        //if the person who made the comment is registered, he can delete the comment
        if (!commentsRepository.existsById(id)) {
            return "Delete operation not successful. Comment does not exist";
        }

        CommentsEntity entity = commentsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        PostsEntity posts = postsRepository.findById(commentsRequestDto.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //user is not null. so there is an id, but the id doesn't match the person who created the comment in the first instance.
        if (commentsRequestDto.getUserAuthorId() != null && !commentsRequestDto.getUserAuthorId().equals(entity.getUserAuthor().getId())) {
            return "Only user who made post can delete. Delete not successful";
        }

        //if the creator of the post or the creator of the comment
        if (userRepository.existsById(entity.getPosts().getUserAuthor().getId()) || userRepository.existsById(entity.getUserAuthor().getId())) {
            commentsRepository.delete(entity);
            return "Delete operation successful";

        }

        return "Only registered user who made post can delete. Delete not successful";
    }

    @Override
    public List<CommentResponseData> getCommentByPost(Long id) {

        if (commentsRepository.isCommentByPost(id) == null) {
            return null;
        } else {
            List<CommentsEntity> commentsEntityList = commentsRepository.findCommentByPostID(id);


           List<CommentResponseData> commentResponseDataList = commentsEntityList.stream().map(commentsEntity -> CommentResponseData.builder()
                    .username((commentsEntity.getUserAuthor() == null) ? "Anonymous" : commentsEntity.getUserAuthor().getUsername())
                    .content(commentsEntity.getContent())
                    .timePosted(Duration.between(commentsEntity.getCreationDate(), LocalDateTime.now()).toMinutes())
                    .build()
                    ).toList();

           return commentResponseDataList;

        }


    }
}
