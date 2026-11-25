package com.aarambh.hr_management.dto;

import com.aarambh.hr_management.enums.Role;
import lombok.Data;

@Data
public class LoginResponse
{
    private String token;
    private String tokenType;
    private String email;
    private Role role;
}
