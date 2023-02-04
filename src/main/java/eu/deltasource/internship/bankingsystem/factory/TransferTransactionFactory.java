package eu.deltasource.internship.bankingsystem.factory;

import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.enums.TransactionType;
import eu.deltasource.internship.bankingsystem.model.Transaction;
import eu.deltasource.internship.bankingsystem.model.TransferTransaction;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransferTransactionFactory {

    public Transaction createTransferTransaction(String id, TransactionType type, String sourceIban, String sourceBankName, Currency sourceCurrency,
                                                 double transferredAmount, double exchangeRate, String targetIban, String targetBankName,
                                                 double additionalFee, Currency targetCurrency) {
        Transaction transaction = new TransferTransaction();
        transaction.setId(id);
        transaction.setTransactionType(type);
        transaction.setSourceIban(sourceIban);
        transaction.setSourceBankName(sourceBankName);
        transaction.setSourceCurrency(sourceCurrency);
        transaction.setTransferredAmount(transferredAmount);
        ((TransferTransaction) transaction).setExchangeRate(exchangeRate);
        transaction.setTimestamp(LocalDateTime.now());
        ((TransferTransaction) transaction).setTargetIban(targetIban);
        ((TransferTransaction) transaction).setTargetBankName(targetBankName);
        ((TransferTransaction) transaction).setAdditionalFee(additionalFee);
        ((TransferTransaction) transaction).setTargetCurrency(targetCurrency);
        return transaction;
    }
}
