package eu.deltasource.internship.bankingsystem.exception;

/**
 * This exception is thrown when the user enters incorrect date while creating customer,
 * e.g. day is zero, below zero or above 31/ above 28 for February in non leap year/,
 * month is zero, below zero or above 12
 * year is below 1920 or above 2013
 */
public class InvalidBirthdateInputException extends RuntimeException {

    public InvalidBirthdateInputException(String message) {
        super(message);
    }
}
