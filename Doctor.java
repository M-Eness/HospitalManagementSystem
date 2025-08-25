package com.mycompany.bp2project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Doctor extends Person {

    // Constructer oluştur.
    public Doctor(String position, String ssn, String name, String surname, String field) {
        super(position, ssn, name, surname, field);
    }
    
    // doktor objesinin formata uygun olup olmadığını kontrol et
     public static boolean doctorCheck(Doctor doctor) { // Doktor için kontrol yapıyor
        boolean valid = true;
        String ssnPattern = "^\\d{11}$";
        String namePattern = "^[a-zA-Z]+$";
        

        // Deseni derleme
        Pattern patternSsn = Pattern.compile(ssnPattern);
        Pattern patternName = Pattern.compile(namePattern);
        

        // Matcher oluşturma
        Matcher matcherSsn = patternSsn.matcher(doctor.getSsn_number());
        Matcher matcherName = patternName.matcher(doctor.getName());
        Matcher matcherSurname = patternName.matcher(doctor.getSurname());
      

        // Her bir özelliğin kontrolü
        if (!matcherSsn.matches() || !matcherName.matches() || !matcherSurname.matches()) {
            JOptionPane.showMessageDialog(null, "Please Enter a Valid Input!", "Error", JOptionPane.ERROR_MESSAGE);
            valid = false;
        }

        return valid;
    }


    

}
