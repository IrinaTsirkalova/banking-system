import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectNameException;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;
import eu.deltasource.internship.bankingsystem.enums.Fee;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class AddFeeInBankInstitutionTests {

    @Test
    public void addSuccessfullyFeeInBankInstitution() throws IncorrectNameException, BlankInputException {
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");
        Fee fee = Fee.BETWEEN_TWO_BANKS;
        double feeValue = 0.80;
        boolean isAdded = false;

        bankInstitution.addFee(fee,feeValue);
        Map<Fee, Double> feeList = bankInstitution.getBankInstitution().getFeeList();
        for(Map.Entry<Fee, Double> feeEntry : feeList.entrySet()){
            if(feeEntry.getKey().equals(fee) && feeEntry.getValue().equals(feeValue)){
                isAdded = true;
                break;
            }
        }

        assertTrue(isAdded);
    }

    @Test
    public void addFeeWithNoFeeName() throws IncorrectNameException, BlankInputException {
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");

        boolean isAdded = bankInstitution.addFee(null,0.80);

        assertFalse(isAdded);
    }

    @Test
    public void addFeeWithNoFeeValue() throws IncorrectNameException, BlankInputException {
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");

        boolean isAdded = bankInstitution.addFee(Fee.BETWEEN_TWO_BANKS,0.0);

        assertFalse(isAdded);
    }

    @Test
    public void addFeeWithNoFeeNameAndValue() throws IncorrectNameException, BlankInputException {
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");

        boolean isAdded = bankInstitution.addFee(null,0.0);

        assertFalse(isAdded);
    }

}
