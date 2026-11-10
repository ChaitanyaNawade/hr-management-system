package com.aarambh.hr_management.service;

import com.aarambh.hr_management.dto.CreateDepartmentRequest;
import com.aarambh.hr_management.dto.DepartmentResponse;
import com.aarambh.hr_management.entity.Department;
import com.aarambh.hr_management.exception.DepartmentNotFoundException;
import com.aarambh.hr_management.exception.DuplicateDepartmentException;
import com.aarambh.hr_management.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DepartmentService
{
    private final DepartmentRepository departmentRepository;

    //---------------------------------------CreateDepartment-----------------------------
    public DepartmentResponse createDepartment(CreateDepartmentRequest request)
    {
        if(departmentRepository.existsByDepartmentName(request.getDepartmentName()))
        {
            throw new DuplicateDepartmentException("Department already exists with name: "+request.getDepartmentName());
        }

        Department department = new Department();
        department.setDepartmentName(request.getDepartmentName());
        department.setDescription(request.getDescription());

        Department savedDepartment = departmentRepository.save(department);
        DepartmentResponse response = new DepartmentResponse();

        response.setId(savedDepartment.getId());
        response.setDepartmentName(savedDepartment.getDepartmentName());
        response.setDescription(savedDepartment.getDescription());
        response.setCreatedAt(savedDepartment.getCreatedAt());

        return response;
    }

    //---------------------------------------RetriveAllDepartment-----------------------------
    public List<DepartmentResponse> getAllDepartments()
    {
        List<Department> departments = departmentRepository.findAll();

        List<DepartmentResponse> responses = new ArrayList<>();

        for(Department fetchedDepartment : departments)
        {
            DepartmentResponse response = new DepartmentResponse();

            response.setId(fetchedDepartment.getId());
            response.setDepartmentName(fetchedDepartment.getDepartmentName());
            response.setDescription(fetchedDepartment.getDescription());
            response.setCreatedAt(fetchedDepartment.getCreatedAt());

            responses.add(response);
        }
        return responses;
    }

    //--------------------------------------FindDepartment-----------------------------
    public DepartmentResponse findDepartmentById(Long id)
    {
        Department department = departmentRepository.findById(id).orElseThrow(()->new DepartmentNotFoundException("Department not exist with this id: "+id));

        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setDepartmentName(department.getDepartmentName());
        response.setDescription(department.getDescription());
        response.setCreatedAt(department.getCreatedAt());

        return  response;
    }

    //---------------------------------------UpdateDepartment-----------------------------
    public DepartmentResponse updateDepartmentById(CreateDepartmentRequest request , Long id) {

        Department oldDepratment = departmentRepository.findById(id).
                orElseThrow(() -> new DepartmentNotFoundException("Department not exist with this id: " + id));

        if(!oldDepratment.getDepartmentName().equalsIgnoreCase(request.getDepartmentName()) && departmentRepository.existsByDepartmentName(request.getDepartmentName()))
        {
            throw new DuplicateDepartmentException("Department already exists with name: "+request.getDepartmentName());
        }

        oldDepratment.setDepartmentName(request.getDepartmentName());
        oldDepratment.setDescription(request.getDescription());

        Department savedDepartment = departmentRepository.save(oldDepratment);

        DepartmentResponse response = new DepartmentResponse();

        response.setId(savedDepartment.getId());
        response.setDepartmentName(savedDepartment.getDepartmentName());
        response.setDescription(savedDepartment.getDescription());
        response.setCreatedAt(savedDepartment.getCreatedAt());

        return response;
    }

    //---------------------------------------DeleteDepartment-----------------------------
    public  void deleteDepartmentById(Long id)
    {
        Department department = departmentRepository.findById(id).
                orElseThrow(()->new DepartmentNotFoundException("Department not found with this id: "+id));

        departmentRepository.delete(department);
    }
}
