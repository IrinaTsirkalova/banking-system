package eu.deltasource.internship.bankingsystem.repository;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.model.BankAccount;
import eu.deltasource.internship.bankingsystem.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankAccountRepository {
    public static final BankAccountRepository bankAccountRepository = new BankAccountRepository();
    private final Map<BankAccount, String> bankAccounts = new HashMap<>();

    public boolean doesBankAccountExist(String iban) {
        return bankAccounts.keySet().stream().anyMatch(a -> a.getIban().equals(iban));
    }

    public void addBankAccountToBank(String bankName, BankAccount account) {
        if (doesBankAccountExist(account.getIban())) {
            throw new ElementAlreadyExistsException("There is a bank account with the same iban!");
        }
        bankAccounts.put(account, bankName);
    }

    public BankAccount getBankAccountByIban(String iban) {
        if (!doesBankAccountExist(iban)) {
            throw new ElementDoesNotExistsException("There is no such bank account");
        }
        return bankAccounts.keySet().stream().filter(b -> b.getIban().equals(iban)).findFirst().get();
    }

    public List<BankAccount> getBankAccountsByBankName(String bankName) {
        Map<BankAccount, String> bankAccountsForBankMap = bankAccounts.entrySet().stream().filter(m -> m.getValue().equals(bankName)).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        return new ArrayList<>(bankAccountsForBankMap.keySet());
    }

    public List<Customer> getBankCustomersByBankName(String bankName) {
        List<BankAccount> bankAccountsByBank = getBankAccountsByBankName(bankName);
        return bankAccountsByBank.stream().map(BankAccount::getCustomer).collect(Collectors.toList());
    }
}
