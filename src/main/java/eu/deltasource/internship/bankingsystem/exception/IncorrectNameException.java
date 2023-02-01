package eu.deltasource.internship.bankingsystem.exception;

public class IncorrectNameException extends Exception{

    public IncorrectNameException(){
        super();
    }
    public IncorrectNameException(String message){
        super(message);
    }
}

