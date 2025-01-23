package com.footcare.footcare.entity.Post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;  // 카테고리와의 연관 관계 추가

    private Long category_id;


    private String postName;

    private String postContentName;

    @Temporal(TemporalType.DATE)
    private Date postDate;

    private Long postView;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long likeCount = 0L;

    private String postFg;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostMember> postMembers;



}
