package com.virtusa.dlvery.Inventory.Repository;

import com.virtusa.dlvery.Inventory.Entities.Transaction;
import com.virtusa.dlvery.Inventory.Enum.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {


    Transaction findByTransactionId(UUID transactionId);


    List<Transaction> findByOrder_OrderId(UUID order);

    List<Transaction> findByAmount(BigDecimal amount);

    List<Transaction> findByTransactionDate(LocalDate transactionDate);

    List<Transaction> findByPaymentStatus(PaymentStatus paymentStatus);

}
