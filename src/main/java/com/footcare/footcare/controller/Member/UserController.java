package com.footcare.footcare.controller.Member;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.dto.Member.UpdatePasswordDTO;
import com.footcare.footcare.dto.Member.UpdateUserDTO;
import com.footcare.footcare.dto.Member.UserInfoDTO;
import com.footcare.footcare.entity.Member.Member;
import com.footcare.footcare.service.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long memberId, @RequestHeader("Authorization") String token) {
        // 사용자 존재 확인
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();


            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setId(member.getId());
            userInfoDTO.setName(member.getName());
            userInfoDTO.setAddress(member.getAddress());
            userInfoDTO.setPhone(member.getPhone());

            return ResponseEntity.ok(userInfoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found.");
        }
    }

    @DeleteMapping("/{memberId}/delete")
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

    @PutMapping("/{memberId}/update")
    public ResponseEntity<?> updateUser(
            @PathVariable Long memberId,
            @RequestBody UpdateUserDTO updateRequest,
            @RequestHeader("Authorization") String token) {

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 정보 업데이트
            member.setName(updateRequest.getName());
            member.setAddress(updateRequest.getAddress());
            member.setPhone(updateRequest.getPhone());
            memberRepository.save(member);

            return ResponseEntity.ok("User information updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found.");
        }
    }
    @PutMapping("/{memberId}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Long memberId,
            @RequestBody UpdatePasswordDTO passwordRequest,
            @RequestHeader("Authorization") String token) {

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 기존 비밀번호 확인
            if (!passwordEncoder.matches(passwordRequest.getOldPassword(), member.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Current password is incorrect.");
            }

            // 새 비밀번호 설정
            member.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            memberRepository.save(member);

            return ResponseEntity.ok("Password updated successfully!");
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

    // ID 중복 검사
    @GetMapping("/check-id")
    public ResponseEntity<String> checkIdAvailability(@RequestParam String id) {
        boolean isAvailable = memberService.isIdAvailable(id);

        String responseMessage = isAvailable ? "N" : "Y";
        return ResponseEntity.ok(responseMessage);
    }
}