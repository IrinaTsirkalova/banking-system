package eu.deltasource.internship.bankingsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccount {

    private String id;
    private BankInstitution bankInstitution;
    private Customer customer;
    private String iban;
    private Currency currency;
    private double availableAmount;
    private AccountType type;
    private List<Transaction> transactionList = new ArrayList<>();

    public BankAccount(BankInstitution bankInstitution, Customer customer, String iban, Currency currency, double availableAmount, AccountType type) {
       setId();
       setBankInstitution(bankInstitution);
       setCustomer(customer);
       setIban(iban);
       setCurrency(currency);
       setAvailableAmount(availableAmount);
       setType(type);
       setTransactionList(transactionList);
       bankInstitution.addNewCustomer(customer);
    }

    //Create methods that allow withdrawing money from and depositing to the
    //account. Those methods should be available for all accounts.
    public boolean withdraw(BankAccount sourceAccount, double amount){
        double sourceAccountAvailableAmount = sourceAccount.getAvailableAmount();
        if(sourceAccountAvailableAmount > amount){
            sourceAccount.setAvailableAmount(sourceAccountAvailableAmount-amount);
            return true;
        }
        return false;
    }
    public boolean deposit(BankAccount sourceAccount, double amount){
        double sourceAccountAvailableAmount = sourceAccount.getAvailableAmount();
        double ownerAvailableAmount = getAvailableAmount();
        if(ownerAvailableAmount > amount){
            sourceAccount.setAvailableAmount(sourceAccountAvailableAmount + amount);
            setAvailableAmount(ownerAvailableAmount - amount);
            return true;
        }
        return false;
    }

    public boolean trasfer(BankAccount sourceAccount, BankAccount targetAccount, double amount){
        if(sourceAccount.type == AccountType.CURRENT_ACCOUNT && targetAccount.type == AccountType.CURRENT_ACCOUNT){
            double sourceAccountAvailableAmount = sourceAccount.getAvailableAmount();
            double targetAccountAvailableAmount = targetAccount.getAvailableAmount();
            if(sourceAccountAvailableAmount > amount){
                sourceAccount.setAvailableAmount(sourceAccountAvailableAmount-amount);
                targetAccount.setAvailableAmount(targetAccountAvailableAmount + amount);
                return true;
            }
        }

        return false;
    }
    //Create methods for transferring money between accounts. Allow only transfers
    //between two current accounts. Upon each transfer apply standard fees as
    //specified in the bank’s price list. Make sure to differentiate between transfers
    //within the same bank and transfers between two banks. When the two accounts
    //are in different currencies apply the exchange rates of the source bank.

    //NOTE: MAKE ENUM TAXES?
    public String getId() {
        return id;
    }

    public BankInstitution getBankInstitution() {
        return bankInstitution;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getIban() {
        return iban;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAvailableAmount() {
        return availableAmount;
    }

    public AccountType getType() {
        return type;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setId() {
        id = UUID.randomUUID().toString();
    }

    public void setBankInstitution(BankInstitution bankInstitution) {
        this.bankInstitution = bankInstitution;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setIban(String iban) {
        if(iban.length() == 22){
            this.iban = iban;
        }
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setAvailableAmount(double availableAmount) {
        this.availableAmount = availableAmount;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public String getTransactionsInfo(List<Transaction> transactions){
        String transactionInfo = "";
        if(transactions.size() == 0){
            transactionInfo = "No transactions yet!";
        }else{
            for(Transaction transaction : transactions ){
                transactionInfo += transaction;
            }
        }
        return transactionInfo;
    }
    public String toString(){
        return "Bank account information:------------\n" +
                "Bank account id: " + getId() + ";\n"
                + getCustomer() +
                "IBAN: " + getIban() + "; Currency: " + getCurrency() + ";\nAvailable amount: " + getAvailableAmount() +
                "; Account type: " + getType() + "\nTransactions: " + getTransactionsInfo(getTransactionList());
    }
}
