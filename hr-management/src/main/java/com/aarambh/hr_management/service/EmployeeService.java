package com.aarambh.hr_management.service;

import com.aarambh.hr_management.dto.CreateEmployeeRequest;
import com.aarambh.hr_management.dto.EmployeeResponse;
import com.aarambh.hr_management.dto.UpdateEmployeeRequest;
import com.aarambh.hr_management.entity.Department;
import com.aarambh.hr_management.entity.Employee;
import com.aarambh.hr_management.enums.Role;
import com.aarambh.hr_management.exception.DepartmentNotFoundException;
import com.aarambh.hr_management.exception.DuplicateEmployeeException;
import com.aarambh.hr_management.exception.EmployeeNotFoundException;
import com.aarambh.hr_management.repository.DepartmentRepository;
import com.aarambh.hr_management.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;



    private EmployeeResponse converToRespone(Employee employee)
    {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setEmail(employee.getEmail());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setSalary(employee.getSalary());
        response.setJoiningDate(employee.getJoiningDate());
        response.setRole(employee.getRole());
        response.setDepartmentName(employee.getDepartment().getDepartmentName());
        response.setCreatedAt(employee.getCreatedAt());
        response.setUpdatedAt(employee.getUpdatedAt());

        return  response;
    }

    //---------------------------------------CREATE EMPLOYEE-------------------------------------
    public EmployeeResponse createEmployee(CreateEmployeeRequest request)
    {
        if(employeeRepository.existsByEmail(request.getEmail()))
        {
            throw new DuplicateEmployeeException("Employee with this email already exists: "+request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(()->new DepartmentNotFoundException("department not found with this id: "+request.getDepartmentId()));

        Employee employee = new Employee();

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setRole(Role.EMPLOYEE);
        employee.setDepartment(department);


        Employee savedEmployee =  employeeRepository.save(employee);

        return converToRespone(savedEmployee);
    }


    //---------------------------------------GET ALL EMPLOYEE-------------------------------------
    public List<EmployeeResponse> getAllEmployees()
    {
        List<Employee> employees = employeeRepository.findAllWithDepartment();

        List<EmployeeResponse> responses = new ArrayList<>();

        for(Employee employee : employees)
        {
            EmployeeResponse response = converToRespone(employee);

            responses.add(response);
        }

        return responses;
    }

    //--------------------------------------- GET EMPLOYEE BY ID-------------------------------------
    public EmployeeResponse getEmployeeById(Long id)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("There is no such employee with this id: "+id));


        return converToRespone(employee);
    }

    //---------------------------------------UPDATE EMPLOYEE-------------------------------------
    public EmployeeResponse updateEmployeeById(Long id , UpdateEmployeeRequest request)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("There is no such with this id: "+id));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(()->new DepartmentNotFoundException("There is no such department with this id: "+request.getDepartmentId()));


        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setSalary(request.getSalary());
        employee.setDepartment(department);

        Employee updatedEmployee = employeeRepository.save(employee);

        EmployeeResponse response = converToRespone(updatedEmployee);

        return response;
    }


    //---------------------------------------DELETE EMPLOYEE-------------------------------------
    public void deleteEmployee(Long id)
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException("There is no such employee with this id: "+id));

        employeeRepository.delete(employee);
    }

    //---------------------------------------PAGINATION LOGIC-------------------------------------
    public Page<EmployeeResponse> getAllEmployees(int page, int size,String sortBy,String direction)
    {

        //This is the list of allowed fields to sorting logic
        List<String> allowedFields = List.of(
                "id",
                "firstName",
                "lastName",
                "email",
                "salary",
                "joiningDate",
                "createdAt"
        );

        //This loop check for the sortBy type is equal to the field
        boolean isValidField = false;
        for(String str:allowedFields)
        {
            if(str.equalsIgnoreCase(sortBy))
            {
                isValidField = true;
                break;
            }
        }

        if(!isValidField)
        {
            throw new IllegalArgumentException("Invalid sorting field: "+sortBy);
        }

        Sort sort = null;
        if(direction.equalsIgnoreCase("asc")) {
              sort  = Sort.by(sortBy).ascending();
        }else if(direction.equalsIgnoreCase("desc"))
        {
            sort = Sort.by(sortBy).descending();
        }
        else
        {
            throw new IllegalArgumentException("Invalid sorting direction. Use asc or desc");
        }

        if(page < 0)
        {
            throw new IllegalArgumentException("Page number  cannot be negative");
        }

        if(size < 1 || size > 100)
        {
            throw new IllegalArgumentException("page size must be between 1 and 100");
        }


        Pageable pageable = PageRequest.of(page,size,sort);

        Page<Employee> employees = employeeRepository.findAll(pageable);

        List<EmployeeResponse> responses = new ArrayList<>();

        for(Employee employee:employees.getContent())
        {
            EmployeeResponse response = converToRespone(employee);

            responses.add(response);
        }

        Page<EmployeeResponse> responsePage =
                new PageImpl<>(responses,pageable,employees.getTotalElements());


        return responsePage;
    }
}
