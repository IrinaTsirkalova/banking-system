package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.enums.ExchangeRatePair;
import eu.deltasource.internship.bankingsystem.enums.FeeType;
import eu.deltasource.internship.bankingsystem.model.BankAccount;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.repository.BankAccountRepository;
import eu.deltasource.internship.bankingsystem.repository.BankInstitutionRepository;

import java.util.List;

/**
 * Used when:
 * - the user wants to add new bank institution in the repository
 * - the user wants to add a fee to specific bank
 * - the user wants to add an exchange rate to a specific bank
 * - the user wants to print information for specific bank
 * - the user wants to print information for the number of users for a specific bank
 */
public class BankInstitutionService {

    public void addNewBankInstitution(BankInstitution bank) {
        BankInstitutionRepository.bankInstitutionsRepository.addBankInstitution(bank);
    }

    public void removeBankInstitution(String name){
        BankInstitutionRepository.bankInstitutionsRepository.removeBankInstitution(name);
    }

    public void addNewFee(String bankName, FeeType type, Double value) {
        BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).addFee(type, value);
    }

    public void removeFee(String bankName, FeeType type) {
        BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).removeFee(type);
    }

    public void updateFee(String bankName, FeeType type, Double value) {
        BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).updateFee(type,value);
    }

    public void addNewExchangeRate(String bankName, ExchangeRatePair exchangeRatePair, Double value) {
        BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).addExchangeRatePair(exchangeRatePair, value);
    }

    public void removeExchangeRate(String bankName, ExchangeRatePair pair) {
        BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).removeExchangeRate(pair);
    }

    public void updateExchangeRate(String bankName, ExchangeRatePair pair, Double value) {
        BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).updateExchangeRate(pair,value);
    }

    public String printBankInfo(String bankName) {
        return BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).getBankInstitutionInfo();
    }

    public String printBankCustomersNumberInfo(String bankName) {
        List<Customer> bankCustomers = BankAccountRepository.bankAccountRepository.getBankCustomersByBankName(bankName);
        return BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).getBankInstitutionCustomerNumberInfo(bankCustomers);
    }

    public String printBankAccountInfo(String bankName) {
        List<BankAccount> bankAccounts = BankAccountRepository.bankAccountRepository.getBankAccountsByBankName(bankName);
        return BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).getBankAccountListInfo(bankAccounts);
    }

    public String printBankFeeInfo(String bankName) {
        return BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).getFeeListInfo();
    }

    public String printBankExchangeRateInfo(String bankName) {
        return BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName).getExchangeRateListInfo();
    }
}