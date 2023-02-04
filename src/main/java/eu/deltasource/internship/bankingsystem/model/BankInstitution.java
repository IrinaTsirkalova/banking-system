package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.Validation;
import eu.deltasource.internship.bankingsystem.enums.ExchangeRatePair;
import eu.deltasource.internship.bankingsystem.enums.FeeType;
import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;

import java.util.*;

public class BankInstitution {

    private String id;
    private String name;
    private String address;
    private final Map<FeeType, Double> feeList = new TreeMap<>();
    private final Map<ExchangeRatePair, Double> exchangeRatePairList = new TreeMap<>();

    public void validateFee(FeeType fee) {
        if (!doesFeeExists(fee)) {
            throw new ElementDoesNotExistsException("There is no such fee");
        }
    }

    public void validateExchangeRatePair(ExchangeRatePair pair) {
        if (!doesExchangeRatePairExists(pair)) {
            throw new ElementDoesNotExistsException("There is no such exchange rate pair");
        }
    }

    public boolean doesFeeExists(FeeType type) {
        return feeList.containsKey(type);
    }

    public boolean doesExchangeRatePairExists(ExchangeRatePair exchangeRatePair) {
        return exchangeRatePairList.containsKey(exchangeRatePair);
    }

    public void addFee(FeeType type, Double value) {
        Validation.validateFeeInputName(type);
        if (doesFeeExists(type)) {
            throw new ElementAlreadyExistsException("This fee already exists!");
        }
        Validation.validateFeeInputValue(value);
        feeList.put(type, value);
    }

    public void removeFee(FeeType type) {
        Validation.validateFeeInputName(type);
        validateFee(type);
        feeList.remove(type);
    }

    public void updateFee(FeeType type, Double value) {
        Validation.validateFeeInputName(type);
        validateFee(type);
        feeList.replace(type, value);
    }

    public void addExchangeRatePair(ExchangeRatePair exchangeRatePair, Double exchangeRatePairValue) {
        Validation.validateExchangeRatePairInputName(exchangeRatePair);
        Validation.validateExchangeRatePairInputValue(exchangeRatePairValue);
        if (doesExchangeRatePairExists(exchangeRatePair)) {
            throw new ElementAlreadyExistsException("This pair already exists!");
        }
        exchangeRatePairList.put(exchangeRatePair, exchangeRatePairValue);
    }

    public void removeExchangeRate(ExchangeRatePair pair) {
        Validation.validateExchangeRatePairInputName(pair);
        validateExchangeRatePair(pair);
        exchangeRatePairList.remove(pair);
    }

    public void updateExchangeRate(ExchangeRatePair exchangeRatePair, Double value) {
        validateExchangeRatePair(exchangeRatePair);
        exchangeRatePairList.replace(exchangeRatePair, value);
    }

    public String getBankAccountListInfo(List<BankAccount> bankAccounts) {
        StringBuilder bankAccountsListInfo = new StringBuilder();
        if (bankAccounts.size() == 0) {
            return "The bank doesn't have customers yet!";
        }
        for (BankAccount bankAccount : bankAccounts) {
            bankAccountsListInfo.append(bankAccount);
        }
        return bankAccountsListInfo.toString();
    }

    /**
     * Can be used to print information about bank fees
     *
     * @return feeListInfo String format value that contains information about the fee - name and value
     */
    public String getFeeListInfo() {
        StringBuilder feeListInfo = new StringBuilder();
        if (feeList.size() == 0) {
            return "No taxes or fees included yet!";
        }
        for (Map.Entry<FeeType, Double> element : feeList.entrySet()) {
            feeListInfo.append("Fee name: " + element.getKey() + "; Value: " + element.getValue() + "\n");
        }
        return feeListInfo.toString();
    }

    /**
     * Can be used to print information about bank exchange rates
     *
     * @return exchangeRateListInfo String format value that contains information about the exchange rates - name and value
     */
    public String getExchangeRateListInfo() {
        StringBuilder exchangeRateListInfo = new StringBuilder();
        if (exchangeRatePairList.size() == 0) {
            return "No exchange rates included yet!";
        }
        for (Map.Entry<ExchangeRatePair, Double> element : exchangeRatePairList.entrySet()) {
            exchangeRateListInfo.append("Exchange rate name: " + element.getKey() + ";Exchange rate value: " + element.getValue() + "\n");
        }
        return exchangeRateListInfo.toString();
    }

    public String getTransactionListInfo(List<Transaction> transactions) {
        StringBuilder transactionListInfo = new StringBuilder();
        if (transactions.size() == 0) {
            return "No exchange transactions yet!";
        }
        for (Transaction transaction : transactions) {
            transactionListInfo.append(transaction);
        }
        return transactionListInfo.toString();
    }

    public String getBankInstitutionInfo() {
        return "Bank institution information: ----------------\n" +
                "Bank id: " + id +
                "; Bank name: " + name +
                "; Address: " + address + ";";
    }

    public String getBankInstitutionCustomerNumberInfo(List<Customer> customers) {
        return "Bank name: " + name + "; " + "Number of customers: " + customers.size();
    }

    public String getName() {
        return name;
    }

    public Map<FeeType, Double> getFeeList() {
        return Collections.unmodifiableMap(feeList);
    }

    public Map<ExchangeRatePair, Double> getExchangeRatePairList() {
        return Collections.unmodifiableMap(exchangeRatePairList);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        Validation.validateForNoName(name);
        Validation.validateForNonLetters(name);
        this.name = name;
    }

    public void setAddress(String address) {
        Validation.validateBankAddress(address);
        this.address = address;
    }
}
