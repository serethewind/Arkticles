package com.serethewind.Arkticles.service.comments.serviceImpl;

import com.serethewind.Arkticles.dto.comments.CommentDeleteRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentResponseData;
import com.serethewind.Arkticles.dto.comments.CommentsRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentsResponseDto;
import com.serethewind.Arkticles.entity.CommentsEntity;
import com.serethewind.Arkticles.entity.PostsEntity;
import com.serethewind.Arkticles.exceptions.BadRequestException;
import com.serethewind.Arkticles.exceptions.ResourceNotFoundException;
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
import java.util.Objects;
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

        if (!postsRepository.existsById(commentsRequestDto.getPostId())) {
            //comment can only be created when there is a previous post. attempting to create comment without post is a bad request
            throw new BadRequestException("Bad request. Comment is not affixed to any post");
        }

        if (commentsRequestDto.getUserAuthorId() != null && !userRepository.existsById(commentsRequestDto.getUserAuthorId())) {
            //two conditions required to create comment. User can be null or User can be registered. If the user is not null, then the user id in the dto must be correct.
            throw new BadRequestException("Bad request. Comment cannot be made");
        }

        PostsEntity posts = postsRepository.findById(commentsRequestDto.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CommentsEntity createdEntity = CommentsEntity.builder()
                .content(commentsRequestDto.getContent())
                .posts(posts)
                .userAuthor(commentsRequestDto.getUserAuthorId() == null ? null : userRepository.findById(commentsRequestDto.getUserAuthorId()).orElseThrow(() -> new ResourceNotFoundException("User not found")))
                .build();

        commentsRepository.save(createdEntity);

        CommentsResponseDto commentsResponseDto = new CommentsResponseDto();

        commentsResponseDto.setUsername(createdEntity.getUserAuthor() == null ? "Anonymous User" : createdEntity.getUserAuthor().getUsername());
        commentsResponseDto.setContent(createdEntity.getContent());
        commentsResponseDto.setPostTitle(posts.getTitle());
        return commentsResponseDto;
    }

    @Override
    public CommentsResponseDto updateComment(Long id, CommentsRequestDto commentsRequestDto) {

        if (!postsRepository.existsById(commentsRequestDto.getPostId()) || !commentsRepository.existsById(id)) {
            //neither post nor comment exist hence update is not possible. throw a bad request.
            throw new BadRequestException("Bad request, comment not found");
        }

        PostsEntity posts = postsRepository.findById(commentsRequestDto.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        CommentsEntity entity = commentsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        //only registered user who created a comment can edit a comment. an anonymous user who creates a comment cannot edit a comment.
        if (commentsRequestDto.getUserAuthorId() != null && !commentsRequestDto.getUserAuthorId().equals(entity.getUserAuthor().getId())) {
            //in this condition, the supposed user is registered however the id doesn't match the id of the user who created the comment
            throw new BadRequestException("Update not possible");
        }

        if (commentsRequestDto.getUserAuthorId() == null) {
            //this prevents an anonymous user from updating a comment
            throw new BadRequestException("Bad request. Not possible");
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
    public String deleteComment(Long commentId, CommentDeleteRequestDto commentDeleteRequestDto, Long userAuthorId) {

        //user who owns the post can delete comment.
        //if the person who made the comment is registered, he can delete the comment
        //if the person who made the comment is anonymous, his comment can be deleted by the owner of the post
        if (!commentsRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found");
        }

        CommentsEntity entity = commentsRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        PostsEntity posts = postsRepository.findById(entity.getPosts().getId()).orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (Objects.equals(entity.getUserAuthor().getId(), commentDeleteRequestDto.getUserAuthorId())) {
            commentsRepository.delete(entity);
            return "Delete successful";
        }

        if(commentDeleteRequestDto.getUserAuthorId() == null && Objects.equals(posts.getUserAuthor().getId(), userAuthorId)){
            commentsRepository.delete(entity);
            return "Delete successful";
        }

        if (commentDeleteRequestDto.getUserAuthorId() == null) {
            throw new BadRequestException("Anonymous user cannot delete comment");
        }

        throw new BadRequestException("Delete unsuccessful");
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
                    .timePosted(Duration.between(commentsEntity.getCreationDate(), LocalDateTime.now()).toHours() + " hours ago")
                    .build()
            ).toList();

            return commentResponseDataList;

        }


    }
}
