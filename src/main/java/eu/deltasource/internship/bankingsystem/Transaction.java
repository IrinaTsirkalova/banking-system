package eu.deltasource.internship.bankingsystem;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private String id;
    private String sourceIban;
    private String targetIban;
    private BankInstitution sourceBank;
    private BankInstitution targetBank;
    private double transferredAmount;
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private double exchangeRate;
    private LocalDateTime timestamp;

    public Transaction(String sourceIban, String targetIban, BankInstitution sourceBank, BankInstitution targetBank, double transferredAmount, Currency sourceCurrency, Currency targetCurrency, double exchangeRate) {
        setId();
        setSourceIban(sourceIban);
        setTargetIban(targetIban);
        setSourceBank(sourceBank);
        setTargetBank(targetBank);
        setTransferredAmount(transferredAmount);
        setSourceCurrency(sourceCurrency);
        setTargetCurrency(targetCurrency);
        setExchangeRate(exchangeRate);
        setTimestamp();
    }

    public void calculateExchangeRate(Currency source, Currency target){


    }
    public String getId() {
        return id;
    }

    public String getSourceIban() {
        return sourceIban;
    }

    public String getTargetIban() {
        return targetIban;
    }

    public BankInstitution getSourceBank() {
        return sourceBank;
    }

    public BankInstitution getTargetBank() {
        return targetBank;
    }

    public double getTransferredAmount() {
        return transferredAmount;
    }

    public Currency getSourceCurrency() {
        return sourceCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setId() {
        id = UUID.randomUUID().toString();
    }

    public void setSourceIban(String sourceIban) {
        this.sourceIban = sourceIban;
    }

    public void setTargetIban(String targetIban) {
        this.targetIban = targetIban;
    }

    public void setSourceBank(BankInstitution sourceBank) {
        this.sourceBank = sourceBank;
    }

    public void setTargetBank(BankInstitution targetBank) {
        this.targetBank = targetBank;
    }

    public void setTransferredAmount(double transferredAmount) {
        this.transferredAmount = transferredAmount;
    }

    public void setSourceCurrency(Currency sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setTimestamp() {
        timestamp = LocalDateTime.now();
    }

    public String toString(){
        return  "Transaction information: -----------------\n" +
                "Transaction id: " + getId() +
                ";\nSource IBAN: " + getSourceIban() + "; Target IBAN: " + getTargetIban() +
                ";\nSource Bank: " + getSourceBank() + "; Target Bank: " + getTargetBank() +
                ";\nTransferred amount: " + getTransferredAmount() +
                ";\nSource currency: " + getSourceCurrency() + "; Target currency: " + getTargetCurrency()+
                ";\nExchange rate: " + getExchangeRate() + "; Timestamp: " + getTimestamp();
    }
}
