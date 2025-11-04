package com.nutech.service;

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
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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