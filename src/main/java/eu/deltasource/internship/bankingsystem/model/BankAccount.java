package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.Validation;
import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;

import java.util.ArrayList;
import java.util.Collections;
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

    public void validateAmount(double amount) {
        if (amount <= 0) {
            throw new InvalidValueInputException("The amount should be above 0");
        }
    }

    public void validateIban(String iban) {
        if (iban.isBlank()) {
            throw new InvalidValueInputException("Please enter an iban");
        }
    }

    public void increaseAmount(double amount) {
        validateAmount(amount);
        setAvailableAmount(availableAmount + amount);
    }

    public void reduceAmount(double amount) {
        validateAmount(amount);
        if (amount >= availableAmount) {
            throw new InvalidValueInputException("The amount you want to withdraw is too much!");
        }
        setAvailableAmount(availableAmount - amount);
    }

    public void addToTransactionHistory(Transaction transaction) {
        this.transactionList.add(transaction);
    }

    public String toString() {
        return "Bank account information:------------\n" +
                "Bank account id: " + id + ";" + "; IBAN: " + iban +
                "; Currency: " + currency + "; Available amount: " + availableAmount +
                "; Account type: " + type;
    }

    public List<Transaction> getTransactionList() {
        return Collections.unmodifiableList(transactionList);
    }

    public String getIban() {
        return iban;
    }

    public AccountType getType() {
        return type;
    }

    public Currency getCurrency() {
        return currency;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setBankInstitution(String bankInstitutionName) {
        Validation.validateForNoName(bankInstitutionName);
        this.bankInstitutionName = bankInstitutionName;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setIban(String iban) {
        validateIban(iban);
        this.iban = iban;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        if (currency == null) {
            this.currency = Currency.BGN;
        }
    }

    public void setAvailableAmount(double availableAmount) {
        validateAmount(availableAmount);
        this.availableAmount = availableAmount;
    }

    public void setType(AccountType type) {
        this.type = type;
        if (type == null) {
            this.type = AccountType.CURRENT_ACCOUNT;
        }
    }
}