package eu.deltasource.internship.bankingsystem;

import java.util.*;

public class BankInstitution {
    private String id;
    private String name;
    private String address;
    private int numberOfCustomers;
    private Map<String, Double> priceList = new TreeMap<>();
    private List<Customer> customers = new ArrayList<>();

    public BankInstitution(String name,String address) {
        setId();
        setName(name);
        setAddress(address);
        setNumberOfCustomers(0);
        setPriceList(priceList);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public Map<String, Double> getPriceList() {
        return priceList;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setId() {
        id = UUID.randomUUID().toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumberOfCustomers(int numberOfCustomers) {
       this.numberOfCustomers = numberOfCustomers;
    }

    public void setPriceList(Map<String, Double> priceList) {
        this.priceList = priceList;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getPriceListInfo(Map<String, Double> priceList){
        String priceListInfo = "";
        if(priceList.size() == 0){
            priceListInfo =  "No taxes or fees included";
        }
        for(Map.Entry<String, Double> element : priceList.entrySet()){
           priceListInfo += "Tax/Fee name: " + element.getKey() + "; Tax/Fee value: " + element.getValue() + "\n";
        }
        return  priceListInfo;
    }

    public void addNewCustomer(Customer customer){
      customers.add(customer);
    }

    public boolean addFee(String name, Double value){
        if(name != null && value != 0.0){
            priceList.put(name, value);
        }
        return false;
    }

    public void updateNumberOfCustomers(){
        setNumberOfCustomers(customers.size());
    }
    public String toString() {
        updateNumberOfCustomers();
        return "Bank institution information: ----------------\n" +
                "Bank id: " + getId() +
                "; Bank name: " + getName() +
                ";\nAddress: " + getAddress() +
                "; Number of customers: " + getNumberOfCustomers() +
                ";\nTaxes:------------\n" + getPriceListInfo(getPriceList()) + "\n";
    }
}
