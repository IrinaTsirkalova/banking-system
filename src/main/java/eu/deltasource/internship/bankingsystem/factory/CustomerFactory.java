package eu.deltasource.internship.bankingsystem.factory;

import eu.deltasource.internship.bankingsystem.Validation;
import eu.deltasource.internship.bankingsystem.model.Customer;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerFactory {
    public Customer createNewCustomer(String firstName, String lastName, int day, int month, int year) {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID().toString());
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        Validation.validateCorrectDate(day, month, year);
        LocalDate birthdate = LocalDate.of(year, month, day);
        customer.setBirthdate(birthdate);
        return customer;
    }
}
