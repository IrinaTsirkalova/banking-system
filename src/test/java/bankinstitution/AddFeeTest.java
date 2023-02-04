package bankinstitution;

import eu.deltasource.internship.bankingsystem.enums.FeeType;
import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.repository.BankInstitutionRepository;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can successfully add fee in bank institution that exists
 * - if the user cannot add fee in bank institution that not exists
 * - if the user cannot add already existing fee
 * - if the user cannot add a fee without a type
 * - if the user cannot add a fee with value below 0.1
 */
public class AddFeeTest {

    @Test
    public void should_AddFee_ToBankInstitution() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 0.50);

        //Then
        BankInstitution bankInRepository = BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName("DSK");
        assertTrue(bankInRepository.getFeeList().containsKey(FeeType.BETWEEN_TWO_BANKS));
        bankInstitutionService.removeFee("DSK",FeeType.BETWEEN_TWO_BANKS);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void addFee_ShouldThrowRuntimeException_IfBankInstitutionInNotInRepository() {
        //Given
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        //When
        RuntimeException exceptionElementDoesNotExists = assertThrows(ElementDoesNotExistsException.class,
                () -> bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 0.50));

        //Then
        assertTrue(exceptionElementDoesNotExists.getMessage().contentEquals("There is no such bank"));
    }

    @Test
    public void addFee_ShouldThrowRuntimeException_IfBankInstitutionAlreadyHasIt() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 0.50);
        RuntimeException exceptionElementAlreadyExists = assertThrows(ElementAlreadyExistsException.class,
                () -> bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 0.50));

        //Then
        assertTrue(exceptionElementAlreadyExists.getMessage().contentEquals("This fee already exists!"));
        bankInstitutionService.removeFee("DSK",FeeType.BETWEEN_TWO_BANKS);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void addFee_ShouldThrowRuntimeException_IfFeeTypeIsBlank() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> bankInstitutionService.addNewFee("DSK", null, 0.50));

        //Then
        assertTrue(exception.getMessage().contentEquals("Please enter a fee type"));
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void addFee_ShouldThrowRuntimeException_IfFeeValueIsBelow0() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> bankInstitutionService.addNewFee("DSK", FeeType.BETWEEN_TWO_BANKS, 0.0));

        //Then
        assertTrue(exception.getMessage().contentEquals("Please enter a value that is at least 0.1!"));
        bankInstitutionService.removeBankInstitution("DSK");
    }
}
