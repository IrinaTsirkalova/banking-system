package customer;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can get a customer that is already in the repository
 * - if the user cannot get a customer is not in the repository
 */
public class GetCustomerTest {

    @Test
    public void should_GetCustomer_IfCustomerIsInTheRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer tom = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2001);
        CustomerService customerService = new CustomerService();

        //When
        customerService.addNewCustomer(tom);

        //Then
        assertEquals(customerService.getCustomer(tom), tom);
        customerService.removeCustomer(tom);
    }

    @Test
    public void getCustomer_ShouldThrowRuntimeException_IfCustomerIsNotInTheRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer tom = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2001);
        CustomerService customerService = new CustomerService();

        //When
        RuntimeException exceptionElementDoesNotExists = assertThrows(ElementDoesNotExistsException.class,
                () -> customerService.getCustomer(tom));

        //Then
        assertTrue(exceptionElementDoesNotExists.getMessage().contentEquals("There is no such customer!"));
    }
}
