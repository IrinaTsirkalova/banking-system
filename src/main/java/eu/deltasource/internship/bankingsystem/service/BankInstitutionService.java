package eu.deltasource.internship.bankingsystem.service;

import eu.deltasource.internship.bankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.bankingsystem.enums.Fee;
import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectNameException;
import eu.deltasource.internship.bankingsystem.model.BankInstitutionModel;
import eu.deltasource.internship.bankingsystem.model.CustomerModel;

import java.util.UUID;

public class BankInstitutionService {
    private final BankInstitutionModel bankInstitution = new BankInstitutionModel();

    public boolean createBankInstitution(String name, String address) throws IncorrectNameException, BlankInputException {
        bankInstitution.setId(UUID.randomUUID().toString());
        bankInstitution.setName(name);
        bankInstitution.setAddress(address);
        bankInstitution.setNumberOfCustomers(0);
        bankInstitution.setFeeList(bankInstitution.getFeeList());
        return true;
    }

    public BankInstitutionModel getBankInstitution(){
        return bankInstitution;
    }
    public void addNewCustomer(CustomerModel customer){
        bankInstitution.getCustomers().add(customer);
        bankInstitution.setNumberOfCustomers(bankInstitution.getNumberOfCustomers() + 1);
    }

    public boolean addFee(Fee name, Double value){
        if(name != null && value != 0.0){
            bankInstitution.getFeeList().put(name, value);
            return true;
        }
        return false;
    }

    public void addExchangeRate(ExchangeRate currency, Double value){
        if(currency != null && value != 0.0){
            bankInstitution.getExchangeRateList().put(currency, value);
        }
    }
    public String printBankInstitution(){
        return bankInstitution.toString();
    }
}
