package eu.deltasource.internship.bankingsystem;
import java.util.Map;
import java.util.UUID;

public class BankAccountService {
    private BankAccountModel bankAccount = new BankAccountModel();

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

    public boolean withdraw( double amount){
        double sourceAccountAvailableAmount = bankAccount.getAvailableAmount();
        if(sourceAccountAvailableAmount > amount){
            bankAccount.setAvailableAmount(sourceAccountAvailableAmount-amount);
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

    public boolean transfer(BankAccountModel sourceAccount, BankAccountModel targetAccount, double amount){
        if(bankAccount.getType() == AccountType.CURRENT_ACCOUNT && targetAccount.getType() == AccountType.CURRENT_ACCOUNT){
            double sourceAccountAvailableAmount = bankAccount.getAvailableAmount();
            double targetAccountAvailableAmount = targetAccount.getAvailableAmount();
            double additionalFee =0.0;
            if(sourceAccountAvailableAmount > amount){
                if(!bankAccount.getBankInstitution().equals(targetAccount.getBankInstitution())){
                    additionalFee = sourceAccount.getBankInstitution().getFeeList().get(Fee.BETWEEN_TWO_BANKS);
                }
                double exchangeRate = getExchangeRate(sourceAccount,targetAccount);
                double exchangeValue = calculateExchangeValue(amount, exchangeRate);
                TransactionService transaction = new TransactionService();
                transaction.createTransferTransaction(bankAccount.getIban(), targetAccount.getIban(), bankAccount.getBankInstitution(), targetAccount.getBankInstitution(), amount, bankAccount.getCurrency(), targetAccount.getCurrency(),exchangeRate,"Transfer");
                bankAccount.setAvailableAmount(sourceAccountAvailableAmount - amount - additionalFee);
                targetAccount.setAvailableAmount(targetAccountAvailableAmount + exchangeValue);
                bankAccount.getTransactionList().add(transaction.getTransaction());
                return true;
            }
        }
        return false;
    }

    public String printBankAccount(){
       return bankAccount.toString();
    }
}
