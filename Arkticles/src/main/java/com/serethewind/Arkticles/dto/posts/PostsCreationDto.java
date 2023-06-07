package com.serethewind.Arkticles.dto.posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostsCreationDto {
    private String title;
    private String content;
}
