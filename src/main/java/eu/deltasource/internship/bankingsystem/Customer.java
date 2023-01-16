package eu.deltasource.internship.bankingsystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.UUID;

public class Customer {
   private String id;
   private String fName;
   private String lName;
   private LocalDate birthdate;

   public Customer(String fName, String lName, String birthdate){
       setId();
       setFName(fName);
       setLName(lName);
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    //TO-DO date format handling: from one date format to another
       try {
           LocalDate date = LocalDate.parse(birthdate, formatter);
           setBirthdate(date);
       }catch (DateTimeParseException e){
        e.toString();
       }


   }
    public void setId() {
       id = UUID.randomUUID().toString();
    }

    public void setFName(String fName) {
        if(fName != null){
            this.fName = fName;
        }
    }

    public void setLName(String lName) {
        if (lName != null){
            this.lName = lName;
        }
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getId() {
        return id;
    }

    public String getFName() {
        return fName;
    }


    public String getLName() {
        return lName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String toString(){
       return "Customer information:-------------\n" +
               "Customer Id: " +getId() +
               "; Name: " +  getFName() + " " + getLName() +
               "; Birthdate: " + getBirthdate() + ";\n";
   }
}
