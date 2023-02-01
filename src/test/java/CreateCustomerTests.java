import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectDateInputException;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateCustomerTests {

    @Test
    public void createCorrectCustomer()  {
        CustomerService customer = new CustomerService();

        assertDoesNotThrow(() -> customer.createNewCustomer("Tom", "Smith", 15,1,1998));
    }

    @Test
    public void createCustomerWithNoFName(){
        CustomerService customer = new CustomerService();

        Exception exception = assertThrows(BlankInputException.class, () -> customer.createNewCustomer("", "Smith", 15,1,1998));
        assertTrue(exception.getMessage().contentEquals("Please enter a name"));
    }

    @Test
    public void createCustomerWithNoLName(){
        CustomerService customer = new CustomerService();

        Exception exception = assertThrows(BlankInputException.class, () -> customer.createNewCustomer("Tom", "", 15,1,1998));
        assertTrue(exception.getMessage().contentEquals("Please enter a name"));
    }

    @Test
    public void createCustomerWithNoFNameAndLName(){
        CustomerService customer = new CustomerService();

        Exception exception = assertThrows(BlankInputException.class, () -> customer.createNewCustomer("", "", 15,1,1998));
        assertTrue(exception.getMessage().contentEquals("Please enter a name"));
    }

    @Test
    public void createCustomerWithNoBirthdate(){
        CustomerService customer = new CustomerService();

        Exception exception = assertThrows(IncorrectDateInputException.class, () -> customer.createNewCustomer("Tom", "Smith",0 ,0,0));
        assertTrue(exception.getMessage().contentEquals("Please enter a valid date!"));
    }

    @Test
    public void createCustomerWithIncorrectBirthdateDay(){
        CustomerService customer = new CustomerService();

        Exception exception = assertThrows(IncorrectDateInputException.class, () -> customer.createNewCustomer("Tom", "Smith",0 ,12,2000));
        assertTrue(exception.getMessage().contentEquals("Please enter a valid date!"));
    }

    @Test
    public void createCustomerWithIncorrectBirthdateMonth(){
        CustomerService customer = new CustomerService();

        Exception exception = assertThrows(IncorrectDateInputException.class, () -> customer.createNewCustomer("Tom", "Smith",1 ,13,1998));
        assertTrue(exception.getMessage().contentEquals("Please enter a valid date!"));
    }

    @Test
    public void createCustomerWithIncorrectBirthdateYear(){
        CustomerService customer = new CustomerService();

        Exception exception = assertThrows(IncorrectDateInputException.class, () -> customer.createNewCustomer("Tom", "Smith",1 ,13,11998));
        assertTrue(exception.getMessage().contentEquals("Please enter a valid date!"));
    }
}
