package com.virtusa.dlvery.Inventory.Controller;

import com.virtusa.dlvery.Inventory.DTO.TransactionDTO;
import com.virtusa.dlvery.Inventory.Enum.PaymentStatus;
import com.virtusa.dlvery.Inventory.Repository.Service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable UUID transactionId) {
        logger.info("Received request to get transaction by ID: {}", transactionId);

        TransactionDTO transaction = transactionService.findTransactionById(transactionId);

        if (transaction == null) {
            logger.info("No transaction found with ID: {}", transactionId);
            return ResponseEntity.notFound().build();
        }

        logger.info("Returning transaction: {}", transaction.getTransactionId());
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        logger.info("Received request to get all transactions");

        List<TransactionDTO> transactions = transactionService.findAllTransactions();

        if (transactions.isEmpty()) {
            logger.info("No transactions found");
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} transactions", transactions.size());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/byOrder/{orderId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByOrder(@PathVariable UUID orderId) {
        logger.info("Received request to get transactions by order ID: {}", orderId);

        List<TransactionDTO> transactions = transactionService.findTransactionsByOrder(orderId);

        if (transactions.isEmpty()) {
            logger.info("No transactions found for order ID: {}", orderId);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} transactions", transactions.size());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/byAmount/{amount}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAmount(@PathVariable BigDecimal amount) {
        logger.info("Received request to get transactions by amount: {}", amount);

        List<TransactionDTO> transactions = transactionService.findTransactionsByAmount(amount);

        if (transactions.isEmpty()) {
            logger.info("No transactions found for amount: {}", amount);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} transactions", transactions.size());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/byTransactionDate/{transactionDate}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByTransactionDate(
            @PathVariable LocalDate transactionDate) {
        logger.info("Received request to get transactions by transaction date: {}", transactionDate);

        List<TransactionDTO> transactions = transactionService.findTransactionsByTransactionDate(transactionDate);

        if (transactions.isEmpty()) {
            logger.info("No transactions found for transaction date: {}", transactionDate);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} transactions", transactions.size());
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/byPaymentStatus/{paymentStatus}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByPaymentStatus(
            @PathVariable String paymentStatus) {
        logger.info("Received request to get transactions by payment status: {}", paymentStatus);

        List<TransactionDTO> transactions = transactionService.findTransactionsByPaymentStatus(PaymentStatus.valueOf(paymentStatus));

        if (transactions.isEmpty()) {
            logger.info("No transactions found for payment status: {}", paymentStatus);
            return ResponseEntity.noContent().build();
        }

        logger.info("Returning {} transactions", transactions.size());
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Validated @RequestBody TransactionDTO transactionDTO) {
        logger.info("Received request to create transaction for order ID: {}", transactionDTO.getOrderId());

        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDTO);

        if (createdTransaction == null) {
            logger.info("Transaction creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        logger.info("Transaction created successfully with ID: {}", createdTransaction.getTransactionId());
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID transactionId) {
        logger.info("Received request to delete transaction with ID: {}", transactionId);

        transactionService.deleteTransaction(transactionId);

        logger.info("Transaction deleted successfully with ID: {}", transactionId);
        return ResponseEntity.noContent().build();
    }
}
