package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.enums.*;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.TransactionFactory;
import eu.deltasource.internship.bankingsystem.factory.TransferTransactionFactory;
import eu.deltasource.internship.bankingsystem.model.BankAccount;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Transaction;
import eu.deltasource.internship.bankingsystem.repository.BankAccountRepository;
import eu.deltasource.internship.bankingsystem.repository.BankInstitutionRepository;
import eu.deltasource.internship.bankingsystem.repository.TransactionRepository;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;

public class TransactionService {

    public String printTransactionForAccount(String iban) {
        return TransactionRepository.transactionRepository.getTransactionsByIban(iban).toString();
    }

    public String printBankTransactionListInfo(String bankName) {
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByBankName(bankName);
        return BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).getTransactionListInfo(transactions);
    }

    private void createSimpleTransaction(TransactionType type, String sourceIban, String sourceBankName, Currency sourceCurrency,
                                         double amount) {
        BankAccount account = BankAccountRepository.bankAccountRepository.getBankAccountByIban(sourceIban);
        TransactionFactory transactionFactory = new TransactionFactory();
        Transaction simpleTransaction = transactionFactory.createSimpleTransaction(type, sourceIban, sourceBankName,
                sourceCurrency, amount);
        account.addToTransactionHistory(simpleTransaction);
        TransactionRepository.transactionRepository.addTransaction(sourceBankName, simpleTransaction);
    }

    public void withdraw(String iban, double amount) {
        BankAccount account = BankAccountRepository.bankAccountRepository.getBankAccountByIban(iban);
        account.reduceAmount(amount);
        createSimpleTransaction(TransactionType.WITHDRAW, iban, account.getBankInstitutionName(), account.getCurrency(), amount);
    }

    public void deposit(String iban, double amount) {
        BankAccount account = BankAccountRepository.bankAccountRepository.getBankAccountByIban(iban);
        account.increaseAmount(amount);
        createSimpleTransaction(TransactionType.DEPOSIT, iban, account.getBankInstitutionName(), account.getCurrency(), amount);
    }

    public String printBankStatementForAPeriod(String iban, int fromDay, int fromMonth, int fromYear, int fromHour, int fromMinute,
                                               int toDay, int toMonth, int toYear, int toHour, int toMinute) {
        LocalDateTime fromDate = LocalDateTime.of(fromYear, fromMonth, fromDay, fromHour, fromMinute);
        LocalDateTime toDate = LocalDateTime.of(toYear, toMonth, toDay, toHour, toMinute);
        return TransactionRepository.transactionRepository.getTransactionsByIbanAndTimeRange(iban, fromDate, toDate).toString();
    }


}
