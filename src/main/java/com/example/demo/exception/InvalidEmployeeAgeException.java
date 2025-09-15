package com.example.demo.exception;

public class InvalidEmployeeAgeException extends RuntimeException{
    public InvalidEmployeeAgeException(String message) {
        super(message);
    }
}
