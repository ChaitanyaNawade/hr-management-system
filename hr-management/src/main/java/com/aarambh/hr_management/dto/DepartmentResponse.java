package com.aarambh.hr_management.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse
{
    private Long id;
    private String departmentName;
    private String description;
    private LocalDateTime createdAt;
}
