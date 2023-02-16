package eu.deltasource.internship.bankingsystem.exception;

/**
 * This exception is thrown when the user tries to create a new element that already exists in the repository.
 * Cases:
 * - If fee already exists
 * - Jf exchange rate already exists
 * - If bank account already exists /accounts with the same iban/
 * - If bank already exists /banks with the same name/
 * - If there is already the same customer in the repository
 */
public class ElementAlreadyExistsException extends RuntimeException {

    public ElementAlreadyExistsException(String message) {
        super(message);
    }
}
