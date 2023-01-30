package eu.deltasource.internship.bankingsystem;

import java.time.LocalDate;

public class CustomerModel {
    private String id;
    private String fName;
    private String lName;
    private LocalDate birthdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
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
