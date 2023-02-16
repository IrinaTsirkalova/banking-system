package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.enums.*;
import eu.deltasource.internship.bankingsystem.factory.TransactionFactory;
import eu.deltasource.internship.bankingsystem.model.BankAccount;
import eu.deltasource.internship.bankingsystem.model.Transaction;
import eu.deltasource.internship.bankingsystem.repository.BankAccountRepository;
import eu.deltasource.internship.bankingsystem.repository.BankInstitutionRepository;
import eu.deltasource.internship.bankingsystem.repository.TransactionRepository;

import java.time.LocalDateTime;

import java.util.List;

/**
 * Used when:
 * - the user wants to print transactions information for specific account
 * - the user wants to print transactions information for specific bank
 * - the user wants to print bank statement for account transactions for a specified time range
 * - the user wants to make a withdrawal transaction
 * - the user wants to make a deposit transaction
 */
public class TransactionService {

    public String printTransactionForAccount(String iban) {
        return BankAccountRepository.bankAccountRepository.getBankAccountByIban(iban).getTransactionList().toString();
    }

    public String printBankTransactionListInfo(String bankName) {
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByBankName(bankName);
        return BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).getTransactionListInfo(transactions);
    }

    private void createSimpleTransaction(String id, TransactionType type, String sourceIban, String sourceBankName, Currency sourceCurrency,
                                         double amount) {
        BankAccount account = BankAccountRepository.bankAccountRepository.getBankAccountByIban(sourceIban);
        TransactionFactory transactionFactory = new TransactionFactory();
        Transaction simpleTransaction = transactionFactory.createSimpleTransaction(id, type, sourceIban, sourceBankName,
                sourceCurrency, amount);
        account.addToTransactionHistory(simpleTransaction);
        TransactionRepository.transactionRepository.addTransaction(sourceBankName, simpleTransaction);
    }

    public void removeTransaction(String id) {
        TransactionRepository.transactionRepository.removeTransaction(id);
    }

    public void withdraw(String id, String iban, double amount) {
        BankAccount account = BankAccountRepository.bankAccountRepository.getBankAccountByIban(iban);
        account.reduceAmount(amount);
        createSimpleTransaction(id, TransactionType.WITHDRAW, iban, account.getBankInstitutionName(), account.getCurrency(), amount);
    }

    public void deposit(String id, String iban, double amount) {
        BankAccount account = BankAccountRepository.bankAccountRepository.getBankAccountByIban(iban);
        account.increaseAmount(amount);
        createSimpleTransaction(id, TransactionType.DEPOSIT, iban, account.getBankInstitutionName(), account.getCurrency(), amount);
    }

    public String printBankStatementForAPeriod(String iban, int fromDay, int fromMonth, int fromYear, int fromHour, int fromMinute,
                                               int toDay, int toMonth, int toYear, int toHour, int toMinute) {
        LocalDateTime fromDate = LocalDateTime.of(fromYear, fromMonth, fromDay, fromHour, fromMinute);
        LocalDateTime toDate = LocalDateTime.of(toYear, toMonth, toDay, toHour, toMinute);
        List<Transaction> transactionsForAccount = BankAccountRepository.bankAccountRepository.getBankAccountByIban(iban).getTransactionList();
        return transactionsForAccount.stream().filter(t -> (fromDate.isEqual(t.getTimestamp()) || fromDate.isBefore(t.getTimestamp()))
                && (toDate.isEqual(t.getTimestamp()) || toDate.isAfter(t.getTimestamp()))).toList().toString();
    }
}
