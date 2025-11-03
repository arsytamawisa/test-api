package com.nutech.service;

import com.nutech.dto.LoginRequest;
import com.nutech.dto.LoginResponse;
import com.nutech.exception.BusinessException;
import com.nutech.model.User;
import com.nutech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Email atau password salah", 103));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Email atau password salah", 103);
        }

        // Generate JWT token
        String token = tokenService.generateToken(user.getEmail());

        // Build response according to API documentation
        return LoginResponse.builder()
                .status(0)
                .message("Login berhasil")
                .data(LoginResponse.LoginData.builder()
                        .token(token)
                        .build())
                .build();
    }
}