package com.footcare.footcare.service.Member;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.entity.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 설문조사 완료 여부 확인
    public boolean isSurveyCompleted(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return member.isPresent() && "Y".equals(member.get().getIsSurveyCompleted());
    }

    // 설문조사 완료 상태로 업데이트
    public void completeSurvey(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));
        member.setIsSurveyCompleted("Y");
        memberRepository.save(member);
    }

    // ID 중복 검사
    public boolean isIdAvailable(String id) {
        return !memberRepository.existsById(id);
    }

    // 전체 사용자 수
    public Long getTotalMembers() {
        return memberRepository.countAllMembers();
    }

    // 성별 구분 사용자 수
    public Map<String, Long> getMembersByGender() {
        List<Object[]> result = memberRepository.countMembersByGender();
        Map<String, Long> genderStats = new HashMap<>();
        for (Object[] row : result) {
            String gender = (String) row[0];
            Long count = (Long) row[1];
            genderStats.put(gender, count);
        }
        return genderStats;
    }

    // 월별 신규 가입자 수
    public Map<String, Long> getMonthlySignups() {
        List<Object[]> result = memberRepository.countMonthlySignups();
        Map<String, Long> monthlyStats = new LinkedHashMap<>();
        for (Object[] row : result) {
            String month = (String) row[0];
            Long count = (Long) row[1];
            monthlyStats.put(month, count);
        }
        return monthlyStats;
    }
}
