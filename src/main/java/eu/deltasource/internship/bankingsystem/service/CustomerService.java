package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.repository.CustomerRepository;

/**
 * Used when:
 * - the user wants to remove a specific customer
 * - the user wants to add a new customer
 * - the user wants to update an existing customer
 * - the user wants to print information about a customer
 */
public class CustomerService {

    public Customer getCustomer(Customer customer) {
        return CustomerRepository.customerRepository.getCustomer(customer);
    }

    public void removeCustomer(Customer customer) {
        CustomerRepository.customerRepository.removeCustomer(customer);
    }

    public void addNewCustomer(Customer customer) {
        CustomerRepository.customerRepository.addCustomer(customer);
    }

    public void updateCustomer(Customer customer, String firstName, String lastName, int day, int month, int year) {
        CustomerRepository.customerRepository.updateCustomer(customer, firstName, lastName, day, month, year);
    }

    public String printCustomerInfo(Customer customer) {
        return getCustomer(customer).toString();
    }
}
