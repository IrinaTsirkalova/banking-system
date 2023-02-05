package transaction;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.enums.ExchangeRatePair;
import eu.deltasource.internship.bankingsystem.enums.FeeType;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.model.Transaction;
import eu.deltasource.internship.bankingsystem.repository.TransactionRepository;
import eu.deltasource.internship.bankingsystem.service.*;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests:
 * - if transfer transaction is added to source account transaction history
 * - if transfer transaction is added to target account transaction history
 * - if transfer transaction is added to source bank institution transaction history
 * - if transfer transaction is added to target bank institution transaction history
 */
public class AddTransferTransactionToHistoryTest {

    @Test
    public void transferTransaction_ShouldBe_InSourceAccountHistory() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer sourceCustomer = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        Customer targetCustomer = customerFactory.createNewCustomer("Tom", "Smith", 1, 1, 1998);
        BankInstitution sourceBank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitution targetBank = bankInstitutionFactory.createBankInstitution("RFB", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(sourceCustomer);
        customerService.addNewCustomer(targetCustomer);
        bankInstitutionService.addNewBankInstitution(sourceBank);
        bankInstitutionService.addNewBankInstitution(targetBank);
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 1.96);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        transferTransactionService.transfer("2", "DSK", "RFB", "111", "123", 10);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByIban("111");
        for (Transaction transaction : transactions) {
            assertEquals("2", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
        transferTransactionService.removeTransaction("2");
    }

    @Test
    public void transferTransaction_ShouldBe_InTargetAccountHistory() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer sourceCustomer = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        Customer targetCustomer = customerFactory.createNewCustomer("Tom", "Smith", 1, 1, 1998);
        BankInstitution sourceBank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitution targetBank = bankInstitutionFactory.createBankInstitution("RFB", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(sourceCustomer);
        customerService.addNewCustomer(targetCustomer);
        bankInstitutionService.addNewBankInstitution(sourceBank);
        bankInstitutionService.addNewBankInstitution(targetBank);
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 1.96);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        transferTransactionService.transfer("2", "DSK", "RFB", "111", "123", 10);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByIban("123");
        for (Transaction transaction : transactions) {
            assertEquals("2", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
        transferTransactionService.removeTransaction("2");
    }

    @Test
    public void transferTransaction_ShouldBe_InSourceBankHistory() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer sourceCustomer = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        Customer targetCustomer = customerFactory.createNewCustomer("Tom", "Smith", 1, 1, 1998);
        BankInstitution sourceBank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitution targetBank = bankInstitutionFactory.createBankInstitution("RFB", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(sourceCustomer);
        customerService.addNewCustomer(targetCustomer);
        bankInstitutionService.addNewBankInstitution(sourceBank);
        bankInstitutionService.addNewBankInstitution(targetBank);
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 1.96);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        transferTransactionService.transfer("2", "DSK", "RFB", "111", "123", 10);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByBankName("DSK");
        for (Transaction transaction : transactions) {
            assertEquals("2", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
        transferTransactionService.removeTransaction("2");
    }

    @Test
    public void transferTransaction_ShouldBe_InTargetBankHistory() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        Customer sourceCustomer = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        Customer targetCustomer = customerFactory.createNewCustomer("Tom", "Smith", 1, 1, 1998);
        BankInstitution sourceBank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitution targetBank = bankInstitutionFactory.createBankInstitution("RFB", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        CustomerService customerService = new CustomerService();
        BankAccountService bankAccountService = new BankAccountService();
        customerService.addNewCustomer(sourceCustomer);
        customerService.addNewCustomer(targetCustomer);
        bankInstitutionService.addNewBankInstitution(sourceBank);
        bankInstitutionService.addNewBankInstitution(targetBank);
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 1.96);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        transferTransactionService.transfer("2", "DSK", "RFB", "111", "123", 10);

        //Then
        List<Transaction> transactions = TransactionRepository.transactionRepository.getTransactionsByBankName("RFB");
        for (Transaction transaction : transactions) {
            assertEquals("2", transaction.getId());
        }
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
        transferTransactionService.removeTransaction("2");
    }
}
