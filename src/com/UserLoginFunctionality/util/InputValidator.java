package com.UserLoginFunctionality.util;

import org.springframework.stereotype.Component;

@Component
public class InputValidator {
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
}