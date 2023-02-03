package eu.deltasource.internship.bankingsystem.repository;

import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.model.Customer;

import java.util.ArrayList;
import java.util.List;


public class CustomerRepository {
    public static final CustomerRepository customerRepository = new CustomerRepository();
    final List<Customer> customers = new ArrayList<>();

    public boolean doesCustomerExists(Customer searchCustomer) {
        return customers.stream().anyMatch(ec -> ec.getFirstName().equals(searchCustomer.getFirstName())
                && ec.getLastName().equals(searchCustomer.getLastName())
                && ec.getBirthdate().isEqual(searchCustomer.getBirthdate()));
    }

    public void addCustomer(Customer customer) {
        if (doesCustomerExists(customer)) {
            throw new ElementAlreadyExistsException("This person is already a customer!");
        }
        customers.add(customer);
    }

    public Customer getCustomer(Customer searchCustomer) {
        Customer customerResult = null;
        if (doesCustomerExists(searchCustomer)) {
            customerResult = customers.stream().filter(ec -> searchCustomer.getFirstName().equals(ec.getFirstName())
                            && searchCustomer.getLastName().equals(ec.getLastName())
                            && searchCustomer.getBirthdate().isEqual(ec.getBirthdate()))
                    .findFirst().get();
        }
        return customerResult;
    }
}
