package eu.deltasource.internship.bankingsystem.exception;

public class IncorrectDateInputException extends Exception {

    public IncorrectDateInputException(){
        super();
    }
    public IncorrectDateInputException(String message){
        super(message);
    }
}
