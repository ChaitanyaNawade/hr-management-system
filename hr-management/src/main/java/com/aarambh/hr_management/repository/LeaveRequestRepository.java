package com.aarambh.hr_management.repository;

import com.aarambh.hr_management.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long>
{
 List<LeaveRequest> findByEmployeeId(Long employeeId);
}
