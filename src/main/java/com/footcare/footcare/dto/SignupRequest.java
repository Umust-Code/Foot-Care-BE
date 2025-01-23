package com.footcare.footcare.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private String id;  // 회원 아이디
    private String password;  // 비밀번호
    private String name;      // 이름
    private String phone;     // 휴대폰 번호 (옵션)
    private String address;   // 배송 주소 (옵션)
    private String sex;
    private String icon;

    public SignupRequest() {
    }
}