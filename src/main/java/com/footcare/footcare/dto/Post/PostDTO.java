package com.footcare.footcare.dto.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private LocalDate createdDate; // 게시물 생성 날짜
    private Long memberId;
    private String postFg;

    private Long likeCount;

    public Long getLikeCount() {
        return likeCount == null ? 0L : likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount == null ? 0L : likeCount;
    }
}