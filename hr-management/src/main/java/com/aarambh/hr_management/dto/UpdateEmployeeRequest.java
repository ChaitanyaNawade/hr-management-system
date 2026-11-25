package com.aarambh.hr_management.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest
{
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;


    private String phoneNumber;

    @NotNull(message = "Salary is required")
    @DecimalMin(value = "0.0",inclusive = false,message = "Salary must be greater than 0")
    private BigDecimal salary;

    @NotNull(message = "Department id is required")
    private Long departmentId;
}
