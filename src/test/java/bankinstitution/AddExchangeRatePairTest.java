package bankinstitution;

import eu.deltasource.internship.bankingsystem.enums.ExchangeRatePair;
import eu.deltasource.internship.bankingsystem.exception.ElementAlreadyExistsException;
import eu.deltasource.internship.bankingsystem.exception.ElementDoesNotExistsException;
import eu.deltasource.internship.bankingsystem.exception.InvalidValueInputException;
import eu.deltasource.internship.bankingsystem.factory.BankInstitutionFactory;
import eu.deltasource.internship.bankingsystem.model.BankInstitution;
import eu.deltasource.internship.bankingsystem.repository.BankInstitutionRepository;
import eu.deltasource.internship.bankingsystem.service.BankInstitutionService;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests:
 * - if the user can successfully add exchange rate in bank institution that exists
 * - if the user cannot add exchange rate in bank institution that not exists
 * - if the user cannot add already existing exchange rate
 * - if the user cannot add an exchange rate without a pair
 * - if the user cannot add a exchange rate with value below 0.01
 */
public class AddExchangeRatePairTest {

    @Test
    public void should_AddExchangeRate_ToBankInstitution() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.EUR_BGN, 0.50);

        //Then
        BankInstitution bankInRepository = BankInstitutionRepository.bankInstitutionsRepository.getBankInstitutionByName("DSK");
        assertTrue(bankInRepository.getExchangeRatePairList().containsKey(ExchangeRatePair.EUR_BGN));
        bankInstitutionService.removeExchangeRate("DSK", ExchangeRatePair.EUR_BGN);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void addExchangeRate_ShouldThrowRuntimeException_IfBankInstitutionInNotInRepository() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();

        //When
        RuntimeException exception = assertThrows(ElementDoesNotExistsException.class,
                () -> bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.EUR_BGN, 0.50));

        //Then
        assertTrue(exception.getMessage().contentEquals("There is no such bank"));
    }

    @Test
    public void addExchangeRate_ShouldThrowRuntimeException_IfBankInstitutionAlreadyHasIt() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.EUR_BGN, 0.50);
        RuntimeException exception = assertThrows(ElementAlreadyExistsException.class,
                () -> bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.EUR_BGN, 0.50));

        //Then
        assertTrue(exception.getMessage().contentEquals("This pair already exists!"));
        bankInstitutionService.removeExchangeRate("DSK", ExchangeRatePair.EUR_BGN);
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void addExchangeRate_ShouldThrowRuntimeException_IfExchangeRatePairIsNull() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> bankInstitutionService.addNewExchangeRate("DSK", null, 0.50));

        //Then
        assertTrue(exception.getMessage().contentEquals("Please enter an exchange rate pair!"));
        bankInstitutionService.removeBankInstitution("DSK");
    }

    @Test
    public void addExchangeRate_ShouldThrowRuntimeException_IfExchangeRateValueIs0() {
        //Given
        BankInstitutionFactory bankInstitutionFactory = new BankInstitutionFactory();
        BankInstitution bank = bankInstitutionFactory.createBankInstitution("DSK", "Vasil Aprilov");
        BankInstitutionService bankInstitutionService = new BankInstitutionService();
        bankInstitutionService.addNewBankInstitution(bank);

        //When
        RuntimeException exception = assertThrows(InvalidValueInputException.class,
                () -> bankInstitutionService.addNewExchangeRate("DSK", ExchangeRatePair.EUR_BGN, 0.0));

        //Then
        assertTrue(exception.getMessage().contentEquals("Please enter an exchange rate value that is at least 0.01!"));
        bankInstitutionService.removeBankInstitution("DSK");
    }
}
