package com.footcare.footcare.dto.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

    private Long memberId;
    private String id;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime signUpDate;  // LocalDateTime 타입
    private LocalDateTime stopDate;    // LocalDateTime 타입
    private LocalDateTime exitDate;    // LocalDateTime 타입
    private String address;

}
