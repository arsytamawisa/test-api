package com.nutech.service;

import com.nutech.dto.*;
import com.nutech.exception.BusinessException;
import com.nutech.model.Transaction;
import com.nutech.model.ServiceModel;
import com.nutech.model.User;
import com.nutech.repository.ServiceRepository;
import com.nutech.repository.TransactionRepository;
import com.nutech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    // Get transaction history tanpa pagination
    public TransactionHistoryListResponse getTransactionHistory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        List<Transaction> transactions = transactionRepository.findByUserOrderByCreatedOnDesc(user);

        List<TransactionHistoryResponse> transactionResponses = transactions.stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());

        return TransactionHistoryListResponse.builder()
                .offset(0)
                .limit(transactionResponses.size())
                .records(transactionResponses)
                .build();
    }

    // Get transaction history dengan pagination
    public TransactionHistoryListResponse getTransactionHistoryWithPagination(String email, Integer offset, Integer limit) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        if (offset == null || offset < 0) offset = 0;
        if (limit == null || limit <= 0) limit = 5;

        Pageable pageable = PageRequest.of(offset, limit);
        Page<Transaction> transactionPage = transactionRepository.findByUserOrderByCreatedOnDesc(user, pageable);

        List<TransactionHistoryResponse> transactionResponses = transactionPage.getContent().stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());

        return TransactionHistoryListResponse.builder()
                .offset(offset)
                .limit(limit)
                .records(transactionResponses)
                .build();
    }

    // Get semua services - PERBAIKAN DI SINI
    public ServiceListResponse getAllServices() {
        List<ServiceModel> services = serviceRepository.findAll(Sort.by(Sort.Direction.ASC, "serviceName"));

        List<ServiceListResponse.ServiceItem> serviceData = services.stream()
                .map(this::mapToServiceItem)
                .collect(Collectors.toList());

        return ServiceListResponse.builder()
                .status(0)
                .message("Sukses")
                .data(serviceData) // Method .data() bukan .services()
                .build();
    }

    // Process payment
    @Transactional
    public PaymentResponse processPayment(String email, PaymentRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User tidak ditemukan", 108));

        // Validasi service code
        if (request.getServiceCode() == null || request.getServiceCode().trim().isEmpty()) {
            throw new BusinessException("Service atau Layanan tidak ditemukan", 108);
        }

        ServiceModel service = serviceRepository.findByServiceCode(request.getServiceCode())
                .orElseThrow(() -> new BusinessException("Service atau Layanan tidak ditemukan", 108));

        // Validasi balance
        if (user.getBalance() < service.getServiceTariff()) {
            throw new BusinessException("Saldo tidak mencukupi", 102);
        }

        // Process payment
        Long newBalance = user.getBalance() - service.getServiceTariff();
        user.setBalance(newBalance);
        userRepository.save(user);

        // Record transaction
        String invoiceNumber = generateInvoiceNumber();
        Transaction transaction = Transaction.builder()
                .invoiceNumber(invoiceNumber)
                .user(user)
                .transactionType("PAYMENT")
                .description("Pembayaran " + service.getServiceName())
                .totalAmount(service.getServiceTariff())
                .build();

        transactionRepository.save(transaction);

        return PaymentResponse.builder()
                .invoiceNumber(invoiceNumber)
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .transactionType("PAYMENT")
                .totalAmount(service.getServiceTariff())
                .createdOn(transaction.getCreatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    // Helper methods
    private TransactionHistoryResponse mapToTransactionResponse(Transaction transaction) {
        return TransactionHistoryResponse.builder()
                .invoiceNumber(transaction.getInvoiceNumber())
                .transactionType(transaction.getTransactionType())
                .description(transaction.getDescription())
                .totalAmount(transaction.getTotalAmount())
                .createdOn(transaction.getCreatedOn())
                .build();
    }

    // PERBAIKAN: Method baru untuk ServiceItem
    private ServiceListResponse.ServiceItem mapToServiceItem(ServiceModel service) {
        return ServiceListResponse.ServiceItem.builder()
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .serviceIcon(service.getServiceIcon())
                .serviceTariff(service.getServiceTariff())
                .build();
    }

    // Method lama untuk ServiceResponse (jika masih dibutuhkan)
    private ServiceItemResponse mapToServiceResponse(ServiceModel service) {
        return ServiceItemResponse.builder()
                .serviceCode(service.getServiceCode())
                .serviceName(service.getServiceName())
                .serviceIcon(service.getServiceIcon())
                .serviceTariff(service.getServiceTariff())
                .build();
    }

    private String generateInvoiceNumber() {
        return "INV_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }

    // Method untuk record topup transaction (digunakan oleh BalanceService)
    public void recordTopUpTransaction(User user, Long amount) {
        String invoiceNumber = "TOPUP_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);

        Transaction transaction = Transaction.builder()
                .invoiceNumber(invoiceNumber)
                .user(user)
                .transactionType("TOPUP")
                .description("Top Up Balance")
                .totalAmount(amount)
                .build();

        transactionRepository.save(transaction);
    }
}