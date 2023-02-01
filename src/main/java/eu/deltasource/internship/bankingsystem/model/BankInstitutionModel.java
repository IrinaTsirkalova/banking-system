package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.enums.ExchangeRate;
import eu.deltasource.internship.bankingsystem.enums.Fee;
import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Models a bank institution with id, name, address, number of customers, fees, exchange rates and customers
 */
public class BankInstitutionModel {

    private String id;
    private String name;
    private String address;
    private int numberOfCustomers;
    private Map<Fee, Double> feeList = new TreeMap<>();
    private Map<ExchangeRate, Double> exchangeRateList = new TreeMap<>();
    private final List<CustomerModel> customers = new ArrayList<>();

    public String getName(){
        if(name.equals(null)){
            name = "No bank";
        }
        return name;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public Map<Fee, Double> getFeeList() {
        return feeList;
    }

    public Map<ExchangeRate, Double> getExchangeRateList() {
        return exchangeRateList;
    }

    public List<CustomerModel> getCustomers() {
        return customers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) throws IncorrectNameException, BlankInputException {
        if(name.equals("")){
            throw new BlankInputException();
        }
        Pattern nonLettersPattern = Pattern.compile("[^a-zA-Z]");
        Matcher nonLettersNameMatcher = nonLettersPattern.matcher(name);
        if(nonLettersNameMatcher.find()){
            throw  new IncorrectNameException();
        }
        this.name = name;
    }

    public void setAddress(String address) throws BlankInputException {
        this.address = address;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public void setFeeList(Map<Fee, Double> feeList) {
        this.feeList = feeList;
    }

    /**
     * Can be used to print information about bank fees
     * @param feeList
     * @return feeListInfo String format value that contains information about the fee - name and value
     */
    public String getFeeListInfo(Map<Fee, Double> feeList){
        String feeListInfo = "";
        if(feeList.size() == 0){
            feeListInfo =  "No taxes or fees included yet!";
        }else{
            for(Map.Entry<Fee, Double> element : feeList.entrySet()){
                feeListInfo += "Fee name: " + element.getKey() + "; Value: " + element.getValue() + "\n";
            }
        }
        return  feeListInfo;
    }

    /**
     * Can be used to print information about bank exchange rates
     * @param exchangeRateList
     * @return exchangeRateListInfo String format value that contains information about the exchange rates - name and value
     */
    public String getExchangeRateListInfo(Map<ExchangeRate, Double> exchangeRateList){
        String exchangeRateListInfo = "";
        if(exchangeRateList.size() == 0){
            exchangeRateListInfo =  "No exchange rates included yet!";
        }else{
            for(Map.Entry<ExchangeRate, Double> element : exchangeRateList.entrySet()){
                exchangeRateListInfo += "Exchange rate name: " + element.getKey() + ";Exchange rate value: " + element.getValue() + "\n";
            }
        }
        return  exchangeRateListInfo;
    }

    public String toString() {
        return "Bank institution information: ----------------\n" +
                "Bank id: " + id +
                "; Bank name: " + name +
                "; Address: " + address +
                "; Number of customers: " + numberOfCustomers +
                "; Taxes:------------\n" + getFeeListInfo(feeList) + "\n" +
                "Exchange rates:------------\n" + getExchangeRateListInfo(exchangeRateList) + "\n";
    }
}
