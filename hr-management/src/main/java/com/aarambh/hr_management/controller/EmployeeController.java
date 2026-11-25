package com.aarambh.hr_management.controller;

import com.aarambh.hr_management.dto.CreateEmployeeRequest;
import com.aarambh.hr_management.dto.EmployeeResponse;
import com.aarambh.hr_management.dto.UpdateEmployeeRequest;
import com.aarambh.hr_management.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController
{
    private final EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request)
    {
        EmployeeResponse response = employeeService.createEmployee(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees()
    {
        System.out.println("Controller reached. Current user: " +
                org.springframework.security.core.context.SecurityContextHolder
                        .getContext().getAuthentication());

        List<EmployeeResponse> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id)
    {
        EmployeeResponse response = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployeeById(@Valid @RequestBody UpdateEmployeeRequest request
            , @PathVariable Long id)
    {
       EmployeeResponse response = employeeService.updateEmployeeById(id,request);

       return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id)
    {
        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/page")
    public ResponseEntity<Page<EmployeeResponse>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5")int size,
                                                                  @RequestParam(defaultValue = "firstName")String sortBy,
                                                                  @RequestParam(defaultValue = "asc")String dirction)
    {
        Page<EmployeeResponse> employeeResponses = employeeService.getAllEmployees(page,size,sortBy,dirction);

        return ResponseEntity.ok(employeeResponses);
    }
}
