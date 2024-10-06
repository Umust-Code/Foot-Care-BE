package com.footcare.footcare.entity;

import com.footcare.footcare.entity.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "memberproductReview")
@Getter
public class MemberProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberProductReviewId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private String productReviewId;

}

