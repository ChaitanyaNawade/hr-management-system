package com.aarambh.hr_management.repository;

import com.aarambh.hr_management.entity.LeaveRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest,Long>
{
   @EntityGraph(attributePaths = "employee")
   List<LeaveRequest> findByEmployeeId(Long employeeId);

   @EntityGraph(attributePaths = "employee")
   List<LeaveRequest> findAll();
}
