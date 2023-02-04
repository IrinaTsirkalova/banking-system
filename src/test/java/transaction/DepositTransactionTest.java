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
 *  - if the user can make a deposit transaction if he has a bank account
 *  - if the user cannot make a deposit if he hasn't a bank account
 *  - if the user cannot make a deposit with amount = 0 or below 0
 */
public class DepositTransactionTest {

    @Test
    public void deposit_Should_DecreaseAvailableAmount_InBankAccount() {
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
        transactionService.deposit("1","111", 5);

        //Then
        assertEquals(BankAccountRepository.bankAccountRepository.getBankAccountByIban("111").getAvailableAmount(), 15);
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
        transactionService.removeTransaction("1");
    }

    @Test
    public void deposit_ShouldTrowRuntimeException_IfThereIsNoAccount_WithThisIban() {
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
                ()->transactionService.deposit("1","11", 5));

        //Then
        assertTrue(exception.getMessage().contentEquals("There is no such bank account"));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void deposit_ShouldTrowRuntimeException_DepositAmountIs0() {
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
                ()->transactionService.deposit("1","111", 0));

        //Then
        assertTrue(exception.getMessage().contentEquals("The amount should be above 0"));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void deposit_ShouldTrowRuntimeException_DepositAmountIsBelow0() {
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
                ()->transactionService.deposit("1","111", -1));

        //Then
        assertTrue(exception.getMessage().contentEquals("The amount should be above 0"));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }
}
