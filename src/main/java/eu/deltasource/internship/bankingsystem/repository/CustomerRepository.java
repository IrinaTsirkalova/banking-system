package eu.deltasource.internship.bankingsystem.repository;

import eu.deltasource.internship.bankingsystem.Validation;
import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.model.Customer;

import java.time.LocalDate;
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

    public void removeCustomer(Customer customer) {
        validateCustomer(customer);
        customers.remove(customer);
    }

    public void updateCustomer(Customer customer, String firstName, String lastName, int day, int month, int year) {
        validateCustomer(customer);
        validateUpdateCustomer(firstName, lastName, day, month, year);
        getCustomer(customer).setFirstName(firstName);
        getCustomer(customer).setLastName(lastName);
        getCustomer(customer).setBirthdate(LocalDate.of(year, month, day));
    }

    public Customer getCustomer(Customer searchCustomer) {
        validateCustomer(searchCustomer);
        return customers.stream().filter(ec -> searchCustomer.getFirstName().equals(ec.getFirstName())
                        && searchCustomer.getLastName().equals(ec.getLastName())
                        && searchCustomer.getBirthdate().isEqual(ec.getBirthdate()))
                .findFirst().get();
    }

    public void validateCustomer(Customer customer) {
        if (!doesCustomerExists(customer)) {
            throw new ElementDoesNotExistsException("There is no such customer!");
        }
    }

    public void validateUpdateCustomer(String firstName, String lastName, int day, int month, int year) {
        Validation.validateForNoName(firstName);
        Validation.validateForNonLetters(firstName);
        Validation.validateForNoName(lastName);
        Validation.validateForNonLetters(lastName);
        Validation.validateCorrectDate(day, month, year);
        Validation.validateFebruaryDays(day, month, year);
    }
}
