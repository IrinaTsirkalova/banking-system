package eu.deltasource.internship.bankingsystem;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionService {
    private TransactionModel transaction = new TransactionModel();

    public TransactionModel getTransaction(){
        return transaction;
    }

    public boolean createWithdrawOrDepositTransaction(String sourceIban, BankInstitutionModel sourceBank, double transferredAmount, Currency sourceCurrency, String transactionType){
        transaction.setId(UUID.randomUUID().toString());
        transaction.setSourceIban(sourceIban);
        transaction.setSourceBank(sourceBank);
        transaction.setTransferredAmount(transferredAmount);
        transaction.setSourceCurrency(sourceCurrency);
        transaction.setTransactionType(transactionType);
        transaction.setTimestamp(LocalDateTime.now());
        return false;
    }

    public boolean createTransferTransaction(String sourceIban, String targetIban, BankInstitutionModel sourceBank,BankInstitutionModel targetBank, double transferredAmount, Currency sourceCurrency, Currency targetCurrency, double exchangeRate, String transactionType){
        transaction.setId(UUID.randomUUID().toString());
        transaction.setSourceIban(sourceIban);
        transaction.setTargetIban(targetIban);
        transaction.setSourceBank(sourceBank);
        transaction.setTargetBank(targetBank);
        transaction.setTransferredAmount(transferredAmount);
        transaction.setSourceCurrency(sourceCurrency);
        transaction.setTargetCurrency(targetCurrency);
        transaction.setExchangeRate(exchangeRate);
        transaction.setTransactionType(transactionType);
        transaction.setTimestamp(LocalDateTime.now());
        return false;
    }

    public String printTransaction(){
        return transaction.toString();
    }
}
