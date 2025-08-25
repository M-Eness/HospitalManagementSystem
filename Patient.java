package com.mycompany.bp2project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Patient extends Person{

    // Constructer
    public Patient(String position, String ssn_number, String name, String surname, String gender, String age, String height, String weight, String password) {
        super(position, ssn_number, name, surname, gender, age, height, weight, password);
    }
    
    // Hasta nesnesinin formata uygun olup olmadığını kontrol et
     public static boolean patientCheck(Patient patient) { // hasta için kontrol yapıyor
        boolean valid = true;
        String ssnPattern = "^\\d{11}$";
        String namePattern = "^[a-zA-Z]+$";
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        String pattern = "\\d{1,3}+";

        // Deseni derleme
        Pattern patternSsn = Pattern.compile(ssnPattern);
        Pattern patternName = Pattern.compile(namePattern);
        Pattern patternAge = Pattern.compile(pattern);
        Pattern patternHeight = Pattern.compile(pattern);
        Pattern patternWeight = Pattern.compile(pattern);
        Pattern patternPassword = Pattern.compile(passwordPattern);

        // Matcher oluşturma
        Matcher matcherSsn = patternSsn.matcher(patient.getSsn_number());
        Matcher matcherName = patternName.matcher(patient.getName());
        Matcher matcherSurname = patternName.matcher(patient.getSurname());
        Matcher matcherAge = patternAge.matcher(patient.getAge());
        Matcher matcherHeight = patternHeight.matcher(patient.getHeight());
        Matcher matcherWeight = patternWeight.matcher(patient.getWeight());
        Matcher matcherPassword = patternPassword.matcher(patient.getPassword());

        // Her bir özelliğin kontrolü
        if (!matcherSsn.matches() || !matcherName.matches() || !matcherSurname.matches()
                || !matcherAge.matches() || !matcherHeight.matches()
                || !matcherWeight.matches() || !matcherPassword.matches()) {
            JOptionPane.showMessageDialog(null, "Please Enter a Valid Input!", "Error", JOptionPane.ERROR_MESSAGE);

            valid = false;
        }

        return valid;
    }

   
    
    
    
    
    
    
    
}