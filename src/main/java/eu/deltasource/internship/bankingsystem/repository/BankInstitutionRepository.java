package eu.deltasource.internship.bankingsystem.repository;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;

import java.util.ArrayList;
import java.util.List;

public class BankInstitutionRepository {
    public static final BankInstitutionRepository bankInstitutionsRepository = new BankInstitutionRepository();
    private final List<BankInstitution> bankInstitutions = new ArrayList<>();

    public boolean doesBankExist(String name) {
        return bankInstitutions.stream().anyMatch(c -> c.getName().equals(name));
    }

    public void addBank(BankInstitution bank) {
        if (doesBankExist(bank.getName())) {
            throw new ElementAlreadyExistsException("This bank is already in the database");
        }
        bankInstitutions.add(bank);
    }

    public BankInstitution getBankInstitutionByName(String name) {
        if (!doesBankExist(name)) {
            throw new ElementDoesNotExistsException("There is no such bank");
        }
        return bankInstitutions.stream().filter(b -> b.getName().equals(name)).findFirst().get();
    }
}
