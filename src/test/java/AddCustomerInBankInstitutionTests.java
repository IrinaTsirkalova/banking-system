import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectNameException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectDateInputException;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;
import eu.deltasource.internship.bankingsystem.model.CustomerModel;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import org.junit.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddCustomerInBankInstitutionTests {

    @Test
    public void increaseNumberOfCustomersAfterAddingNew() throws BlankInputException, IncorrectNameException, IncorrectDateInputException {
        CustomerService customer = new CustomerService();
        customer.createNewCustomer("Tom","Smith",4,11,1988);
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");

        int numberOfCustomersBeforeAdding = bankInstitution.getBankInstitution().getNumberOfCustomers();
        bankInstitution.addNewCustomer(customer.getCustomer());
        int numberOfCustomersAfterAdding = bankInstitution.getBankInstitution().getNumberOfCustomers();

        assertEquals(0,numberOfCustomersBeforeAdding);
        assertEquals(1,numberOfCustomersAfterAdding);
    }

    @Test
    public void addSuccessfullyNewCustomerInBankInstitution() throws BlankInputException, IncorrectNameException, IncorrectDateInputException {
        CustomerService customer = new CustomerService();
        customer.createNewCustomer("Tom","Smith",4,11,1988);
        BankInstitutionService bankInstitution = new BankInstitutionService();
        bankInstitution.createBankInstitution("DSK", "Vasil Aprilov");
        boolean isAdded = false;

        bankInstitution.addNewCustomer(customer.getCustomer());
        List<CustomerModel> customers = bankInstitution.getBankInstitution().getCustomers();
        for(CustomerModel customerModel : customers){
            if(customerModel.equals(customer.getCustomer())){
                isAdded = true;
                break;
            }
        }

        assertTrue(isAdded);
    }

}
