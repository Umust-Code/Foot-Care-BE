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

    private Long likeCount;
    private String postFg;

    public Long getLikeCount() {
        return likeCount == null ? 0L : likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount == null ? 0L : likeCount;
    }
}