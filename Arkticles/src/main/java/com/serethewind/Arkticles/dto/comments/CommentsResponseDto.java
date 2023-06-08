package com.serethewind.Arkticles.dto.comments;

import com.serethewind.Arkticles.entity.PostsEntity;
import com.serethewind.Arkticles.repository.PostsRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsResponseDto {
    private String username;
    private String content;
    private String postTitle;

}
