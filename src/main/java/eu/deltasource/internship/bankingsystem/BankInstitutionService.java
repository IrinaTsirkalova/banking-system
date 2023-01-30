package eu.deltasource.internship.bankingsystem;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankInstitutionService {
    private final BankInstitutionModel bankInstitution = new BankInstitutionModel();

    public boolean createBankInstitution(String name, String address){
        bankInstitution.setId(UUID.randomUUID().toString());
        Pattern nonLettersPattern = Pattern.compile("[^a-zA-Z]");
        Matcher nonLettersNameMatcher = nonLettersPattern.matcher(name);
        if(name.equals("") || nonLettersNameMatcher.find() || address.equals("")){
            return false;
        }
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
