package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.enums.Currency;

public class TransferTransaction extends Transaction {
    private String targetIban;
    private String targetBankName;
    private double additionalFee;
    private double exchangeRate;
    private Currency targetCurrency;

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public void setAdditionalFee(double additionalFee) {
        this.additionalFee = additionalFee;
    }

    public void setTargetIban(String targetIban) {
        this.targetIban = targetIban;
    }

    public void setTargetBankName(String targetBankName) {
        this.targetBankName = targetBankName;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public String toString() {
        return "Transaction information: -----------------\n" +
                "Transaction id: " + getId() +
                "; Source bank name: " + getSourceBankName() + "; Source IBAN: " + getSourceIban() + "; Source currency: " + getSourceCurrency() +
                "; Transferred amount: " + getTransferredAmount() +
                "; Target bank name: " + targetBankName + "; Target iban: " + targetIban + "; Target currency: " + targetCurrency +
                "; Additional fee: " + additionalFee + "; Exchange rate: " + exchangeRate +
                "; Transaction type: " + getTransactionType() + "; Timestamp: " + getTimestamp() + "\n";
    }
}
