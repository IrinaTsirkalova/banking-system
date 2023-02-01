package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.exception.BlankInputException;
import eu.deltasource.internship.bankingsystem.exception.IncorrectNameException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Models a customer that has id, first name, last name and birthdate
 */
public class CustomerModel {

    private String id;
    private String fName;
    private String lName;
    private LocalDate birthdate;

    public void setId(String id) {
        this.id = id;
    }

    public void setFName(String fName) throws IncorrectNameException, BlankInputException {
        if(fName.equals("")){
            throw new BlankInputException();
        }

        Pattern nonLettersPattern = Pattern.compile("[^a-zA-Z]");
        Matcher nonLettersNameMatcher = nonLettersPattern.matcher(fName);
        if(nonLettersNameMatcher.find()){
            throw  new IncorrectNameException();
        }

        this.fName = fName;
    }

    public void setLName(String lName) throws BlankInputException, IncorrectNameException {
        if(lName.equals("")){
            throw new BlankInputException();
        }
        Pattern nonLettersPattern = Pattern.compile("[^a-zA-Z]");
        Matcher nonLettersNameMatcher = nonLettersPattern.matcher(lName);
        if(nonLettersNameMatcher.find()){
            throw  new IncorrectNameException();
        }
        this.lName = lName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String toString(){
        return "Customer information:-------------\n" +
                "Customer Id: " + id +
                "; Name: " +  fName + " " + lName +
                "; Birthdate: " + birthdate + ";\n";
    }
}
