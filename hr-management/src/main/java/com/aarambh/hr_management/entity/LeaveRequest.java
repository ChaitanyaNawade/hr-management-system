package com.aarambh.hr_management.entity;

import com.aarambh.hr_management.enums.LeaveStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "leave_requests")
public class LeaveRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false )
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    private  LocalDate endDate;

    @NotBlank
    @Column(nullable = false,length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private LeaveStatus status;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @PrePersist
    protected  void onCreate()
    {
        appliedAt = LocalDateTime.now();
    }

    private LocalDateTime reviewedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",nullable = false)
    private Employee employee;
}
