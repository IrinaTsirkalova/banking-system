import eu.deltasource.internship.bankingsystem.BankInstitutionService;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateBankInstitutionTests {

    @Test
    public void createCorrectBankInstitutionCapitalLetters(){
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        assertTrue(bankInstitutionService.createBankInstitution("DSK", "Vasil Aprilov"));
    }

    @Test
    public void createCorrectBankInstitutionLowerLetters(){
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        assertTrue(bankInstitutionService.createBankInstitution("dsk", "Vasil Aprilov"));
    }

    @Test
    public void createBankInstitutionWithNoName(){
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        assertFalse(bankInstitutionService.createBankInstitution("", "Vasil Aprilov"));
    }

    @Test
    public void createBankInstitutionWithIncorrectNameSpecialChar(){
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        assertFalse(bankInstitutionService.createBankInstitution("DS@", "Vasil Aprilov"));
    }

    @Test
    public void createBankInstitutionWithIncorrectNameNumbers(){
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        assertFalse(bankInstitutionService.createBankInstitution("DS111", "Vasil Aprilov"));
    }

    @Test
    public void createBankInstitutionWithNoAddress(){
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        assertFalse(bankInstitutionService.createBankInstitution("DSK", ""));
    }

    @Test
    public void createBankInstitutionWithNoNameAndAddress(){
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        assertFalse(bankInstitutionService.createBankInstitution("", ""));
    }
}
