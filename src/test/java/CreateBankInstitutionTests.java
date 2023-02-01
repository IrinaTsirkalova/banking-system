import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectNameException;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateBankInstitutionTests {

    @Test
    public void createCorrectBankInstitutionCapitalLetters() throws IncorrectNameException {
        BankInstitutionService bank = new BankInstitutionService();

        assertDoesNotThrow(() -> bank.createBankInstitution("DSK", "Vasil Aprilov"));
    }

    @Test
    public void createCorrectBankInstitutionLowerLetters() throws IncorrectNameException {
        BankInstitutionService bank = new BankInstitutionService();

        assertDoesNotThrow(() -> bank.createBankInstitution("dsk", "Vasil Aprilov"));
    }

    @Test
    public void createBankInstitutionWithNoName() throws IncorrectNameException {
        BankInstitutionService bank = new BankInstitutionService();

        //Exception exception = assertThrows(BlankInputException.class, () -> bank.createBankInstitution("", "Vasil Aprilov"));
        //assertTrue(exception.getMessage().contentEquals("Please enter a name!"));
    }

    @Test
    public void createBankInstitutionWithIncorrectNameSpecialChar() throws IncorrectNameException {
        BankInstitutionService bank = new BankInstitutionService();

       // Exception exception = assertThrows(IncorrectNameException.class, () -> bank.createBankInstitution("DS@", "Vasil Aprilov"));
        //(exception.getMessage().contentEquals("Please enter a correct bank name!"));
    }

    @Test
    public void createBankInstitutionWithIncorrectNameNumbers() throws IncorrectNameException {
        BankInstitutionService bank = new BankInstitutionService();

        //Exception exception = assertThrows(IncorrectNameException.class, () -> bank.createBankInstitution("DS111", "Vasil Aprilov"));
        //assertTrue(exception.getMessage().contentEquals("Please enter a correct bank name!"));

    }

    @Test
    public void createBankInstitutionWithNoAddress() throws IncorrectNameException, BlankInputException {
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        //assertFalse(bankInstitutionService.createBankInstitution("DSK", ""));
    }

    @Test
    public void createBankInstitutionWithNoNameAndAddress() throws IncorrectNameException, BlankInputException {
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        //assertFalse(bankInstitutionService.createBankInstitution("", ""));
    }
}
