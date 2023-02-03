package eu.deltasource.internship.bankingsystem.factory;

import eu.deltasource.internship.bankingsystem.model.BankInstitution;

import java.util.UUID;

public class BankInstitutionFactory {
    public BankInstitution createBankInstitution(String name, String address) {
        BankInstitution bankInstitution = new BankInstitution();
        bankInstitution.setId(UUID.randomUUID().toString());
        bankInstitution.setName(name);
        bankInstitution.setAddress(address);
        return bankInstitution;
    }
}
