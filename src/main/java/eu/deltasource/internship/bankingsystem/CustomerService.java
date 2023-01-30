package eu.deltasource.internship.bankingsystem;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerService {
    private CustomerModel customer = new CustomerModel();

    public boolean createNewCustomer(String fName, String lName, int day, int month, int year){
        customer.setId(UUID.randomUUID().toString());
        if(fName.equals("") || lName.equals("")){
            return false;
        }
        if((day < 1 || day >= 31) || (month < 1 || month >= 12) || (year < 1920 || year >= 2013)){
            return false;
        }
        customer.setfName(fName);
        customer.setlName(lName);
        LocalDate birthdate = LocalDate.of(year, month, day);
        customer.setBirthdate(birthdate);
        return true;
    }

    public CustomerModel getCustomer(){
        return customer;
    }

    public String printCustomer(){
        return customer.toString();
    }
}
