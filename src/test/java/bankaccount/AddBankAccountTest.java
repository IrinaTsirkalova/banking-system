package bankaccount;

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
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can create a bank account with correct input data
 * - if the user can create a bank account without currency, then the default currency is BGN
 * - if the user cannot create a bank account if the customer is not in the repository
 * - if the user cannot create a bank account without an amount
 * - if the user cannot create a bank account without an iban
 * - if the user cannot create a bank account without an existing bank
 */
public class AddBankAccountTest {

    @Test
    public void should_CreateAndAddBankAccount_IfInputIsCorrect() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //Then
        assertDoesNotThrow(() ->  bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT));
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void should_CreateTwoAccount_ForSameCustomer() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //Then
        assertDoesNotThrow(() ->  bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT));
        assertDoesNotThrow(() ->  bankAccountService.addNewBankAccount("DSK", lily, "1112", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT));
        bankAccountService.removeBankAccount("1112");
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void should_CreateAndAddBankAccount_IfCurrencyIsNull() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //Then
        assertDoesNotThrow(() ->  bankAccountService.addNewBankAccount("DSK", lily, "111",null, 15, AccountType.CURRENT_ACCOUNT));
        assertEquals(Currency.BGN,BankAccountRepository.bankAccountRepository.getBankAccountByIban("111").getCurrency());
        bankAccountService.removeBankAccount("111");
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void createAndAddBankAccount_ShouldThrowRuntimeException_IfBankNameIsBlank() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");

        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //When
        RuntimeException exception = assertThrows(ElementDoesNotExistsException.class,
                () -> bankAccountService.addNewBankAccount("", lily, "111", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT));

        //Then
        assertEquals("There is no such bank", exception.getMessage());
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void createAndAddBankAccount_ShouldThrowRuntimeException_IfIbanIsBlank() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");

        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //When
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> bankAccountService.addNewBankAccount("DSK", lily, "", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT));

        //Then
        assertEquals("Please enter an iban", exception.getMessage());
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void createAndAddBankAccount_ShouldThrowRuntimeException_IfBankDoesNotExists() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");

        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //When
        RuntimeException exception = assertThrows(ElementDoesNotExistsException.class,
                () -> bankAccountService.addNewBankAccount("RFB", lily, "111", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT));

        //Then
        assertEquals("There is no such bank", exception.getMessage());
        customerService.removeCustomer(lily);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void createAndAddBankAccount_ShouldThrowRuntimeException_IfCustomerDoesNotExists() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");

        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //When
        RuntimeException exception = assertThrows(ElementDoesNotExistsException.class,
                () -> bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 15, AccountType.CURRENT_ACCOUNT));

        //Then
        assertEquals("There is no such customer!", exception.getMessage());
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void createAndAddBankAccount_ShouldThrowRuntimeException_IfAmountIsZero() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");

        CustomerFactory customerFactory = new CustomerFactory();
        Customer lily = customerFactory.createNewCustomer("Lily", "Smith", 1, 1, 1998);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(lily);
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);
        BankAccountService bankAccountService = new BankAccountService();

        //When
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> bankAccountService.addNewBankAccount("DSK", lily, "111", Currency.BGN, 0, AccountType.CURRENT_ACCOUNT));

        //Then
        assertEquals("The amount should be above 0", exception.getMessage());
        bankInstitutionService.removeBankInstitution("DSK");
        customerService.removeCustomer(lily);
    }
}
