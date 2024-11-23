package com.footcare.footcare.dto.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
