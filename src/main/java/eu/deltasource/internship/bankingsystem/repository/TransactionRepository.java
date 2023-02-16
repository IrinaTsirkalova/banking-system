package eu.deltasource.internship.bankingsystem.repository;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionRepository {

    public static final TransactionRepository transactionRepository = new TransactionRepository();
    final Map<Transaction, String> transactions = new HashMap<>();

    public void addTransaction(String bankId, Transaction transaction) {
        transactions.put(transaction, bankId);
    }

    public void validateTransaction(String id) {
        if (!doesTransactionExists(id)) {
            throw new ElementDoesNotExistsException("There is no transaction with this id!");
        }
    }

    public boolean doesTransactionExists(String id) {
        return transactions.entrySet().stream().anyMatch(t -> t.getKey().getId().equals(id));
    }

    public void removeTransaction(String id) {
        validateTransaction(id);
        transactions.remove(getTransactionById(id));
    }

    public void doesAccountHaveTransactions(String iban) {
        if (getTransactionsByIban(iban).isEmpty()) {
            throw new ElementDoesNotExistsException("There are no transactions in this account!");
        }
    }

    public void doesBankHaveTransactions(String bankName) {
        if (getTransactionsByBankName(bankName).isEmpty()) {
            throw new ElementDoesNotExistsException("There are no transactions in this bank!");
        }
    }

    public Transaction getTransactionById(String id) {
        return transactions.keySet().stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    public List<Transaction> getTransactionsByBankName(String bankName) {
        Map<Transaction, String> transactionsForBankMap = transactions.entrySet().stream().filter(m -> m.getValue().equals(bankName)).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return new ArrayList<>(transactionsForBankMap.keySet());
    }

    public List<Transaction> getTransactionsByIban(String iban) {
        Map<Transaction, String> transactionsForIban = transactions.entrySet().stream().filter(m -> m.getKey().getSourceIban().equals(iban)).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return new ArrayList<>(transactionsForIban.keySet());
    }
}
