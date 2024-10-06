package com.footcare.footcare.entity;


import com.footcare.footcare.entity.Member.Member;
import com.footcare.footcare.entity.Post.Post;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "postmember")
@Getter
public class PostMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postMemberId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;


}
