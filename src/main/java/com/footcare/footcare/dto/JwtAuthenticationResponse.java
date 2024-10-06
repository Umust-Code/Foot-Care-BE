package com.footcare.footcare.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthenticationResponse {


    private String token;  // 클라이언트에게 반환할 JWT 토큰

    // 기본 생성자
    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

}
