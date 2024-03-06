package com.virtusa.dlvery.Inventory.Repository.Service;

import com.virtusa.dlvery.Inventory.DTO.TransactionDTO;
import com.virtusa.dlvery.Inventory.Entities.Transaction;
import com.virtusa.dlvery.Inventory.Enum.PaymentStatus;
import com.virtusa.dlvery.Inventory.Repository.TransactionRepository;
import com.virtusa.dlvery.common.Util.DTOConversionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionDTO findTransactionById(UUID transactionId) {
        logger.info("Fetching transaction by ID: {}", transactionId);

        if (transactionId == null) {
            logger.error("Transaction ID is null. Unable to fetch transaction.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        Transaction transaction = transactionRepository.findByTransactionId(transactionId);

        if (transaction == null) {
            logger.info("No transaction found with ID: {}", transactionId);
            // Handle case when no transaction is found, return null/empty DTO
            return null;
        }

        return DTOConversionUtil.convertToTransactionDTO(transaction);
    }

    public List<TransactionDTO> findAllTransactions() {
        logger.info("Fetching all transactions");

        List<Transaction> transactions = transactionRepository.findAll();

        if (transactions.isEmpty()) {
            logger.info("No transactions found");
            // Handle case when no transactions are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> findTransactionsByOrder(UUID orderId) {
        logger.info("Fetching transactions by order ID: {}", orderId);

        List<Transaction> transactions = transactionRepository.findByOrder_OrderId(orderId);

        if (transactions.isEmpty()) {
            logger.info("No transactions found for order ID: {}", orderId);
            // Handle case when no transactions are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> findTransactionsByAmount(BigDecimal amount) {
        logger.info("Fetching transactions by amount: {}", amount);

        List<Transaction> transactions = transactionRepository.findByAmount(amount);

        if (transactions.isEmpty()) {
            logger.info("No transactions found for amount: {}", amount);
            // Handle case when no transactions are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> findTransactionsByTransactionDate(LocalDate transactionDate) {
        logger.info("Fetching transactions by transaction date: {}", transactionDate);

        List<Transaction> transactions = transactionRepository.findByTransactionDate(transactionDate);

        if (transactions.isEmpty()) {
            logger.info("No transactions found for transaction date: {}", transactionDate);
            // Handle case when no transactions are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToTransactionDTOList(transactions);
    }

    public List<TransactionDTO> findTransactionsByPaymentStatus(PaymentStatus paymentStatus) {
        logger.info("Fetching transactions by payment status: {}", paymentStatus);

        List<Transaction> transactions = transactionRepository.findByPaymentStatus(paymentStatus);

        if (transactions.isEmpty()) {
            logger.info("No transactions found for payment status: {}", paymentStatus);
            // Handle case when no transactions are found, return empty list
            return List.of();
        }

        return DTOConversionUtil.convertToTransactionDTOList(transactions);
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        logger.info("Creating transaction for order ID: {}", transactionDTO.getOrderId());

        if (transactionDTO.getOrderId() == null) {
            logger.error("Order ID is null. Unable to create transaction.");
            // Handle error appropriately, throw exception or return null/empty DTO
            return null;
        }

        // Convert DTO to entity
        Transaction transaction = DTOConversionUtil.convertToTransaction(transactionDTO);

        // Save transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Convert saved entity back to DTO
        return DTOConversionUtil.convertToTransactionDTO(savedTransaction);
    }

    public void deleteTransaction(UUID transactionId) {
        logger.info("Deleting transaction with ID: {}", transactionId);
        transactionRepository.deleteById(transactionId);
    }
}

