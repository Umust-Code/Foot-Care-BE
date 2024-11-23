package com.footcare.footcare.controller.Member;
import com.footcare.footcare.Repository.Member.MemberRepository;
import com.footcare.footcare.dto.JwtAuthenticationResponse;
import com.footcare.footcare.dto.LoginRequest;
import com.footcare.footcare.dto.SignupRequest;
import com.footcare.footcare.entity.Member.Member;
import com.footcare.footcare.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://foot-care-fe.vercel.app")
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;





    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 인증 수행
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getId(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Access 및 Refresh 토큰 생성
            String accessToken = jwtTokenProvider.generateAccessToken(loginRequest.getId());
            String refreshToken = jwtTokenProvider.generateRefreshToken(loginRequest.getId());

            // 로그인한 사용자 정보 조회
            Member user = memberRepository.findById(loginRequest.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // 응답 데이터에 토큰과 설문조사 완료 여부 추가
            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);
            response.put("memberId", user.getMemberId());
            response.put("isSurveyCompleted", user.getIsSurveyCompleted());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication failed: " + e.getMessage());
        }
    }



    //    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
//        if (jwtTokenProvider.validateToken(refreshToken)) {
//            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
//            String newAccessToken = jwtTokenProvider.generateAccessToken(username);
//            return ResponseEntity.ok(new JwtAuthenticationResponse(newAccessToken));
//        } else {
//            return ResponseEntity.status(403).body("Invalid Refresh Token");
//        }
//    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        // "Bearer " 접두사를 제거하여 실제 토큰을 추출
        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        if (jwtTokenProvider.validateToken(refreshToken)) {
            // Refresh Token에서 사용자 이름 추출
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

            // 새 Access Token 생성
            String newAccessToken = jwtTokenProvider.generateAccessToken(username);
            return ResponseEntity.ok(new JwtAuthenticationResponse(newAccessToken));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Refresh Token");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) { //MAP
        // 아이디 중복 체크
        if (memberRepository.existsById(signupRequest.getId())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        // 새로운 Member 엔티티 생성
        Member user = new Member();
        user.setId(signupRequest.getId());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));  // 비밀번호 암호화
        user.setName(signupRequest.getName());
        user.setPhone(signupRequest.getPhone());
        user.setAddress(signupRequest.getAddress());

        // 현재 날짜를 LocalDateTime으로 변환하여 설정
        LocalDateTime signUpDate = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        user.setSignUpDate(signUpDate);

        // Member 엔티티를 데이터베이스에 저장
        memberRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }


}