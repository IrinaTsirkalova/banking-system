package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.enums.TransactionType;

import java.time.LocalDateTime;

public class Transaction {
    private String id;
    private TransactionType transactionType;
    private String sourceIban;
    private String sourceBankName;
    private Currency sourceCurrency;
    private double transferredAmount;
    private LocalDateTime timestamp;

    public String getId(){
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getSourceIban() {
        return sourceIban;
    }

    public String getSourceBankName() {
        return sourceBankName;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public double getTransferredAmount() {
        return transferredAmount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSourceIban(String sourceIban) {
        this.sourceIban = sourceIban;
    }

    public void setSourceBankName(String sourceBankName) {
        this.sourceBankName = sourceBankName;
    }

    public void setTransferredAmount(double transferredAmount) {
        this.transferredAmount = transferredAmount;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String toString(){
        return  "Transaction information: -----------------\n" +
                "Transaction id: " + id +
                "; Bank name: " + sourceBankName +
                "; Source IBAN: " + sourceIban +
                "; Transferred amount: " + transferredAmount +
                "; Source currency: " + sourceCurrency +
                "; Transaction type: " + transactionType + "; Timestamp: " + timestamp + "\n";
    }
}
