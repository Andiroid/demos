package com.demo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;


public class Patient {

    private int id;
    private int ssn;
    private String address;
    @DateTimeFormat(pattern="dd.MM.yyyy")
    private LocalDate birthday;
    private String gender;
    private String firstname;
    private String lastname;
    private String insurance;

    public Patient(){

    }

    public Patient(int id, int ssn, String address, LocalDate birthday, String gender, String firstname, String lastname, String insurance) {
        this.id = id;
        this.ssn = ssn;
        this.address = address;
        this.birthday = birthday;
        this.gender = gender;
        this.firstname = firstname;
        this.lastname = lastname;
        this.insurance = insurance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", ssn=" + ssn +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", gender='" + gender + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", insurance='" + insurance + '\'' +
                '}';
    }
}
