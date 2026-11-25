package com.aarambh.hr_management.service;

import com.aarambh.hr_management.dto.CreateLeaveRequest;
import com.aarambh.hr_management.dto.LeaveRequestResponse;
import com.aarambh.hr_management.entity.Employee;
import com.aarambh.hr_management.entity.LeaveRequest;
import com.aarambh.hr_management.enums.LeaveStatus;
import com.aarambh.hr_management.exception.EmployeeNotFoundException;
import com.aarambh.hr_management.exception.InvalidLeaveRequestStateException;
import com.aarambh.hr_management.exception.LeaveRequestNotFoundException;
import com.aarambh.hr_management.repository.EmployeeRepository;
import com.aarambh.hr_management.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LeaveRequestService
{
    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    private LeaveRequestResponse convertToResponse(LeaveRequest leaveRequest)
    {
        LeaveRequestResponse response = new LeaveRequestResponse();

        response.setStartDate(leaveRequest.getStartDate());
        response.setId(leaveRequest.getId());
        response.setEmployeeId(leaveRequest.getEmployee().getId());
        response.setEmployeeName(leaveRequest.getEmployee().getFirstName()+" "+leaveRequest.getEmployee().getLastName());
        response.setEndDate(leaveRequest.getEndDate());
        response.setReason(leaveRequest.getReason());
        response.setStatus(leaveRequest.getStatus());
        response.setAppliedAt(leaveRequest.getAppliedAt());
        response.setReviewedAt(leaveRequest.getReviewedAt());

        return response;
    }

    public LeaveRequestResponse createLeaveRequest(Long employeeId,CreateLeaveRequest request)
    {

        if(request.getStartDate().isBefore(LocalDate.now()))
        {
            throw new IllegalArgumentException("start date should not be before current date");
        }

        if(request.getEndDate().isBefore(request.getStartDate()))
        {
            throw new IllegalArgumentException("end date should be after the start date");
        }

        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(()->new EmployeeNotFoundException("Employee not found with id: "+employeeId));

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setStartDate(request.getStartDate());
        leaveRequest.setEndDate(request.getEndDate());
        leaveRequest.setReason(request.getReason());

        leaveRequest.setEmployee(employee);
        leaveRequest.setStatus(LeaveStatus.PENDING);

        LeaveRequest savedLeaveRequest =  leaveRequestRepository.save(leaveRequest);

        return convertToResponse(savedLeaveRequest);
    }


    public List<LeaveRequestResponse> getMyLeaveRequest(Long employeeId)
    {
        Employee employee = employeeRepository.findById(employeeId).
                orElseThrow(() -> new EmployeeNotFoundException("There is no such employee with this id: " + employeeId));


        List<LeaveRequest> leaveRequests =
                leaveRequestRepository.findByEmployeeId(employeeId);

        List<LeaveRequestResponse> responses = new ArrayList<>();

        for (LeaveRequest request : leaveRequests)
        {
            responses.add(convertToResponse(request));
        }

        return responses;
    }


    public List<LeaveRequestResponse> getAllLeaveRequests()
    {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();

        List<LeaveRequestResponse> responses = new ArrayList<>();

        for(LeaveRequest request : leaveRequests)
        {

            responses.add(convertToResponse(request));
        }
        return  responses;
    }


    public LeaveRequestResponse approveLeaveRequest(Long leaveReqestId)
    {
        LeaveRequest request = leaveRequestRepository.findById(leaveReqestId)
                .orElseThrow(()->new LeaveRequestNotFoundException("There is no such leave request associated with this id: "+leaveReqestId));

        if(request.getStatus() != LeaveStatus.PENDING)
        {
            throw new InvalidLeaveRequestStateException("Only pending leave requests can be approved");
        }

        request.setStatus(LeaveStatus.APPROVED);
        request.setReviewedAt(LocalDateTime.now());

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(request);

        return convertToResponse(savedLeaveRequest);
    }

    public LeaveRequestResponse rejectLeaveRequest(Long leaveRequestId)
    {
        LeaveRequest request = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(()->new LeaveRequestNotFoundException("There is no such leave request associated with this id: "+leaveRequestId));

        if(request.getStatus() != LeaveStatus.PENDING)
        {
            throw new InvalidLeaveRequestStateException("Only pending leave requests can be approved or rejected");
        }

        request.setStatus(LeaveStatus.REJECTED);
        request.setReviewedAt(LocalDateTime.now());

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(request);

        return convertToResponse(savedLeaveRequest);
    }

}
