package com.nutech.service;

import com.nutech.dto.BalanceResponse;
import com.nutech.dto.TopUpRequest;
import com.nutech.exception.BusinessException;
import com.nutech.model.User;
import com.nutech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Transactional
    public BalanceResponse topUpBalance(String email, TopUpRequest request) {
        if (request.getTopUpAmount() == null || request.getTopUpAmount() <= 0) {
            throw new BusinessException("Parameter amount hanya boleh angka dan tidak boleh lebih kecil dari 0", 102);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        Long newBalance = user.getBalance() + request.getTopUpAmount();
        user.setBalance(newBalance);
        userRepository.save(user);

        // Record transaction
        transactionService.recordTopUpTransaction(user, request.getTopUpAmount());

        return BalanceResponse.builder()
                .balance(newBalance)
                .build();
    }

    public BalanceResponse getBalance(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        return BalanceResponse.builder()
                .balance(user.getBalance())
                .build();
    }
}