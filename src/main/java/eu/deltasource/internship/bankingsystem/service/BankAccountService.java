package eu.deltasource.internship.bankingsystem.service;
import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.bankingsystem.enums.Fee;
import eu.deltasource.internship.bankingsystem.model.BankAccountModel;
import eu.deltasource.internship.bankingsystem.model.BankInstitutionModel;
import eu.deltasource.internship.bankingsystem.model.CustomerModel;
import eu.deltasource.internship.bankingsystem.model.TransactionModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BankAccountService {

    private final BankAccountModel bankAccount = new BankAccountModel();

    public BankAccountModel getBankAccount(){
        return bankAccount;
    }

    public boolean createBankAccount(BankInstitutionModel bankInstitution, CustomerModel customer, String iban, Currency currency, double availableAmount, AccountType type){
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBankInstitution(bankInstitution);
        bankAccount.setCustomer(customer);
        bankAccount.setIban(iban);
        bankAccount.setCurrency(currency);
        bankAccount.setAvailableAmount(availableAmount);
        bankAccount.setType(type);
        bankAccount.setTransactionList(bankAccount.getTransactionList());
        return false;
    }

    public void createWithdrawOrDepositTransaction(BankAccountModel bankAccount, double amount, String transactionType){
        TransactionService transaction = new TransactionService();
        transaction.createWithdrawOrDepositTransaction(bankAccount.getIban(),bankAccount.getBankInstitution(),
                amount, bankAccount.getCurrency(), transactionType);
        bankAccount.getTransactionList().add(transaction.getTransaction());
    }

    public void createTransferTransaction(BankAccountModel sourceAccount, BankAccountModel targetAccount, double amount, double exchangeRate, double additionalFee, double exchangeValue){
        TransactionService transaction = new TransactionService();
        transaction.createTransferTransaction(sourceAccount.getIban(), targetAccount.getIban(), sourceAccount.getBankInstitution(), targetAccount.getBankInstitution(), amount, sourceAccount.getCurrency(), targetAccount.getCurrency(),exchangeRate,"Transfer");
        sourceAccount.getTransactionList().add(transaction.getTransaction());
    }

    public boolean withdraw( double amount){
        double sourceAccountAvailableAmount = bankAccount.getAvailableAmount();
        if(sourceAccountAvailableAmount > amount){
            bankAccount.setAvailableAmount(sourceAccountAvailableAmount - amount);
            createWithdrawOrDepositTransaction(bankAccount, amount,"Withdraw");
            return true;
        }
        return false;
    }

    public boolean deposit(double amount){
        double sourceAccountAvailableAmount = bankAccount.getAvailableAmount();
        bankAccount.setAvailableAmount(sourceAccountAvailableAmount + amount);
        createWithdrawOrDepositTransaction(bankAccount, amount,"Deposit");
        return true;
    }

    public double getExchangeRate(BankAccountModel sourceAccount,BankAccountModel targetAccount){
        double exchangeRate = 0;
        BankInstitutionModel sourceBank = bankAccount.getBankInstitution();
        Map<ExchangeRate, Double> exchangeRates = sourceBank.getExchangeRateList();
        ExchangeRate exchangeRateName = ExchangeRate.valueOf(sourceAccount.getCurrency().name() + "_" + targetAccount.getCurrency().name());
        if(exchangeRates.containsKey(exchangeRateName)){
            exchangeRate = exchangeRates.get(exchangeRateName);
        }
        return exchangeRate;
    }

    public double calculateExchangeValue(double transferredAmount, double exchangeRate){
        return transferredAmount * exchangeRate;
    }

    public double calculateAdditionalFees(BankAccountModel sourceAccount, BankAccountModel targetAccount){
        double additionalFee = 0.0;
        if(!sourceAccount.getBankInstitution().equals(targetAccount.getBankInstitution())){
            additionalFee += sourceAccount.getBankInstitution().getFeeList().get(Fee.BETWEEN_TWO_BANKS);
        }
        additionalFee += sourceAccount.getBankInstitution().getFeeList().get(Fee.TRANSFER_BETWEEN_TWO_ACCOUNTS);
        return additionalFee;
    }

    public boolean transfer(BankAccountModel sourceAccount, BankAccountModel targetAccount, double amount){
        if(sourceAccount.getType() == AccountType.CURRENT_ACCOUNT && targetAccount.getType() == AccountType.CURRENT_ACCOUNT && sourceAccount.getAvailableAmount() > amount){
            double additionalFee = calculateAdditionalFees(sourceAccount, targetAccount);
            double exchangeRate = 0.0;
            double exchangeValue = 0.0;
            if(!sourceAccount.getCurrency().equals(targetAccount.getCurrency())){
                exchangeRate = getExchangeRate(sourceAccount,targetAccount);
                exchangeValue = calculateExchangeValue(amount, exchangeRate);
            }
            createTransferTransaction(sourceAccount, targetAccount, amount, exchangeRate, additionalFee, exchangeValue);
            sourceAccount.setAvailableAmount(sourceAccount.getAvailableAmount() - amount - additionalFee);
            targetAccount.setAvailableAmount(targetAccount.getAvailableAmount() + exchangeValue);
            return true;

        }
        return false;
    }

    public String printBankStatementForAPeriod(int fromDay, int fromMonth, int fromYear, int toDay, int toMonth, int toYear){
        LocalDate fromDate = LocalDate.of(fromYear, fromMonth, fromDay);
        LocalDate toDate = LocalDate.of(toYear, toMonth,toDay);
        List<TransactionModel> transactionsForRange = new ArrayList<>();
        if(!(bankAccount.getTransactionList() == null)){
            for(TransactionModel transaction : bankAccount.getTransactionList()){
                LocalDate transLocalDate = transaction.getTimestamp().toLocalDate();
                if((fromDate.isEqual(transaction.getTimestamp().toLocalDate()) || fromDate.isBefore(transaction.getTimestamp().toLocalDate()))
                        && (toDate.isEqual(transaction.getTimestamp().toLocalDate()) || toDate.isAfter(transaction.getTimestamp().toLocalDate()))){
                    transactionsForRange.add(transaction);
                }
            }
        }
        return transactionsForRange.toString();

    }

    public String printBankAccount(){
       return bankAccount.toString();
    }
}
