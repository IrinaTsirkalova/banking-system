package eu.deltasource.internship.bankingsystem.repository;

import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;

import java.util.ArrayList;
import java.util.List;

public class BankInstitutionRepository {

    public static final BankInstitutionRepository bankInstitutionsRepository = new BankInstitutionRepository();
    private final List<BankInstitution> bankInstitutions = new ArrayList<>();

    public boolean doesBankInstitutionExist(String bankName) {
        return bankInstitutions.stream().anyMatch(c -> c.getName().equals(bankName));
    }

    public void addBankInstitution(BankInstitution bank) {
        if (doesBankInstitutionExist(bank.getName())) {
            throw new ElementAlreadyExistsException("This bank is already exists");
        }
        bankInstitutions.add(bank);
    }

    public void removeBankInstitution(String bankName) {
        validateBankInstitution(bankName);
        bankInstitutions.remove(getBankInstitutionByName(bankName));
    }

    public BankInstitution getBankInstitutionByName(String bankName) {
        validateBankInstitution(bankName);
        return bankInstitutions.stream().filter(b -> b.getName().equals(bankName)).findFirst().get();
    }

    public void validateBankInstitution(String bankName) {
        if (!doesBankInstitutionExist(bankName)) {
            throw new ElementDoesNotExistsException("There is no such bank");
        }
    }
}
