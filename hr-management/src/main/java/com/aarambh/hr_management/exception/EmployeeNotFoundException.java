package com.aarambh.hr_management.exception;

public class EmployeeNotFoundException extends  RuntimeException
{
    public EmployeeNotFoundException(String message)
    {
        super(message);
    }
}
