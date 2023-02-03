package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String id;
    private String bankInstitutionName;
    private Customer customer;
    private String iban;
    private Currency currency;
    private double availableAmount;
    private AccountType type;
    private final List<Transaction> transactionList = new ArrayList<>();

    public void increaseAmount(double amount) {
        setAvailableAmount(availableAmount + amount);
    }

    public void reduceAmount(double amount) {
        if (amount >= availableAmount) {
            throw new InvalidValueInputException("The amount you want to withdraw is too much!");
        }
        setAvailableAmount(availableAmount - amount);
    }

    public String toString() {
        return "Bank account information:------------\n" +
                "Bank account id: " + id + ";" + "; IBAN: " + iban +
                "; Currency: " + currency + "; Available amount: " + availableAmount +
                "; Account type: " + type;
    }

    public AccountType getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public String getBankInstitutionName() {
        return bankInstitutionName;
    }

    public void setBankInstitution(String bankInstitutionName) {
        this.bankInstitutionName = bankInstitutionName;
    }

    public void setCustomer(Customer customer) {
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

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void addToTransactionHistory(Transaction transaction) {
        this.transactionList.add(transaction);
    }
}