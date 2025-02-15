package com.footcare.footcare.service.DiseaseSurvey;

import com.footcare.footcare.Repository.DiseaseSurvey.DiseaseSurveyRepository;
import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.Repository.Post.CategoryRepository;
import com.footcare.footcare.dto.DiseaseSurveyDto.CategoryScoreDto;
import com.footcare.footcare.dto.DiseaseSurveyDto.DiseaseSurveyDto;
import com.footcare.footcare.dto.DiseaseSurveyDto.TotalScoreRequestDto;
import com.footcare.footcare.entity.DiseaseSurvey.DiseaseSurvey;
import com.footcare.footcare.entity.Member.Member;
import com.footcare.footcare.entity.Post.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiseaseSurveyService {

    @Autowired
    private DiseaseSurveyRepository diseaseSurveyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public void saveScores(TotalScoreRequestDto requestDto) {

         Member member = memberRepository.findById(requestDto.getMemberId())
                 .orElseThrow(() -> new IllegalArgumentException("Member not found"+requestDto.getMemberId()));
        for (CategoryScoreDto scoreDto : requestDto.getScores()) {
            // 각 카테고리 확인
            Category category = categoryRepository.findById(scoreDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + scoreDto.getCategoryId()));

            DiseaseSurvey diseaseSurvey = new DiseaseSurvey();
            diseaseSurvey.setCategory(category);
            diseaseSurvey.setMember(member);
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
        List<DiseaseSurvey> surveys = diseaseSurveyRepository.findByMember_MemberId(memberId);
        return surveys.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<DiseaseSurveyDto> getSurveysByMemberIdAndDate(Long memberId, Date date) {
        List<DiseaseSurvey> surveys = diseaseSurveyRepository.findByMember_MemberIdAndDiseaseDate(memberId, date);
        return surveys.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Date> getSurveyDatesByMemberId(Long memberId) {
        return diseaseSurveyRepository.findDistinctDiseaseDatesByMemberId(memberId);
    }

    public List<Map<String, Object>> getAllSurveysGroupedByDate(Long memberId) {
        // 특정 사용자의 모든 설문조사 데이터 가져오기
        List<DiseaseSurvey> surveys = diseaseSurveyRepository.findByMember_MemberId(memberId);

        // 날짜별로 그룹화
        Map<String, List<DiseaseSurvey>> groupedByDate = surveys.stream()
                .collect(Collectors.groupingBy(survey -> survey.getDiseaseDate().toString()));

        // 결과를 변환하여 리스트로 반환
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<DiseaseSurvey>> entry : groupedByDate.entrySet()) {
            Map<String, Object> surveyData = new HashMap<>();
            surveyData.put("date", entry.getKey());

            entry.getValue().forEach(survey ->
                    surveyData.put("d" + survey.getCategory().getCategoryId(), Integer.parseInt(survey.getDiseaseScore()))
            );

            result.add(surveyData);
        }

        return result;
    }

    private DiseaseSurveyDto convertToDto(DiseaseSurvey survey) {


        DiseaseSurveyDto dto = new DiseaseSurveyDto();
        dto.setDiseaseSurveyId(survey.getDiseaseSurveyId());
        dto.setCategoryId(survey.getCategory().getCategoryId());
        dto.setMemberId(survey.getMember().getMemberId()); // Member 객체에서 ID 추출
        dto.setDiseaseScore(survey.getDiseaseScore());
        dto.setDiseaseClassify(survey.getDiseaseClassify());
        dto.setDiseaseDate(survey.getDiseaseDate());
        return dto;
    }

}
