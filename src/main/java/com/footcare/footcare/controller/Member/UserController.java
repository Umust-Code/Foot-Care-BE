package com.footcare.footcare.controller.Member;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.entity.Member.Member;
import com.footcare.footcare.service.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://foot-care-fe.vercel.app")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long memberId, @RequestHeader("Authorization") String token) {
        // 토큰에서 ID 확인 및 인증 (필요한 경우 추가)

        // 해당 ID의 회원이 존재하는지 확인
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            memberRepository.deleteById(memberId); // 회원 삭제
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found.");
        }
    }

    @GetMapping("/{memberId}/check-survey")
    public ResponseEntity<String> checkSurveyStatus(@PathVariable Long memberId) {
        if (!memberService.isSurveyCompleted(memberId)) {
            return ResponseEntity.ok("Redirect to survey page");
        }
        return ResponseEntity.ok("Survey already completed");
    }


    @PostMapping("/{memberId}/complete-survey")
    public ResponseEntity<String> completeSurvey(@PathVariable Long memberId) {
        memberService.completeSurvey(memberId);
        return ResponseEntity.ok("Survey marked as completed");
    }
}