package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.factory.BankAccountFactory;
import eu.deltasource.internship.bankingsystem.model.BankAccount;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.repository.BankAccountRepository;

/**
 * Used when the user wants to add a new bank account for a specific bank
 * and when the user wants to print bank account information
 */
public class BankAccountService {

    public void addNewBankAccount(String bankName, Customer customer, String iban,
                                  Currency currency, double amount, AccountType type) {
        BankAccountFactory bankAccountFactory = new BankAccountFactory();
        BankAccount account = bankAccountFactory.createBankAccount(bankName, customer, iban, currency, amount, type);
        BankAccountRepository.bankAccountRepository.addBankAccountToBank(bankName, account);
    }

    public String printBankAccount(String iban) {
        return BankAccountRepository.bankAccountRepository.getBankAccountByIban(iban).toString();
    }
}
