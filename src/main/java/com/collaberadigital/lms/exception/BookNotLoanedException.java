package com.collaberadigital.lms.exception;

public class BookNotLoanedException extends RuntimeException {
    public BookNotLoanedException(String message) {
        super(message);
    }
}
