package com.footcare.footcare.entity.Post;

import com.footcare.footcare.entity.PostMember;
import jakarta.persistence.*;
import lombok.Getter;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private Long categoryId;

    private String postName;

    private String postContentName;

    @Temporal(TemporalType.DATE)
    private Date postDate;

    private Long postView;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostMember> postMembers;

}


