package bankinstitution;

import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can create a bank institution with correct input values.
 * - if the user cannot create a bank institution without name or address
 * - if the user cannot create a bank institution with incorrect name - name containing non-letters characters
 */
public class CreateBankInstitutionTest {

    @Test
    public void should_CreateACustomer_IfInputIsCorrect() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();

        //When/Then
        assertDoesNotThrow(() -> bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov 22"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBankNameIsBlank() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();

        //When
        RuntimeException exceptionInvalidValueInputException = assertThrows(InvalidValueInputException.class,
                () -> bankInstitutionFactory.createBankInstitution("", "Vasil Aprilov 22"));

        //Then
        assertTrue(exceptionInvalidValueInputException.getMessage().contentEquals("Please enter a name!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfBankNameContainsNonLetter() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();

        //When
        RuntimeException exceptionInvalidValueInputException = assertThrows(InvalidValueInputException.class,
                () -> bankInstitutionFactory.createBankInstitution("DSK111", "Vasil Aprilov 22"));

        //Then
        assertTrue(exceptionInvalidValueInputException.getMessage().contentEquals("The name you have entered contains a non-letter characters. Please enter a valid name!"));
    }

    @Test
    public void should_ThrowRuntimeException_IfAddressIsBlank() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();

        //When
        RuntimeException exceptionInvalidValueInputException = assertThrows(InvalidValueInputException.class,
                () -> bankInstitutionFactory.createBankInstitution("DSK", ""));

        //Then
        assertTrue(exceptionInvalidValueInputException.getMessage().contentEquals("Please enter an address for the bank!"));
    }
}
