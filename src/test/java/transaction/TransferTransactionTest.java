package transaction;

import eu.deltasource.internship.bankingsystem.enums.AccountType;
import eu.deltasource.internship.bankingsystem.enums.Currency;
import eu.deltasource.internship.bankingsystem.enums.ExchangeRatePair;
import eu.deltasource.internship.bankingsystem.enums.FeeType;
import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.model.TransferTransaction;
import eu.deltasource.internship.bankingsystem.repository.BankAccountRepository;
import eu.deltasource.internship.bankingsystem.service.*;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can make a successful transfer transaction
 * - if the user cannot make a transfer transaction if the source or the target iban's are incorrect
 * - if the user cannot make a transfer transaction if transferred amount is bigger than source available amount with fees
 * - if the user can make a successful transaction between two account with the same currency without exchange rate
 */

public class TransferTransactionTest {

    @Test
    public void transferTransaction_Should_DecreaseAmountInSourceAccount_AndIncreaseAmountInTargetAccount() {
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
        transferTransactionService.transfer("2","DSK", "RFB", "111", "123", 10);

        //Then
        assertEquals(BankAccountRepository.bankAccountRepository.getBankAccountByIban("123").getAvailableAmount(), 60);
        assertEquals(BankAccountRepository.bankAccountRepository.getBankAccountByIban("111").getAvailableAmount(), 28.349999999999998);
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
        transferTransactionService.removeTransaction("2");
    }

    @Test
    public void transferTransaction_WithoutExchangeRate_ForSameCurrencyAccounts() {
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
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        transferTransactionService.transfer("2","DSK", "RFB", "111", "123", 10);

        //Then
        assertEquals(BankAccountRepository.bankAccountRepository.getBankAccountByIban("123").getAvailableAmount(), 60);
        assertEquals(BankAccountRepository.bankAccountRepository.getBankAccountByIban("111").getAvailableAmount(), 37.95);
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
        transferTransactionService.removeTransaction("2");
    }

    @Test
    public void transferTransaction_ShouldThrowRuntimeException_IfSourceIbanIsIncorrect() {
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
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 0.50);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        RuntimeException exception = assertThrows(ElementDoesNotExistsException.class,
                () -> transferTransactionService.transfer("2","DSK", "RFB", "11", "123", 10));

        //Then
        assertTrue(exception.getMessage().contentEquals("There is no such bank account"));
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
    }

    @Test
    public void transferTransaction_ShouldThrowRuntimeException_IfTargetIbanIsIncorrect() {
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
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 0.50);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        RuntimeException exception = assertThrows(ElementDoesNotExistsException.class,
                () -> transferTransactionService.transfer("2","DSK", "RFB", "111", "12", 10));

        //Then
        assertTrue(exception.getMessage().contentEquals("There is no such bank account"));
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
    }

    @Test
    public void transferTransaction_ShouldThrowRuntimeException_IfTransferredAmountIsBiggerThan_SourceAmount() {
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
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 0.50);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.CURRENT_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> transferTransactionService.transfer("2","DSK", "RFB", "111", "123", 100));

        //Then
        assertTrue(exception.getMessage().contentEquals("The amount is too high!"));
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
    }

    @Test
    public void transferTransaction_ShouldThrowRuntimeException_IfOneAccountIsSavings() {
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
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.BGN_EUR, 0.50);
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 1.55);
        bankInstitutionService.addNewFee("DSK", FeeType.TRANSFER_BETWEEN_TWO_ACCOUNTS, 0.50);
        bankAccountService.addNewBankAccount("DSK", sourceCustomer, "111", Currency.BGN, 50, AccountType.CURRENT_ACCOUNT);
        bankAccountService.addNewBankAccount("RFB", sourceCustomer, "123", Currency.EUR, 50, AccountType.SAVINGS_ACCOUNT);

        //When
        TransferTransactionService transferTransactionService = new TransferTransactionService();
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> transferTransactionService.transfer("2","DSK", "RFB", "111", "123", 10));

        //Then
        assertTrue(exception.getMessage().contentEquals("Both of the account have to be current"));
        bankAccountService.removeBankAccount("111");
        bankAccountService.removeBankAccount("123");
        customerService.removeCustomer(sourceCustomer);
        customerService.removeCustomer(targetCustomer);
        bankInstitutionService.removeBankInstitution("DSK");
        bankInstitutionService.removeBankInstitution("RFB");
    }
}
