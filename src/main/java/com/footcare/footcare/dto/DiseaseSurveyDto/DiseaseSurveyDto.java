package com.footcare.footcare.dto.DiseaseSurveyDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class DiseaseSurveyDto {

    private Long diseaseSurveyId;
    private Long categoryId;
    private Long memberId;
    private String diseaseScore;
    private String diseaseClassify;
    private Date diseaseDate;
}
