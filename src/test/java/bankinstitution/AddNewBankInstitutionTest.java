package bankinstitution;

import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.repository.BankInstitutionRepository;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can successfully add a bank institution in the repository
 * - if the user cannot add already existing bank institution in the repository
 */
public class AddNewBankInstitutionTest {

    @Test
    public void should_AddBankInstitution_InBankInstitutionRepository() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        //When
        bankInstitutionService.addNewBankInstitution(bank);

        //Then
        assertEquals(BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName("DSK"), bank);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void addBankInstitution_shouldThrowRuntimeException_IfBankInstitutionIsAlreadyInRepository() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        //When
        bankInstitutionService.addNewBankInstitution(bank);
        RuntimeException exceptionElementAlreadyExists = assertThrows(ElementAlreadyExistsException.class,
                () -> bankInstitutionService.addNewBankInstitution(bank));

        //Then
        assertTrue(exceptionElementAlreadyExists.getMessage().contentEquals("This bank is already exists"));
        bankInstitutionService.removeBankInstitution("DSK");
    }
}
