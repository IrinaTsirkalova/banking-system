package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectNameException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectDateInputException;
import eu.deltasource.internship.bankingsystem.model.CustomerModel;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerService {

    private CustomerModel customer = new CustomerModel();

    public boolean createNewCustomer(String fName, String lName, int day, int month, int year) throws BlankInputException, IncorrectNameException, IncorrectDateInputException {
        customer.setId(UUID.randomUUID().toString());
        try{
            customer.setFName(fName);
            customer.setLName(lName);
        }catch (BlankInputException cwn){
            throw new BlankInputException("Please enter a name");
        }catch (IncorrectNameException icn){
            throw new IncorrectNameException("Please enter a valid name");
        }

        try{
            if((day < 1 || day >= 31) || (month < 1 || month >= 12) || (year < 1920 || year >= 2013)){
                throw new IncorrectDateInputException("Please enter a valid date!");
            }
            LocalDate birthdate = LocalDate.of(year, month, day);
            customer.setBirthdate(birthdate);
        }catch (IncorrectDateInputException idi){
            throw new IncorrectDateInputException("Please enter a valid date!");
        }
        return true;
    }

    public CustomerModel getCustomer(){
        return customer;
    }

    public String printCustomer(){
        return customer.toString();
    }
}
