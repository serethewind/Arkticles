package com.serethewind.Arkticles.service.comments.serviceImpl;

import com.serethewind.Arkticles.dto.comments.CommentsRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentsResponseDto;
import com.serethewind.Arkticles.entity.CommentsEntity;
import com.serethewind.Arkticles.entity.PostsEntity;
import com.serethewind.Arkticles.repository.CommentsRepository;
import com.serethewind.Arkticles.repository.PostsRepository;
import com.serethewind.Arkticles.service.comments.CommentsServiceInterface;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CommentsServiceImplementation implements CommentsServiceInterface {

    private CommentsRepository commentsRepository;
    private PostsRepository postsRepository;
    private ModelMapper modelMapper;

    @Override
    public CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto) {
        //check if post exist
        if (!postsRepository.existsById(commentsRequestDto.getPostId())) {
            return CommentsResponseDto.builder()
                    .content(null)
                    .posts(null)
                    .build();
        }

//        CommentsEntity createdEntity = modelMapper.map(commentsRequestDto, CommentsEntity.class);
        PostsEntity posts = postsRepository.findById(commentsRequestDto.getPostId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        CommentsEntity createdEntity = CommentsEntity.builder()
                .content(commentsRequestDto.getContent())
                .posts(posts)
                .build();
        commentsRepository.save(createdEntity);
        return modelMapper.map(createdEntity, CommentsResponseDto.class);
    }

    @Override
    public CommentsResponseDto updateComment(Long id, CommentsRequestDto commentsRequestDto) {
        if (!commentsRepository.existsById(id)) {
            return CommentsResponseDto.builder().content(null).build();
        }


        CommentsEntity entity = commentsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        modelMapper.map(commentsRequestDto, entity);
        commentsRepository.save(entity);
        return modelMapper.map(entity, CommentsResponseDto.class);
    }

    @Override
    public String deleteComment(Long id) {
        if (!commentsRepository.existsById(id)) {
            return "Delete operation not successful. Comment does not exist";
        }

        CommentsEntity entity = commentsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        commentsRepository.delete(entity);
        return "Delete operation successful";
    }
}
