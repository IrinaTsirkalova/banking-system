package eu.deltasource.internship.bankingsystem.exception;

/**
 * This exception is thrown when the user tries to get element that is not present in the repository
 * Cases:
 * - If the bank account doesn't exist
 * - If the bank doesn't exist
 */
public class ElementDoesNotExistsException extends RuntimeException {

    public ElementDoesNotExistsException(String message) {
        super(message);
    }
}
