package com.footcare.footcare.dto;

public class LoginRequest {

    private String username;  // 사용자 이름
    private String password;  // 비밀번호

    // 기본 생성자
    public LoginRequest() {}

    // Getter와 Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}