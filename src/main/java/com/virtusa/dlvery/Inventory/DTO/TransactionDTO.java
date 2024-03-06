package com.virtusa.dlvery.Inventory.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virtusa.dlvery.Inventory.Entities.Transaction;
import com.virtusa.dlvery.Inventory.Enum.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {

    private UUID transactionId;
    private UUID orderId; // Assuming this is the order ID associated with the transaction
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;

    private PaymentStatus paymentStatus;

    public TransactionDTO(Transaction transaction) {
        this.transactionId = transaction.getTransactionId();
        this.orderId = transaction.getOrder().getOrderId(); // Assuming there is a getOrderId() method in Order entity
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
        this.paymentStatus = transaction.getPaymentStatus();
    }
}
