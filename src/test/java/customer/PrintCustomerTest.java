package customer;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can print a customer information that is already in the repository
 * - if the user cannot print a customer information is not in the repository
 */
public class PrintCustomerTest {

    @Test
    public void should_PrintCustomer_IfCustomerIsInTheRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer tom = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2001);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(tom);

        //When
        String info = customerService.printCustomerInfo(tom);

        //Then
        assertEquals(info, tom.toString());
    }

    @Test
    public void printCustomerInfo_ShouldThrowRuntimeException_IfCustomerIsNotInTheRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer tom = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2001);
        CustomerService customerService = new CustomerService();

        //When
        RuntimeException exceptionElementDoesNotExists = assertThrows(ElementDoesNotExistsException.class,
                () -> customerService.printCustomerInfo(tom));

        //Then
        assertTrue(exceptionElementDoesNotExists.getMessage().contentEquals("There is no such customer!"));
    }


}
