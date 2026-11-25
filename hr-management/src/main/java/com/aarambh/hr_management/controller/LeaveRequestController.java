package com.aarambh.hr_management.controller;

import com.aarambh.hr_management.dto.CreateLeaveRequest;
import com.aarambh.hr_management.dto.LeaveRequestResponse;
import com.aarambh.hr_management.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController
{
    private final LeaveRequestService leaveRequestService;

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<LeaveRequestResponse> createLeaveRequest(@Valid @RequestBody CreateLeaveRequest request, @PathVariable Long employeeId)
    {
       LeaveRequestResponse leaveRequestResponse = leaveRequestService.createLeaveRequest(employeeId,request);

       return ResponseEntity.status(HttpStatus.CREATED).body(leaveRequestResponse);
    }


    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestResponse>> getAllLeaveRequest(@PathVariable Long employeeId)
    {
        List<LeaveRequestResponse> responses = leaveRequestService.getMyLeaveRequest(employeeId);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestResponse>> getAllLeaveRequests()
    {
        List<LeaveRequestResponse> responses = leaveRequestService.getAllLeaveRequests();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<LeaveRequestResponse> approveLeaveRequest(@PathVariable Long id)
    {
        LeaveRequestResponse response = leaveRequestService.approveLeaveRequest(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PutMapping("/{id}/reject")
    public ResponseEntity<LeaveRequestResponse> rejectLeaveRequest(@PathVariable Long id)
    {
        LeaveRequestResponse response = leaveRequestService.rejectLeaveRequest(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
