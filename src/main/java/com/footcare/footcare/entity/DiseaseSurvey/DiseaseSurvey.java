package com.footcare.footcare.entity.DiseaseSurvey;

import com.footcare.footcare.entity.Post.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "diseaseSurvey")
public class DiseaseSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diseaseSurveyId")
    private Long diseaseSurveyId;

    @Column(name = "memberId", nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @Column(name = "diseaseScore")
    private String diseaseScore;

    @Column(name = "diseaseClassify")
    private String diseaseClassify;

    @Column(name = "diseaseDate")
    @Temporal(TemporalType.DATE)
    private Date diseaseDate;

}
