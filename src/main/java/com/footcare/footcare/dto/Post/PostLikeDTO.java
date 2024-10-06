package com.footcare.footcare.dto.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostLikeDTO {

    private Long postLikeId;
    private Long postId;
    private Long postLikeCount;

}

