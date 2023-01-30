import eu.deltasource.internship.bankingsystem.CustomerService;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateCustomerTests {

    @Test
    public void createCorrectCustomer(){
        CustomerService customer = new CustomerService();

        assertTrue(customer.createNewCustomer("Tom", "Smith", 15,1,1998));
    }
    @Test
    public void createCustomerWithNoFName(){
        CustomerService customer = new CustomerService();

        assertFalse(customer.createNewCustomer("", "Smith", 15,1,1998));
    }

    @Test
    public void createCustomerWithNoLName(){
        CustomerService customer = new CustomerService();

        assertFalse(customer.createNewCustomer("Tom", "", 15,1,1998));
    }

    @Test
    public void createCustomerWithNoFNameAndLName(){
        CustomerService customer = new CustomerService();

        assertFalse(customer.createNewCustomer("", "", 15,1,1998));
    }

    @Test
    public void createCustomerWithNoBirthdate(){
        CustomerService customer = new CustomerService();

        assertFalse(customer.createNewCustomer("Tom", "Smith",0 ,0,0));
    }

    @Test
    public void createCustomerWithIncorrectBirthdateDay(){
        CustomerService customer = new CustomerService();

        assertFalse(customer.createNewCustomer("Tom", "Smith",50 ,1,1998));
    }

    @Test
    public void createCustomerWithIncorrectBirthdateMonth(){
        CustomerService customer = new CustomerService();

        assertFalse(customer.createNewCustomer("Tom", "Smith",1 ,13,1998));
    }

    @Test
    public void createCustomerWithIncorrectBirthdateYear(){
        CustomerService customer = new CustomerService();

        assertFalse(customer.createNewCustomer("Tom", "Smith",1 ,12,1));
    }
}
