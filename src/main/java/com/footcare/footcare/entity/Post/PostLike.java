package com.footcare.footcare.entity.Post;


import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "postlike")
@Getter
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    private Long postLikeCount;
}

