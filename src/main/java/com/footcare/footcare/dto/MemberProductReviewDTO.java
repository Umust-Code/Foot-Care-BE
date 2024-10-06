package com.footcare.footcare.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberProductReviewDTO {

    private Long memberProductReviewId;
    private Long memberId;
    private String productReviewId;
}
