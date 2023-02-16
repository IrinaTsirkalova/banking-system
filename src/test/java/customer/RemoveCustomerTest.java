package customer;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can remove a customer from repository
 * - if the user cannot remove a customer that is not in the repository
 */
public class RemoveCustomerTest {

    @Test
    public void should_RemoveCustomer_FromCustomerRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer tom = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2001);
        CustomerService customerService = new CustomerService();
        customerService.addNewCustomer(tom);

        //When
        customerService.removeCustomer(tom);

        //Then
        RuntimeException exceptionElementDoesNotExists = assertThrows(ElementDoesNotExistsException.class,
                () -> customerService.getCustomer(tom));
        assertTrue(exceptionElementDoesNotExists.getMessage().contentEquals("There is no such customer!"));
    }

    @Test
    public void removeCustomer_Should_ThrowRuntimeException_IfCustomerIsNotInRepository() {
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
