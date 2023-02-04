package eu.deltasource.internship.bankingsystem.model;

import eu.deltasource.internship.bankingsystem.Validation;

import java.time.LocalDate;

public class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        Validation.validateForNoName(firstName);
        Validation.validateForNonLetters(firstName);
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        Validation.validateForNoName(lastName);
        Validation.validateForNonLetters(lastName);
        this.lastName = lastName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String toString() {
        return "Customer information:-------------\n" +
                "Customer Id: " + id +
                "; Name: " + firstName + " " + lastName +
                "; Birthdate: " + birthdate + ";\n";
    }
}
