package transaction;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.repository.BankAccountRepository;
import eu.deltasource.internship.bankingsystem.service.BankAccountService;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import eu.deltasource.internship.bankingsystem.service.TransactionService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if user can make a withdrawal transaction
 * - if user cannot make a withdrawal transaction without iban
 * - if user cannot make a withdrawal transaction if withdraw account is below zero
 * - if user cannot make a withdrawal transaction if account's available amount is the same or below the withdrawing amount
 */
public class WithdrawTransactionTest {

    @Test
    public void withdraw_Should_DecreaseAvailableAmount_InBankAccount() {
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
        transactionService.withdraw("1", "111", 5);

        //Then
        assertEquals(BankAccountRepository.bankAccountRepository.getBankAccountByIban("111").getAvailableAmount(), 5);
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
        transactionService.removeTransaction("1");
    }

    @Test
    public void withdraw_ShouldThrowRuntimeException_IfThereIsNoAccount_WithThisIban() {
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
        RuntimeException exception = assertThrows(ElementDoesNotExistsException.class,
                () -> transactionService.withdraw("11", "11", 5));

        //Then
        assertTrue(exception.getMessage().contentEquals("There is no such bank account"));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void withdraw_ShouldThrowRuntimeException_IfAmountIsBigger_ThanAccountAvailableAmount() {
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
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> transactionService.withdraw("11", "111", 15));

        //Then
        assertTrue(exception.getMessage().contentEquals("The amount you want to withdraw is too much!"));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void withdraw_ShouldThrowRuntimeException_IfAmountEquals_AccountAvailableAmount() {
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
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> transactionService.withdraw("11", "111", 10));

        //Then
        assertTrue(exception.getMessage().contentEquals("The amount you want to withdraw is too much!"));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void withdraw_ShouldThrowRuntimeException_IfAmountIsBelowZero() {
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
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> transactionService.withdraw("11", "111", -1));

        //Then
        assertTrue(exception.getMessage().contentEquals("The amount should be above 0"));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }
}
