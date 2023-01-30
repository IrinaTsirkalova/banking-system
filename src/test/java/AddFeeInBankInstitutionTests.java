import eu.deltasource.internship.bankingsystem.BankInstitutionService;
import eu.deltasource.internship.bankingsystem.Fee;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class AddFeeInBankInstitutionTests {

    //bankInstitution.addFee(null, 0.80);
    //bankInstitution.addFee(null, 0.0);
    @Test
    public void addSuccessfullyFeeInBankInstitution(){
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
    public void addFeeWithNoFeeName(){
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");

        boolean isAdded = bankInstitution.addFee(null,0.80);

        assertFalse(isAdded);
    }

    @Test
    public void addFeeWithNoFeeValue(){
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");

        boolean isAdded = bankInstitution.addFee(Fee.BETWEEN_TWO_BANKS,0.0);

        assertFalse(isAdded);
    }

    @Test
    public void addFeeWithNoFeeNameAndValue(){
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");

        boolean isAdded = bankInstitution.addFee(null,0.0);

        assertFalse(isAdded);
    }

}
