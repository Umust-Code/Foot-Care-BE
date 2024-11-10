package com.footcare.footcare.Repository.DiseaseSurvey;

import com.footcare.footcare.entity.DiseaseSurvey.DiseaseSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiseaseSurveyRepository extends JpaRepository<DiseaseSurvey, Long> {

    List<DiseaseSurvey> findByMemberId(Long memberId);
    List<DiseaseSurvey> findByMemberIdAndDiseaseDate(Long memberId, Date diseaseDate);
    @Query("SELECT DISTINCT d.diseaseDate FROM DiseaseSurvey d WHERE d.memberId = :memberId")
    List<Date> findDistinctDiseaseDatesByMemberId(Long memberId);
}

