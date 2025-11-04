package com.nutech.controller;

import com.nutech.dto.BalanceResponse;
import com.nutech.dto.GenericResponse;
import com.nutech.dto.TopUpRequest;
import com.nutech.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/balance")
    public ResponseEntity<GenericResponse<BalanceResponse>> getBalance(Authentication authentication) {
        String email = authentication.getName();

        BalanceResponse balance = balanceService.getBalance(email);

        GenericResponse<BalanceResponse> response = GenericResponse.<BalanceResponse>builder()
                .status(0)
                .message("Get Balance Berhasil")
                .data(balance)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/topup")
    public ResponseEntity<GenericResponse<BalanceResponse>> topUpBalance(
            Authentication authentication,
            @RequestBody TopUpRequest request) {

        String email = authentication.getName();

        BalanceResponse updatedBalance = balanceService.topUpBalance(email, request);

        GenericResponse<BalanceResponse> response = GenericResponse.<BalanceResponse>builder()
                .status(0)
                .message("Top Up Balance berhasil")
                .data(updatedBalance)
                .build();

        return ResponseEntity.ok(response);
    }
}