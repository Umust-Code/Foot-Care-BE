package com.footcare.footcare.dto.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class IllnessDTO {

    private Long illnessId;
    private Long memberId;
    private Date illnessDate;
    private String illnessPrevent;
    private String illnessReh;
    private String illnessPain;
}

