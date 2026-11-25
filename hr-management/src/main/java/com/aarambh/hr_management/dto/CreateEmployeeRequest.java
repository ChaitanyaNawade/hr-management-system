package com.aarambh.hr_management.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest
{
    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotBlank
    @Size(min = 8 , max = 100)
    private String password;

    private String phoneNumber;

    @NotNull
    @DecimalMin(value = "0.0",inclusive = false)
    private BigDecimal salary;

    @NotNull
    private LocalDate joiningDate;

    @NotNull
    private Long departmentId;
}
