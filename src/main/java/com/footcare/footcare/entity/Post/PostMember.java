package com.footcare.footcare.entity.Post;


import com.footcare.footcare.entity.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity@Table(name = "postmember")
@Setter
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

    @Column(name = "likefg", nullable = false, length = 1)
    private String likefg = "N";


    @Column(nullable = false)
    private LocalDate createdDate;

}
