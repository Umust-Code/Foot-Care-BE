package com.footcare.footcare.entity.Member;

import com.footcare.footcare.entity.DiseaseSurvey.DiseaseSurvey;
import com.footcare.footcare.entity.Post.PostMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String password;

    private String name;

    private String sex;

    private String phone;

    private LocalDateTime signUpDate;
    private LocalDateTime stopDate;
    private LocalDateTime exitDate;  // 변경된 부분

    private String address;

    @Column(nullable = false, length = 1)
    private String isSurveyCompleted = "N";

    @Column(name = "fg", nullable = false, length = 1)
    private String fg = "N"; // 기본값을 'N'으로 설정

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostMember> postMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiseaseSurvey> diseaseSurveys;

    // 연관관계 편의 메서드
    public void addPostMember(PostMember postMember) {
        postMembers.add(postMember);
        postMember.setMember(this);
    }
}

