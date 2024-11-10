package com.footcare.footcare.service.DiseaseSurvey;

import com.footcare.footcare.Repository.DiseaseSurvey.DiseaseSurveyRepository;
import com.footcare.footcare.Repository.Post.CategoryRepository;
import com.footcare.footcare.dto.DiseaseSurveyDto.CategoryScoreDto;
import com.footcare.footcare.dto.DiseaseSurveyDto.DiseaseSurveyDto;
import com.footcare.footcare.dto.DiseaseSurveyDto.TotalScoreRequestDto;
import com.footcare.footcare.entity.DiseaseSurvey.DiseaseSurvey;
import com.footcare.footcare.entity.Post.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiseaseSurveyService {

    @Autowired
    private DiseaseSurveyRepository diseaseSurveyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveScores(TotalScoreRequestDto requestDto) {
        for (CategoryScoreDto scoreDto : requestDto.getScores()) {
            // 각 카테고리 확인
            Category category = categoryRepository.findById(scoreDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + scoreDto.getCategoryId()));

            DiseaseSurvey diseaseSurvey = new DiseaseSurvey();
            diseaseSurvey.setCategory(category);
            diseaseSurvey.setMemberId(requestDto.getMemberId());
            diseaseSurvey.setDiseaseScore(String.valueOf(scoreDto.getScore()));
            diseaseSurvey.setDiseaseClassify(classifyScore(scoreDto.getScore()));
            diseaseSurvey.setDiseaseDate(new Date());

            diseaseSurveyRepository.save(diseaseSurvey);
        }
    }

    private String classifyScore(int score) {
        if (score >= 16) return "심각";
        else if (score >= 11) return "중증";
        else if (score >= 6) return "주의";
        else return "정상";
    }

    public List<DiseaseSurveyDto> getAllSurveys() {
        List<DiseaseSurvey> surveys = diseaseSurveyRepository.findAll();
        return surveys.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<DiseaseSurveyDto> getSurveysByMemberId(Long memberId) {
        List<DiseaseSurvey> surveys = diseaseSurveyRepository.findByMemberId(memberId);
        return surveys.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<DiseaseSurveyDto> getSurveysByMemberIdAndDate(Long memberId, Date date) {
        List<DiseaseSurvey> surveys = diseaseSurveyRepository.findByMemberIdAndDiseaseDate(memberId, date);
        return surveys.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Date> getSurveyDatesByMemberId(Long memberId) {
        return diseaseSurveyRepository.findDistinctDiseaseDatesByMemberId(memberId);
    }

    private DiseaseSurveyDto convertToDto(DiseaseSurvey survey) {
        DiseaseSurveyDto dto = new DiseaseSurveyDto();
        dto.setDiseaseSurveyId(survey.getDiseaseSurveyId());
        dto.setCategoryId(survey.getCategory().getCategoryId());
        dto.setMemberId(survey.getMemberId());
        dto.setDiseaseScore(survey.getDiseaseScore());
        dto.setDiseaseClassify(survey.getDiseaseClassify());
        dto.setDiseaseDate(survey.getDiseaseDate());
        return dto;
    }

}
