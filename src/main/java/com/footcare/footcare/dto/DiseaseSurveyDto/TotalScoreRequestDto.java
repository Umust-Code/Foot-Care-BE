package com.footcare.footcare.dto.DiseaseSurveyDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TotalScoreRequestDto {
    private Long memberId;
    private List<CategoryScoreDto> scores;
}


