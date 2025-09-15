package com.example.demo.exception;

public class InactiveEmployeeException extends RuntimeException{
    public InactiveEmployeeException(String message) {
        super(message);
    }
}
