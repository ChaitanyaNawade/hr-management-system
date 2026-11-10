package com.aarambh.hr_management.repository;

import com.aarambh.hr_management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long>
{
    boolean existsByDepartmentName(String departmentname);
}
