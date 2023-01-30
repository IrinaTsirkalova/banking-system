package eu.deltasource.internship.bankingsystem;

import java.util.ArrayList;
import java.util.List;

public class BankAccountModel {
    private String id;
    private BankInstitutionModel bankInstitution;
    private CustomerModel customer;
    private String iban;
    private Currency currency;
    private double availableAmount;
    private AccountType type;
    private List<TransactionModel> transactionList = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public BankInstitutionModel getBankInstitution() {
        return bankInstitution;
    }

    public void setBankInstitution(BankInstitutionModel bankInstitution) {
        this.bankInstitution = bankInstitution;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public List<TransactionModel> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionModel> transactionList) {
        this.transactionList = transactionList;
    }

    public String getTransactionsInfo(List<TransactionModel> transactions){
        String transactionInfo = "";
        if(transactions.size() == 0){
            transactionInfo = "No transactions yet!";
        }else{
            for(TransactionModel transaction : transactions ){
                transactionInfo += transaction;
            }
        }
        return transactionInfo;
    }

    public String toString(){
        return "Bank account information:------------\n" +
                "Bank account id: " + id + ";" + "; IBAN: " + iban +
                "; Currency: " + currency + "; Available amount: " + availableAmount +
                "; Account type: " + type +
                "\nTransactions: " + getTransactionsInfo(transactionList);
    }
}