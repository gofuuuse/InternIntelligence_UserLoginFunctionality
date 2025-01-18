package com.UserLoginFunctionality.service;

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

