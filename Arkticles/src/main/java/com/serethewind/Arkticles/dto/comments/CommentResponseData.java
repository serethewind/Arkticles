package com.serethewind.Arkticles.dto.comments;

import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentResponseData {
    private String username;
    private String content;
    private Long timePosted;
}
