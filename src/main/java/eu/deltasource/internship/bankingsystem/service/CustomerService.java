package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.model.Customer;
import eu.deltasource.internship.bankingsystem.repository.CustomerRepository;

public class CustomerService {

    public Customer getCustomer(Customer customer) {
        return CustomerRepository.customerRepository.getCustomer(customer);
    }

    public void addNewCustomer(Customer customer) {
        CustomerRepository.customerRepository.addCustomer(customer);
    }

    public String printCustomerInfo(Customer customer) {
        return getCustomer(customer).toString();
    }
}
