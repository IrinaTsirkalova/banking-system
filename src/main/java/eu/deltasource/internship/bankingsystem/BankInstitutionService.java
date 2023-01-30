package eu.deltasource.internship.bankingsystem;

import java.util.UUID;

public class BankInstitutionService {
    private BankInstitutionModel bankInstitution = new BankInstitutionModel();

    public boolean createBankInstitution(String name, String address){
        bankInstitution.setId(UUID.randomUUID().toString());
        bankInstitution.setName(name);
        bankInstitution.setAddress(address);
        bankInstitution.setNumberOfCustomers(0);
        bankInstitution.setFeeList(bankInstitution.getFeeList());
        return false;
    }

    public BankInstitutionModel getBankInstitution(){
        return bankInstitution;
    }
    public boolean addNewCustomer(CustomerModel customer){
        bankInstitution.getCustomers().add(customer);
        bankInstitution.setNumberOfCustomers(bankInstitution.getNumberOfCustomers() + 1);
        return false;
    }

    public boolean addFee(Fee name, Double value){
        if(name != null && value != 0.0){
            bankInstitution.getFeeList().put(name, value);
        }
        return false;
    }

    public boolean addExchangeRate(ExchangeRate currency, Double value){
        if(currency != null && value != 0.0){
            bankInstitution.getExchangeRateList().put(currency, value);
        }
        return false;
    }
    public String printBankInstitution(){
        return bankInstitution.toString();
    }
}
