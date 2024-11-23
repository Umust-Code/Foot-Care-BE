package com.footcare.footcare.dto.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDTO {
    private String name;
    private String address;
    private String phone;
    private String currentPassword;
    private String newPassword;
}
