package com.footcare.footcare.controller.DiseaseSurvey;

import com.footcare.footcare.dto.DiseaseSurveyDto.DiseaseSurveyDto;
import com.footcare.footcare.dto.DiseaseSurveyDto.TotalScoreRequestDto;
import com.footcare.footcare.security.JwtTokenProvider;
import com.footcare.footcare.service.DiseaseSurvey.DiseaseSurveyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://foot-care-fe.vercel.app")
@RequestMapping("/api/disease-survey")
public class DiseaseSurveyController {

    @Autowired
    private DiseaseSurveyService diseaseSurveyService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @PostMapping("/submit-scores")
    public ResponseEntity<String> submitScores(@RequestBody TotalScoreRequestDto requestDto, HttpServletRequest request) {
        try {
            String accessToken = jwtTokenProvider.getJwtFromRequest(request);
            if (accessToken == null || !jwtTokenProvider.validateToken(accessToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("401 Unauthorized: Invalid or missing token");
            }

            diseaseSurveyService.saveScores(requestDto);
            return ResponseEntity.ok("Scores have been recorded successfully.");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error recording scores: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<DiseaseSurveyDto>> getAllSurveys() {
        List<DiseaseSurveyDto> surveys = diseaseSurveyService.getAllSurveys();
        return ResponseEntity.ok(surveys);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<DiseaseSurveyDto>> getSurveysByMemberId(@PathVariable Long memberId) {
        List<DiseaseSurveyDto> surveys = diseaseSurveyService.getSurveysByMemberId(memberId);
        return ResponseEntity.ok(surveys);
    }

    @GetMapping("/member/{memberId}/date/{date}")
    public ResponseEntity<List<DiseaseSurveyDto>> getSurveysByMemberIdAndDate(
            @PathVariable Long memberId,
            @PathVariable String date) {

        Date parsedDate;
        try {
            parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build(); // 날짜 형식 오류 시 400 반환
        }

        List<DiseaseSurveyDto> surveys = diseaseSurveyService.getSurveysByMemberIdAndDate(memberId, parsedDate);
        return ResponseEntity.ok(surveys);
    }

    @GetMapping("/member/{memberId}/dates")
    public ResponseEntity<List<Date>> getSurveyDatesByMemberId(@PathVariable Long memberId) {
        List<Date> surveyDates = diseaseSurveyService.getSurveyDatesByMemberId(memberId);
        return ResponseEntity.ok(surveyDates);
    }

    @GetMapping("/all-surveys/{memberId}")
    public ResponseEntity<List<Map<String, Object>>> getAllSurveysGroupedByDate(
            @PathVariable Long memberId) {
        List<Map<String, Object>> surveys = diseaseSurveyService.getAllSurveysGroupedByDate(memberId);
        return ResponseEntity.ok(surveys);
    }



}
