package eu.deltasource.internship.bankingsystem.repository;

import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.model.*;

import java.time.LocalDateTime;
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

    public List<Transaction> getTransactionsByBankName(String bankName) {
        Map<Transaction, String> transactionsForBankMap = transactions.entrySet().stream().filter(m -> m.getValue().equals(bankName)).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return new ArrayList<>(transactionsForBankMap.keySet());
    }

    public List<Transaction> getTransactionsByIban(String iban) {
        Map<Transaction, String> transactionsForIban = transactions.entrySet().stream().filter(m -> m.getKey().getSourceIban().equals(iban)).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return new ArrayList<>(transactionsForIban.keySet());
    }

    public List<Transaction> getTransactionsByIbanAndTimeRange(String iban, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        doesAccountHaveTransactions(iban);
        List<Transaction> transactionsForAccount = getTransactionsByIban(iban);
        return transactionsForAccount.stream().filter(t -> (fromDateTime.isEqual(t.getTimestamp()) || fromDateTime.isBefore(t.getTimestamp()))
                && (toDateTime.isEqual(t.getTimestamp()) || toDateTime.isAfter(t.getTimestamp()))).toList();

    }

}
