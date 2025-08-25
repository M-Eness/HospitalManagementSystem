package com.mycompany.bp2project;

import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DatabaseManager {

    // sql bağlantısı için url
    private final static String url = "Database URL";
    // sql bağlantısı için username
    private final static String username = "Database Username";
    // sql bağlantısı için şifre
    private final static String password = "Database Password";
  
    public static Connection getConnection() throws SQLException { // Database bağlantı kuran method
        return DriverManager.getConnection(url, username, password);
    }

    public static boolean login(String ssn, String password) {
        //login işlemini gerçekleştire method 
        boolean exist = false;
        try {
            Connection connection = getConnection(); 
            String query = "SELECT * FROM person WHERE Ssn = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ssn);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return exist = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return exist;
    }

    public static boolean registerPatient(Patient patient) {// HASTA İÇİN
        // Hasta kayıt eden method
        boolean isAdd = false;
        if (Patient.patientCheck(patient)) { // patientCheck Methodu girilen bilgilerin formata uyumlu olup olmadıpını kontrol eder.
            try {
                
                Connection connection = getConnection(); 
                String query = "SELECT * FROM person WHERE Ssn = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, patient.getSsn_number());
                ResultSet resultSet = preparedStatement.executeQuery();
                // Girilen ssn mevcutsa resultSet true dönecektir.
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "This SSN is already exist", "", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Eğer değilse yeni hasta ekleme işlemi gerçekleşecektir.
                    String insertQuery = "INSERT INTO person (position, Ssn, name, surname, gender, age, height, weight, password) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setString(1, patient.getPosition());
                    insertStatement.setString(2, patient.getSsn_number());
                    insertStatement.setString(3, patient.getName());
                    insertStatement.setString(4, patient.getSurname());
                    insertStatement.setString(5, patient.getGender());

                    insertStatement.setString(6, patient.getAge());
                    insertStatement.setString(7, patient.getHeight());
                    insertStatement.setString(8, patient.getWeight());
                    insertStatement.setString(9, patient.getPassword());

                    insertStatement.executeUpdate();
                    
                    // Bağlantıları kapatma işlemleri.
                    preparedStatement.close();
                    connection.close();
                    JOptionPane.showMessageDialog(null, "Person is added successfully", "", JOptionPane.INFORMATION_MESSAGE);
                    isAdd = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return isAdd;
    }

    public static void registerDoctor(Doctor doctor) {// Doktor kaydetme methodu
        
        if (Doctor.doctorCheck(doctor)) { //gelen doktor objesinin formata uygun olup olmadığını kontrol eder.
            try {
                // query for registering
                Connection connection = getConnection(); // getConnection() metodunuzu burada çağırarak bağlantıyı alın
                String query = "SELECT * FROM person WHERE Ssn = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, doctor.getSsn_number());
                ResultSet resultSet = preparedStatement.executeQuery();
                // Girilen ssn mevcutsa resultSet true dönecektir.
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "This SSN is already exist", "", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Değilse doktor kaydedilecektir.
                    String insertQuery = "INSERT INTO person (position, Ssn, name, surname, field) "
                            + "VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                    insertStatement.setString(1, doctor.getPosition());
                    insertStatement.setString(2, doctor.getSsn_number());
                    insertStatement.setString(3, doctor.getName());
                    insertStatement.setString(4, doctor.getSurname());
                    insertStatement.setString(5, doctor.getField());

                    insertStatement.executeUpdate();
                    
                    // Bağlantıları kapatma işlemleri

                    preparedStatement.close();
                    connection.close();
                    JOptionPane.showMessageDialog(null, "Person is added successfully", "", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String whoIsThis(Person person) {
    // bu method içine aldığı person objesinin hasta mı yoksa admin mi olduğunu kontrol eder.
    // bu method sayesinde tek login ekranından hem admin hem hasta sayfasına geçiş yapabiliyoruz.
        String position = null;
        try {
            Connection connection = getConnection();
            String query = "SELECT position, password FROM person WHERE Ssn = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, person.getSsn_number());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (resultSet.getString("position").equals("admin")) {
                    return position = "admin";
                } else if (resultSet.getString("position").equals("Patient")) {
                    return position = "Patient";
                }
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }

   

    public static boolean checkAlreadyExists(JComboBox comboBox, String targetString) {
        // Bu method parametre olarak aldığı stringi yine parametre olarak aldığı combobox nesnesinin içinde olup olmadığını kontrol eder.
        // Böylelikle combobox a tekrarlı veri yüklemenin önüne geçilmiş olur.

        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            String item = model.getElementAt(i);
            if (item.equals(targetString)) {
                return true; 
            }
        }

        return false; 
    }

    public static void randevuEkle(JComboBox comboTime, JComboBox comboField, JComboBox comboDoctor, Person patient, JDateChooser calendar) {
        // Bu method randevu eklemek için gereken nesneleri parametre olarak alır ve olası randevu çakışmalarını kontrol eder.
        // Eğer herhangi bir sıkıntı yoksa randevu eklenir eğer varsa kullanıcıya hata mesajı gösterilir. 
        
        java.util.Date date = calendar.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String selectedDate = sdf.format(date);
        String selectedTime = comboTime.getSelectedItem().toString();
        String selectedField = comboField.getSelectedItem().toString();
        String selectedDoctor = comboDoctor.getSelectedItem().toString();
        try {
            Connection connection = getConnection();

            // Aynı doktorun aynı tarih ve saatte randevusu olup olmadığını kontrol et
            String queryDoctor = "SELECT * FROM randevu WHERE doctorName = ? AND date = ? AND hour = ?";
            PreparedStatement preparedStatementDoctor = connection.prepareStatement(queryDoctor);
            preparedStatementDoctor.setString(1, selectedDoctor);
            preparedStatementDoctor.setString(2, selectedDate);
            preparedStatementDoctor.setString(3, selectedTime);
            ResultSet resultSetDoctor = preparedStatementDoctor.executeQuery();

            if (resultSetDoctor.next()) {
                JOptionPane.showMessageDialog(null, "This doctor has another appointment at the specified date and time.", "Conflict Error", JOptionPane.ERROR_MESSAGE);
                resultSetDoctor.close();
                preparedStatementDoctor.close();
                connection.close();
                return;
            }

            // Aynı hastanın aynı tarih ve saatte başka randevusu olup olmadığını kontrol et
            String queryPatient = "SELECT * FROM randevu WHERE patientSsn = ? AND date = ? AND hour = ?";
            PreparedStatement preparedStatementPatient = connection.prepareStatement(queryPatient);
            preparedStatementPatient.setString(1, patient.getSsn_number());
            preparedStatementPatient.setString(2, selectedDate);
            preparedStatementPatient.setString(3, selectedTime);
            ResultSet resultSetPatient = preparedStatementPatient.executeQuery();

            if (resultSetPatient.next()) {
                JOptionPane.showMessageDialog(null, "You have another appointment at the specified date and time.", "Conflict Error", JOptionPane.ERROR_MESSAGE);
                resultSetPatient.close();
                preparedStatementPatient.close();
                connection.close();
                return;
            }

            // Randevuyu ekle
            String insertQuery = "INSERT INTO randevu (patientSsn, patientName, patientSurname, field, doctorName, date, hour) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementInsert = connection.prepareStatement(insertQuery);
            preparedStatementInsert.setString(1, patient.getSsn_number());
            preparedStatementInsert.setString(2, patient.getName());
            preparedStatementInsert.setString(3, patient.getSurname());
            preparedStatementInsert.setString(4, selectedField);
            preparedStatementInsert.setString(5, selectedDoctor);
            preparedStatementInsert.setString(6, selectedDate);
            preparedStatementInsert.setString(7, selectedTime);
            preparedStatementInsert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Your appointment has been added successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);

            preparedStatementInsert.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while adding an appointment.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void randevuGöster(String ssn, JTable table) {
        // Hasta frameinde hastanın randevularını gösteren method.
        try {
            Connection connection = getConnection();
            String query = "SELECT patientSsn, patientName, patientSurname, field, doctorName, date, hour FROM randevu WHERE patientSsn = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, ssn);
            ResultSet resultSet = preparedStatement.executeQuery();

            // DefaultTableModel oluştur ve sütunları ekle
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("SSN");
            model.addColumn("Ad");
            model.addColumn("Soyad");
            model.addColumn("Alan");
            model.addColumn("Doktor");
            model.addColumn("Tarih");
            model.addColumn("Saat");

            // Sonuçları tablo modeline ekle
            while (resultSet.next()) {
                String patientSsn = resultSet.getString("patientSsn");
                String patientName = resultSet.getString("patientName");
                String patientSurname = resultSet.getString("patientSurname");
                String field = resultSet.getString("field");
                String doctorName = resultSet.getString("doctorName");
                String date = resultSet.getString("date");
                String hour = resultSet.getString("hour");

                model.addRow(new Object[]{patientSsn, patientName, patientSurname, field, doctorName, date, hour});
            }

            table.setModel(model);

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while loading appointments.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void randevuGöster(JTable table) {
        // Admin frameinde tüm hastaların randevularını gösteren method
        try {
            Connection connection = getConnection();
            String query = "SELECT patientSsn, patientName, patientSurname, field, doctorName, date, hour FROM randevu ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // DefaultTableModel oluştur ve sütunları ekle
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("SSN");
            model.addColumn("Ad");
            model.addColumn("Soyad");
            model.addColumn("Alan");
            model.addColumn("Doktor");
            model.addColumn("Tarih");
            model.addColumn("Saat");

            // Sonuçları tablo modeline ekle
            while (resultSet.next()) {
                String patientSsn = resultSet.getString("patientSsn");
                String patientName = resultSet.getString("patientName");
                String patientSurname = resultSet.getString("patientSurname");
                String field = resultSet.getString("field");
                String doctorName = resultSet.getString("doctorName");
                String date = resultSet.getString("date");
                String hour = resultSet.getString("hour");

                model.addRow(new Object[]{patientSsn, patientName, patientSurname, field, doctorName, date, hour});
            }

            table.setModel(model);

            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while loading appointments.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
