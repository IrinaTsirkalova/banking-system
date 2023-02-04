package eu.deltasource.internship.bankingsystem;

import eu.deltasource.internship.bankingsystem.enums.ExchangeRatePair;
import eu.deltasource.internship.bankingsystem.enums.FeeType;
import eu.deltasource.internship.bankingsystem.exception.*;

import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static void validateForNoName(String name) {
        if (name.isBlank()) {
            throw new InvalidValueInputException("Please enter a name!");
        }
    }

    public static void validateForNonLetters(String name) {
        Pattern nonLettersPattern = Pattern.compile("[^a-zA-Z]");
        Matcher nonLettersNameMatcher = nonLettersPattern.matcher(name);
        if (nonLettersNameMatcher.find()) {
            throw new InvalidValueInputException("The name you have entered contains a non-letter characters. Please enter a valid name!");
        }
    }

    public static void validateCorrectDate(int day, int month, int year) {
        if ((day < 1 || day >= 31) || (month < 1 || month >= 12) || (year < 1920 || year >= 2013)) {
            throw new InvalidBirthdateInputException("The date you have entered is not valid! Please enter a valid date!");
        }
    }

    public static void validateFebruaryDays(int day, int month, int year) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (month == 2 && day > 29) {
            throw new InvalidBirthdateInputException("February can not have 30 days!");
        } else if (month == 2 && !calendar.isLeapYear(year) && day > 28) {
            throw new InvalidBirthdateInputException("February can not have 29 days! It's not a leap year");
        }
    }

    public static void validateBankAddress(String address) {
        if (address.isBlank()) {
            throw new InvalidValueInputException("Please enter an address for the bank!");
        }
    }

    public static void validateFeeInputName(FeeType feeTypeName) {
        if (feeTypeName == null) {
            throw new InvalidValueInputException("Please enter a fee type");
        }
    }

    public static void validateFeeInputValue(Double feeValue) {
        if (feeValue < 0.1) {
            throw new InvalidValueInputException("Please enter a value that is at least 0.1!");
        }
    }

    public static void validateExchangeRatePairInputName(ExchangeRatePair exchangeRatePair) {
        if (exchangeRatePair == null) {
            throw new InvalidValueInputException("Please enter an exchange rate pair!");
        }
    }

    public static void validateExchangeRatePairInputValue(Double value) {
        if (value < 0.01) {
            throw new InvalidValueInputException("Please enter an exchange rate value that is at least 0.01!");
        }
    }

}
