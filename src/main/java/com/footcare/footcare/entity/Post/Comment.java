package com.footcare.footcare.entity.Post;

import com.footcare.footcare.entity.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private String commentContent;

    @Temporal(TemporalType.DATE)
    private Date commentDate;

}
