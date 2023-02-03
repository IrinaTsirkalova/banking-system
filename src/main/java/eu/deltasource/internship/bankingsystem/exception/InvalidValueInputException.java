package eu.deltasource.internship.bankingsystem.exception;

/**
 * This exception is thrown when user enters incorrect values
 * Cases:
 * - the amount he wants to withdraw or transfer is too high
 * - while creating transfer transaction one of the accounts' type is savings
 * - whenever he doesn't enter information
 */
public class InvalidValueInputException extends RuntimeException {
    public InvalidValueInputException(String message){
        super(message);
    }
}
