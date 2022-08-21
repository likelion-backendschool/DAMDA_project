package com.ll.exam.damda.config.user;

public class SignupUsernameDuplicatedException extends RuntimeException {
    public SignupUsernameDuplicatedException(String message) {
        super(message);
    }
}