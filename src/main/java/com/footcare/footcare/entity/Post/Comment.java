package com.footcare.footcare.entity.Post;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "comment")
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    private String commentContent;

    @Temporal(TemporalType.DATE)
    private Date commentDate;

}
