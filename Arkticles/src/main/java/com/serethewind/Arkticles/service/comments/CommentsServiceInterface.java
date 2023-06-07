package com.serethewind.Arkticles.service.comments;

import com.serethewind.Arkticles.dto.comments.CommentsRequestDto;
import com.serethewind.Arkticles.dto.comments.CommentsResponseDto;

public interface CommentsServiceInterface {
    CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto);

    CommentsResponseDto updateComment(Long id, CommentsRequestDto commentsRequestDto);

    String deleteComment(Long id);
}
