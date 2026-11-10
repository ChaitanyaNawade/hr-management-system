package com.aarambh.hr_management.controller;
import com.aarambh.hr_management.dto.CreateDepartmentRequest;
import com.aarambh.hr_management.dto.DepartmentResponse;
import com.aarambh.hr_management.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController
{
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody CreateDepartmentRequest request)
    {
        DepartmentResponse response = departmentService.createDepartment(request);

        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments()
    {
        List<DepartmentResponse> response = departmentService.getAllDepartments();

        return  ResponseEntity.ok(response);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id)
    {
        DepartmentResponse response = departmentService.findDepartmentById(id);

        return  ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@Valid @RequestBody CreateDepartmentRequest request , @PathVariable Long id)
    {
        DepartmentResponse response = departmentService.updateDepartmentById(request,id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable Long id)
    {
        departmentService.deleteDepartmentById(id);

        return ResponseEntity.noContent().build();
    }
}
