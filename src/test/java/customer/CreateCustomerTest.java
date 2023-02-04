package customer;

import eu.deltasource.internship.bankingsystem.exception.InvalidBirthdateInputException;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

/**
 * Tests:
 * - if the user can create a customer with correct input values.
 * - if the user cannot create a customer without first name or last name
 * - if the user cannot create a customer with incorrect birthdate - birth year should be between 1920 and 2013
 */
public class CreateCustomerTest {

    @Test
    public void should_CreateACustomer_IfInputIsCorrect() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When/Then
        assertDoesNotThrow(() -> customerFactory.createNewCustomer("Tom", "Smith", 1, 11, 2001));
    }

    @Test
    public void should_CreateACustomer_IfBirthMonthIsFebruary_DayIs29_InLeapYear() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When/Then
        assertDoesNotThrow(() -> customerFactory.createNewCustomer("Tom", "Smith", 29, 11, 2000));
    }

    @Test
    public void should_ThrowRuntimeException_IfFirstNameIsBlank() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidValueInputException = assertThrows(InvalidValueInputException.class,
                () -> customerFactory.createNewCustomer("", "Smith", 1, 10, 2001));

        //Then
        assertTrue(exceptionInvalidValueInputException.getMessage().contentEquals("Please enter a name!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfFirstNameContainsNonLetters() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidValueInputException = assertThrows(InvalidValueInputException.class,
                () -> customerFactory.createNewCustomer("Sam111", "Smith", 1, 10, 2001));

        //Then
        assertTrue(exceptionInvalidValueInputException.getMessage().contentEquals("The name you have entered contains a non-letter characters. Please enter a valid name!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfLastNameContainsNonLetters() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidValueInputException = assertThrows(InvalidValueInputException.class,
                () -> customerFactory.createNewCustomer("Sam", "Smith111", 1, 10, 2001));

        //Then
        assertTrue(exceptionInvalidValueInputException.getMessage().contentEquals("The name you have entered contains a non-letter characters. Please enter a valid name!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfLastNameIsBlank() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidValueInputException = assertThrows(InvalidValueInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "", 1, 10, 2001));

        //Then
        assertTrue(exceptionInvalidValueInputException.getMessage().contentEquals("Please enter a name!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBirthdayIsBelow1() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 0, 10, 2001));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBirthDayIsAbove31() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 32, 10, 2001));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBirthMonthIsFebruary_AndHas29Days_InNonLeapYear() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 29, 2, 2001));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("February can not have 29 days! It's not a leap year"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBirthMonthIsFebruary_AndHas30Days() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 30, 2, 2000));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("February can not have 30 days!"));
    }


    @Test
    public void should_ThrowRuntimeException_IfBirthMonthIsBelow1() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 1, 0, 2001));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBirthMonthIsAbove12() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 1, 13, 2001));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBirthYearIsBelow1920() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 1, 1, 1919));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBirthYearIsAbove2013() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();

        //When
        RuntimeException exceptionInvalidBirthdateInputException = assertThrows(InvalidBirthdateInputException.class,
                () -> customerFactory.createNewCustomer("Tom", "Smith", 1, 1, 2014));

        //Then
        assertTrue(exceptionInvalidBirthdateInputException.getMessage().contentEquals("The date you have entered is not valid! Please enter a valid date!"));
    }
}
