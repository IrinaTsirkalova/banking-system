package eu.deltasource.internship.bankingsystem;

import java.time.LocalDateTime;

public class TransactionModel {
    private String id;
    private String sourceIban;
    private String targetIban;
    private BankInstitutionModel sourceBank;
    private BankInstitutionModel targetBank;
    private double transferredAmount;
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private double exchangeRate;
    private LocalDateTime timestamp;
    private String transactionType;

    public void setId(String id) {
        this.id = id;
    }

    public void setSourceIban(String sourceIban) {
        this.sourceIban = sourceIban;
    }

    public void setTargetIban(String targetIban) {
        this.targetIban = targetIban;
    }

    public void setSourceBank(BankInstitutionModel sourceBank) {
        this.sourceBank = sourceBank;
    }

    public void setTargetBank(BankInstitutionModel targetBank) {
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

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getBankInfo(){
        String bankInfo;
        if(!(sourceBank == null) && !(targetBank == null)){
            bankInfo = "; Source Bank: " + sourceBank.getName() + "; Target Bank: " + targetBank.getName();
        }else{
            bankInfo = "; Source Bank: " + sourceBank.getName();
        }
        return bankInfo;
    }
    public String toString(){
        return  "Transaction information: -----------------\n" +
                "Transaction id: " + id +
                "; Source IBAN: " + sourceIban + "; Target IBAN: " + targetIban + getBankInfo() +
                "; Transferred amount: " + transferredAmount +
                "; Source currency: " + sourceCurrency + "; Target currency: " + targetCurrency +
                "; Exchange rate: " + exchangeRate + "; Transaction type: " + transactionType + "; Timestamp: " + timestamp + "\n";
    }
}
