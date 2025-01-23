package com.footcare.footcare.dto.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Date signUpDate;
    private Date stopDate;
    private Date exitDate;
    private String address;
    private String icon;

}
