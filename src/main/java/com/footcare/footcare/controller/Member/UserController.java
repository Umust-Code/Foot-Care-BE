package com.footcare.footcare.controller.Member;

import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.entity.Member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MemberRepository memberRepository;

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, @RequestHeader("Authorization") String token) {
        // 토큰에서 ID 확인 및 인증 (필요한 경우 추가)

        // 해당 ID의 회원이 존재하는지 확인
        Optional<Member> memberOptional = memberRepository.findById(userId);
        if (memberOptional.isPresent()) {
            memberRepository.deleteById(userId); // 회원 삭제
            return ResponseEntity.ok("User deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User not found.");
        }
    }
}