package com.UserLoginFunctionality.service;

import com.UserLoginFunctionality.repository.UserRepository;
import com.UserLoginFunctionality.util.PasswordUtils;
import com.UserLoginFunctionality.util.TokenUtils;
import com.UserLoginFunctionality.exception.InvalidCredentialsException;
import com.UserLoginFunctionality.model.LoginRequest;
import com.UserLoginFunctionality.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private TokenUtils tokenUtils;

    public String authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordUtils.verifyPassword(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return tokenUtils.generateToken(user);
    }

    public void logoutUser(String token) {
        tokenUtils.invalidateToken(token);
    }
}
