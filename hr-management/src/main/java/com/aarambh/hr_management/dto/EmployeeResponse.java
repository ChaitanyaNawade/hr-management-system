package com.aarambh.hr_management.dto;

import com.aarambh.hr_management.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse
{

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private BigDecimal salary;
    private LocalDate joiningDate;
    private Role role;
    private String departmentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
