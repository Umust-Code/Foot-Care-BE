package com.footcare.footcare.dto.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Long postId;
    private Long categoryId;
    private String postName;
    private String postContentName;
    private Date postDate;
    private Long postView;

    private Long likeCount = 0L;

}
