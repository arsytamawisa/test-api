package com.nutech.repository;

import com.nutech.model.Transaction;
import com.nutech.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByCreatedOnDesc(User user);

    Page<Transaction> findByUserOrderByCreatedOnDesc(User user, Pageable pageable);

    @Query("SELECT COUNT(t) > 0 FROM Transaction t WHERE t.invoiceNumber = :invoiceNumber")
    boolean existsByInvoiceNumber(@Param("invoiceNumber") String invoiceNumber);
}