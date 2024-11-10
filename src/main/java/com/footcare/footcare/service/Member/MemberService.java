package com.footcare.footcare.service.Member;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.entity.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
