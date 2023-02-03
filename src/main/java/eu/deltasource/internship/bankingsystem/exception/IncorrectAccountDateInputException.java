package eu.deltasource.internship.bankingsystem.exception;

/**
 * This exception is thrown when the user enters incorrect date while creating customer,
 * e.g. day is zero, below zero or above 31/ above 28 for February/,
 * month is zero, below zero or above 12
 * year is below 1920 or above 2013
 */
public class IncorrectAccountDateInputException extends RuntimeException {

    public IncorrectAccountDateInputException(String message) {
        super(message);
    }
}
