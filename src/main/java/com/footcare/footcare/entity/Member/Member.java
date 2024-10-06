package com.footcare.footcare.entity.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    private String phone;

    private LocalDateTime signUpDate;
    private LocalDateTime stopDate;
    private LocalDateTime exitDate;  // 변경된 부분

    private String address;
}

