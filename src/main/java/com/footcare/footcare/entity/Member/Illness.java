package com.footcare.footcare.entity.Member;

import com.footcare.footcare.entity.Member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "illness")
@Getter
public class Illness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long illnessId;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Temporal(TemporalType.DATE)
    private Date illnessDate;

    private String illnessPrevent;

    private String illnessReh;

    private String illnessPain;

}
