package eu.deltasource.internship.bankingsystem.factory;

import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.enums.TransactionType;
import eu.deltasource.internship.bankingsystem.model.Transaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionFactory {
    public Transaction createSimpleTransaction(TransactionType type, String sourceIban, String sourceBankName, Currency sourceCurrency,
                                               double transferredAmount) {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setTransactionType(type);
        transaction.setSourceIban(sourceIban);
        transaction.setSourceBankName(sourceBankName);
        transaction.setSourceCurrency(sourceCurrency);
        transaction.setTransferredAmount(transferredAmount);
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
    }
}
