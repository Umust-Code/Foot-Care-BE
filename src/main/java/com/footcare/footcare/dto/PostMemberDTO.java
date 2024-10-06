package com.footcare.footcare.dto;

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

