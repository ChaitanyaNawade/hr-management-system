package com.aarambh.hr_management.exception;

public class InvalidLeaveRequestStateException extends RuntimeException
{
    public InvalidLeaveRequestStateException(String message)
    {
        super(message);
    }
}
