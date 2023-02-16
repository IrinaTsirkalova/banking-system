package eu.deltasource.internship.bankingsystem.factory;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.model.BankAccount;
import eu.deltasource.internship.bankingsystem.model.Customer;

import java.util.UUID;

public class BankAccountFactory {

    public BankAccount createBankAccount(String bankInstitutionName, Customer customer,
                                         String iban, Currency currency, double availableAmount, AccountType type) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBankInstitution(bankInstitutionName);
        bankAccount.setCustomer(customer);
        bankAccount.setIban(iban);
        bankAccount.setCurrency(currency);
        bankAccount.setAvailableAmount(availableAmount);
        bankAccount.setType(type);
        return bankAccount;
    }
}
