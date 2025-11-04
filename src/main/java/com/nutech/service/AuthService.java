package com.nutech.service;

import com.nutech.dto.LoginRequest;
import com.nutech.dto.LoginResponse;
import com.nutech.dto.RegistrationRequest;
import com.nutech.dto.RegistrationResponse;
import com.nutech.exception.BusinessException;
import com.nutech.model.User;
import com.nutech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Email atau password salah", 103));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("Email atau password salah", 103);
        }

        String token = tokenService.generateToken(user.getEmail());

        return LoginResponse.builder()
                .status(0)
                .message("Login berhasil")
                .data(LoginResponse.LoginData.builder()
                        .token(token)
                        .build())
                .build();
    }

    @Transactional
    public RegistrationResponse register(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email sudah terdaftar", 102);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .balance(0L)
                .build();

        userRepository.save(user);

        return RegistrationResponse.builder()
                .status(0)
                .message("Registrasi berhasil silakan login")
                .data(null).build();
    }
}