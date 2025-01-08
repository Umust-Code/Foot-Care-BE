package com.footcare.footcare.dto.Post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostMemberDTO {

    private Long postMemberId;
    private Long memberId;
    private Long postId;

}

