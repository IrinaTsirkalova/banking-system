package eu.deltasource.internship.bankingsystem.exception;

public class BlankInputException extends Exception{

    public BlankInputException(){
        super();
    }
    public BlankInputException(String message){
        super(message);
    }
}
