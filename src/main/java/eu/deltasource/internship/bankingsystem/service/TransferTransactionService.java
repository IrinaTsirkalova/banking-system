package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.enums.*;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.TransferTransactionFactory;
import eu.deltasource.internship.bankingsystem.model.BankAccount;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Transaction;
import eu.deltasource.internship.bankingsystem.repository.BankAccountRepository;
import eu.deltasource.internship.bankingsystem.repository.BankInstitutionRepository;
import eu.deltasource.internship.bankingsystem.repository.TransactionRepository;

import java.util.Map;

public class TransferTransactionService {

    private void createTransferTransaction(String id, String sourceIban,
                                           String sourceBankName,
                                           double transferredAmount, double exchangeRate, String targetIban, String targetBankName,
                                           double additionalFee) {
        BankAccount sourceAccount = BankAccountRepository.bankAccountRepository.getBankAccountByIban(sourceIban);
        BankAccount targetAccount = BankAccountRepository.bankAccountRepository.getBankAccountByIban(targetIban);
        TransferTransactionFactory transferTransactionFactory = new TransferTransactionFactory();
        Transaction transferTransaction = transferTransactionFactory.createTransferTransaction(id, TransactionType.TRANSFER, sourceIban,
                sourceBankName, sourceAccount.getCurrency(), transferredAmount, exchangeRate, targetIban, targetBankName,
                additionalFee, targetAccount.getCurrency());
        sourceAccount.addToTransactionHistory(transferTransaction);
        targetAccount.addToTransactionHistory(transferTransaction);
        TransactionRepository.transactionRepository.addTransaction(sourceBankName, transferTransaction);
        TransactionRepository.transactionRepository.addTransaction(targetBankName, transferTransaction);
    }

    public void removeTransaction(String id) {
        TransactionRepository.transactionRepository.removeTransaction(id);
    }

    public Map<ExchangeRatePair, Double> getBankExchangeRatePairList(String bankName) {
        BankInstitution sourceBank = BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName);
        return sourceBank.getExchangeRatePairList();
    }

    public Map<FeeType, Double> getBankFeeList(String bankName) {
        BankInstitution sourceBank = BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName(bankName);
        return sourceBank.getFeeList();
    }

    public double getExchangeRateForAccount(String bankName, String sourceIban, String targetIban) {
        BankAccount sourceAccount = BankAccountRepository.bankAccountRepository.getBankAccountByIban(sourceIban);
        BankAccount targetAccount = BankAccountRepository.bankAccountRepository.getBankAccountByIban(targetIban);
        ExchangeRatePair exchangeRateName = ExchangeRatePair.valueOf(sourceAccount.getCurrency().name() + "_" + targetAccount.getCurrency().name());
        if (getBankExchangeRatePairList(bankName).containsKey(exchangeRateName)) {
            return getBankExchangeRatePairList(bankName).get(exchangeRateName);
        }
        return 0.0;
    }

    public double calculateExchangeValue(String bankName, String sourceIban, String targetIban, double transferredAmount) {
        double exchangeRate = getExchangeRateForAccount(bankName, sourceIban, targetIban);
        if (exchangeRate == 0.0) {
            return 0;
        }
        return transferredAmount * exchangeRate;
    }

    public double calculateAdditionalFees(String sourceBankName, String targetBankName, BankAccount sourceAccount, BankAccount targetAccount) {
        double additionalFee = getBankFeeList(sourceBankName).get(FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS);
        if (!sourceBankName.equals(targetBankName)) {
            additionalFee += getBankFeeList(sourceBankName).get(FeeType.BETWEEN_TWO_BANKS);
        }
        return additionalFee;
    }

    public void validateTransferAmount(double availableAmount, double amountReduced) {
        if (amountReduced > availableAmount) {
            throw new InvalidValueInputException("The amount is too high!");
        }
    }

    public void validateAccountsType(AccountType sourceAccountType, AccountType targetAccountType) {
        if (!sourceAccountType.equals(AccountType.CURRENT_ACCOUNT) || !targetAccountType.equals(AccountType.CURRENT_ACCOUNT)) {
            throw new InvalidValueInputException("Both of the account have to be current");
        }
    }

    public double getExchangeRateValue(Currency sourceAccountCurrency, Currency targetAccountCurrency, String sourceBankName, String sourceIban, String targetIban, double amount) {
        if (sourceAccountCurrency.equals(targetAccountCurrency)) {
            return amount;
        }
        return calculateExchangeValue(sourceBankName, sourceIban, targetIban, amount);
    }

    public void transfer(String id, String sourceBankName, String targetBankName, String sourceIban, String targetIban, double amount) {
        BankAccount sourceAccount = BankAccountRepository.bankAccountRepository.getBankAccountByIban(sourceIban);
        BankAccount targetAccount = BankAccountRepository.bankAccountRepository.getBankAccountByIban(targetIban);
        double exchangeValue = getExchangeRateValue(sourceAccount.getCurrency(), targetAccount.getCurrency(), sourceBankName, sourceIban, targetIban, amount);
        double additionalFees = calculateAdditionalFees(sourceBankName, targetBankName, sourceAccount, targetAccount);
        double amountReduced = exchangeValue + additionalFees;
        validateTransferAmount(sourceAccount.getAvailableAmount(), amountReduced);
        validateAccountsType(sourceAccount.getType(), targetAccount.getType());
        createTransferTransaction(id, sourceIban, sourceBankName, amount, exchangeValue,
                targetIban, targetBankName, additionalFees);
        sourceAccount.setAvailableAmount(sourceAccount.getAvailableAmount() - amountReduced);
        targetAccount.setAvailableAmount(targetAccount.getAvailableAmount() + amount);
    }

}
