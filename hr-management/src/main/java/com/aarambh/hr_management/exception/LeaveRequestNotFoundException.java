package com.aarambh.hr_management.exception;

public class LeaveRequestNotFoundException extends RuntimeException
{
    public LeaveRequestNotFoundException(String message)
    {
        super(message);
    }
}
