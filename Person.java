package com.mycompany.bp2project;

public class Person {

    private String position;
    private String ssn_number;
    private String name;
    private String surname;
    private String gender;
    private String age;
    private String height;
    private String weight;
    private String password;
    private String field;
    
   
    public Person(String position, String ssn_number, String name, String surname, String gender, String age, String height, String weight, String password) {
        this.position = position;
        this.ssn_number = ssn_number;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.password = password;
    }

    public Person(String position,String ssn, String name, String surname, String field) {// Doktor İçin
        this.position = position;
        this.ssn_number = ssn;
        this.name = name;
        this.surname = surname;
        this.field = field;
    }

    public Person(String ssn_number, String password) {// Login ekranı için
        
        this.ssn_number = ssn_number;
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSsn_number() {
        return ssn_number;
    }

    public void setSsn_number(String ssn_number) {
        this.ssn_number = ssn_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
    
    

    

}
