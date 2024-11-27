package com.footcare.footcare.Repository.DiseaseSurvey;

import com.footcare.footcare.entity.DiseaseSurvey.DiseaseSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiseaseSurveyRepository extends JpaRepository<DiseaseSurvey, Long> {

    List<DiseaseSurvey> findByMember_MemberId(Long memberId);
    List<DiseaseSurvey> findByMember_MemberIdAndDiseaseDate(Long memberId, Date diseaseDate);
    @Query("SELECT DISTINCT d.diseaseDate FROM DiseaseSurvey d WHERE d.member.memberId = :memberId")
    List<Date> findDistinctDiseaseDatesByMemberId(Long memberId);
}

