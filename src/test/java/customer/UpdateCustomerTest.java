package customer;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.InvalidBirthdateInputException;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can update a customer if it exists and the input values are correct
 * - if the user cannot update a customer if it doesn't exist
 * - if the user cannot update a customer that exists with invalid input data
 */
public class UpdateCustomerTest {

    @Test
    public void should_UpdateCustomer_IfCustomerIsInRepository_AndInputValuesAreCorrect() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        customerService.updateCustomer(customer, "Tim", "Smith", 1, 10, 2010);

        //Then
        assertEquals(customerService.getCustomer(customer).getFirstName(), "Tim");
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_shouldTrowRuntimeException_IfCustomerIsNotInTheRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();

        //When
        RuntimeException exceptionElementDoesNotExists = assertThrows(ElementDoesNotExistsException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 1, 10, 2010));

        //Then
        assertTrue(exceptionElementDoesNotExists.getMessage().contentEquals("There is no such customer!"));
    }

    @Test
    public void updateCustomer_shouldTrowRuntimeException_IfCustomerUpdateInputValue_FirstNameIsBlank() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidInputValue = assertThrows(InvalidValueInputException.class,
                () -> customerService.updateCustomer(customer, "", "Smith", 1, 10, 2010));

        //Then
        assertTrue(exceptionInvalidInputValue.getMessage().contentEquals("Please enter a name!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_shouldTrowRuntimeException_IfCustomerUpdateInputValue_FirstNameContainsNonLetter() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidInputValue = assertThrows(InvalidValueInputException.class,
                () -> customerService.updateCustomer(customer, "1111", "Smith", 1, 10, 2010));

        //Then
        assertTrue(exceptionInvalidInputValue.getMessage().contentEquals("The name you have entered contains a non-letter characters. Please enter a valid name!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_shouldTrowRuntimeException_IfCustomerUpdateInputValue_LastNameContainsNonLetter() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidInputValue = assertThrows(InvalidValueInputException.class,
                () -> customerService.updateCustomer(customer, "Tom", "Smith11", 1, 10, 2010));

        //Then
        assertTrue(exceptionInvalidInputValue.getMessage().contentEquals("The name you have entered contains a non-letter characters. Please enter a valid name!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_shouldTrowRuntimeException_IfCustomerUpdateInputValue_LastNameIsBlank() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidInputValue = assertThrows(InvalidValueInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "", 1, 10, 2010));

        //Then
        assertTrue(exceptionInvalidInputValue.getMessage().contentEquals("Please enter a name!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void should_UpdateCustomer_IfBirthMonthIsFebruary_DayIs29_InLeapYear() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When/Then
        assertDoesNotThrow(() -> customerService.updateCustomer(customer, "Tim", "Smith", 29, 11, 2000));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthdayIsBelow1() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 0, 10, 2001));
        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthDayIsAbove31() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 31, 10, 2001));
        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthMonthIsFebruary_AndHas29Days_InNonLeapYear() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 29, 2, 2001));
        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("February can not have 29 days! It's not a leap year"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthMonthIsFebruary_AndHas30Days() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 30, 2, 2000));
        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("February can not have 30 days!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthMonthIsBelow1() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 30, 0, 2000));
        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthMonthIsAbove12() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 30, 13, 2000));
        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthYearIsBelow1920() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 30, 12, 1919));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
        customerService.removeCustomer(customer);
    }

    @Test
    public void updateCustomer_ShouldThrowRuntimeException_IfBirthYearIsAbove2013() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2010);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(customer);

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerService.updateCustomer(customer, "Tim", "Smith", 30, 12, 2014));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
        customerService.removeCustomer(customer);
    }
}
