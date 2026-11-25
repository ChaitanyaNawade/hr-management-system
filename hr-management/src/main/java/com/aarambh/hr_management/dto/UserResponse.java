package com.aarambh.hr_management.dto;

import com.aarambh.hr_management.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.internal.Abstract;

@Data
public class UserResponse
{
    private Long id;
    private String email;
    private Role role;
}
