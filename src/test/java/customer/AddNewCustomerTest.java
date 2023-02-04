package customer;

import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.factory.CustomerFactory;
import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.repository.CustomerRepository;
import eu.deltasource.internship.bankingsystem.service.CustomerService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests:
 * - if the user can successfully add a customer in the repository
 * - if the user cannot add already existing customer in the repository
 */
public class AddNewCustomerTest {

    @Test
    public void should_AddCustomer_InCustomerRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer tom = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2001);
        CustomerService customerService = new CustomerService();

        //When
        customerService.addNewCustomer(tom);

        //Then
        assertEquals(CustomerRepository.customerRepository.getCustomer(tom), tom);
        customerService.removeCustomer(tom);
    }

    @Test
    public void addCustomer_shouldThrowRuntimeException_IfCustomerIsAlreadyInRepository() {
        //Given
        CustomerFactory customerFactory = new CustomerFactory();
        Customer tom = customerFactory.createNewCustomer("Tom", "Smith", 1, 10, 2001);
        CustomerService customerService = new CustomerService();

        //When
        customerService.addNewCustomer(tom);
        RuntimeException exceptionElementAlreadyExists = assertThrows(ElementAlreadyExistsException.class,
                () -> customerService.addNewCustomer(tom));

        //Then
        assertTrue(exceptionElementAlreadyExists.getMessage().contentEquals("This person is already a customer!"));
        customerService.removeCustomer(tom);
    }
}
