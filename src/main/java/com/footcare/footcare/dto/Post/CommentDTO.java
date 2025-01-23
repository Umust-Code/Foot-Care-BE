package com.footcare.footcare.dto.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private Long commentId;
    private Long postId;
    private String commentContent;
    private Date commentDate;

    private Long memberId;
    private String name;
    private String icon;

}

