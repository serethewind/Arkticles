package com.serethewind.Arkticles.service.comments;

import com.serethewind.Arkticles.dto.comments.CommentDeleteRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentResponseData;
import com.serethewind.Arkticles.dto.comments.CommentsRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentsResponseDto;

import java.util.List;

public interface CommentsServiceInterface {
    CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto);

    CommentsResponseDto updateComment(Long id, CommentsRequestDto commentsRequestDto);

    String deleteComment(Long id, CommentDeleteRequestDto commentDeleteRequestDto, Long userAuthorId);

    List<CommentResponseData> getCommentByPost(Long id);
}
