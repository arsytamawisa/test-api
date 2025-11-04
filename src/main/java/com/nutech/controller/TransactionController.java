package com.nutech.controller;

import com.nutech.dto.*;
import com.nutech.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("history")
    public ResponseEntity<GenericResponse<TransactionHistoryListResponse>> transactionHistory(
            Authentication authentication,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) Integer limit) {

        String email = authentication.getName();

        TransactionHistoryListResponse history = transactionService.getTransactionHistoryWithPagination(email, offset, limit);

        GenericResponse<TransactionHistoryListResponse> response = GenericResponse.<TransactionHistoryListResponse>builder()
                .status(0)
                .message("Get History Berhasil")
                .data(history)
                .build();

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<GenericResponse<PaymentResponse>> createTransaction(
            Authentication authentication,
            @RequestBody PaymentRequest request) {

        String email = authentication.getName();

        PaymentResponse payment = transactionService.processPayment(email, request);

        GenericResponse<PaymentResponse> response = GenericResponse.<PaymentResponse>builder()
                .status(0)
                .message("Transaksi berhasil")
                .data(payment)
                .build();

        return ResponseEntity.ok(response);
    }
}