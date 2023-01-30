package eu.deltasource.internship.bankingsystem;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BankInstitutionModel {
    private String id;
    private String name;
    private String address;
    private int numberOfCustomers;
    private Map<Fee, Double> feeList = new TreeMap<>();
    private Map<ExchangeRate, Double> exchangeRateList = new TreeMap<>();
    private final List<CustomerModel> customers = new ArrayList<>();

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        if(name.equals(null)){
            name = "No bank";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public Map<Fee, Double> getFeeList() {
        return feeList;
    }

    public void setFeeList(Map<Fee, Double> feeList) {
        this.feeList = feeList;
    }

    public Map<ExchangeRate, Double> getExchangeRateList() {
        return exchangeRateList;
    }

    public List<CustomerModel> getCustomers() {
        return customers;
    }

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
