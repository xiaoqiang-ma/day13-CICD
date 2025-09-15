package com.example.demo.exception;

public class InvalidAgeAndSalaryEmployeeException extends RuntimeException{
    public InvalidAgeAndSalaryEmployeeException(String message) {
        super(message);
    }
}
