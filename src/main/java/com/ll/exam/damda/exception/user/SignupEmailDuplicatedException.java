package com.ll.exam.damda.exception.user;

public class SignupEmailDuplicatedException extends RuntimeException {
    public SignupEmailDuplicatedException(String message) {
        super(message);
    }
}