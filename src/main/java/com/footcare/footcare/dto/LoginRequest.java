package com.footcare.footcare.dto;

public class LoginRequest {

    private String id;  // 사용자 ID
    private String password;  // 비밀번호

    // 기본 생성자
    public LoginRequest() {}

    // Getter와 Setter
    public String getId() {  // 이제 getId()로 Spring Security에 전달
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
