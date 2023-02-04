package transaction;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.model.Transaction;
import eu.deltasource.internship.bankingsystem.repository.TransactionRepository;
import eu.deltasource.internship.bankingsystem.service.BankAccountService;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import eu.deltasource.internship.bankingsystem.service.TransactionService;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTransactionToHistoryTest {
    @Test
    public void should_addDepositTransaction_ToBankAccountTransactions(){
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        BankInstitution bankInstitution = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(lily);
        bankInstitutionService.addNewBankInstitution(bankInstitution);
        bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 10, AccountType.CURRENT_ACCOUNT);

        //When
        TransactionService transactionService = new TransactionService();
        transactionService.deposit("123","111", 5);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByIban("111");
        for(Transaction transaction : transactions){
            assertEquals("123", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
        transactionService.removeTransaction("123");
    }

    @Test
    public void should_addWithdrawTransaction_ToBankAccountTransactions(){
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        BankInstitution bankInstitution = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(lily);
        bankInstitutionService.addNewBankInstitution(bankInstitution);
        bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 10, AccountType.CURRENT_ACCOUNT);

        //When
        TransactionService transactionService = new TransactionService();
        transactionService.withdraw("123","111", 5);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByIban("111");
        for(Transaction transaction : transactions){
            assertEquals("123", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
        transactionService.removeTransaction("123");
    }


    @Test
    public void should_addDepositTransaction_ToBankInstitutionsTransactions(){
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        BankInstitution bankInstitution = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(lily);
        bankInstitutionService.addNewBankInstitution(bankInstitution);
        bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 10, AccountType.CURRENT_ACCOUNT);

        //When
        TransactionService transactionService = new TransactionService();
        transactionService.deposit("123","111", 5);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByBankName("DSK");
        for(Transaction transaction : transactions){
            assertEquals("123", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
        transactionService.removeTransaction("123");
    }

    @Test
    public void should_addWithdrawTransaction_ToBankInstitutionsTransactions(){
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        BankInstitution bankInstitution = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(lily);
        bankInstitutionService.addNewBankInstitution(bankInstitution);
        bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 10, AccountType.CURRENT_ACCOUNT);

        //When
        TransactionService transactionService = new TransactionService();
        transactionService.withdraw("123","111", 5);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByBankName("DSK");
        for(Transaction transaction : transactions){
            assertEquals("123", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
        transactionService.removeTransaction("123");
    }
}
