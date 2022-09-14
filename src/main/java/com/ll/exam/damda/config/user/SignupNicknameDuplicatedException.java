package com.ll.exam.damda.config.user;

public class SignupNicknameDuplicatedException extends RuntimeException {
    public SignupNicknameDuplicatedException(String message) {
        super(message);
    }
}
