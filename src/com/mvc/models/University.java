package com.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class University {

    private String name;
    private String address;
    private String phoneNumber;
    private List<Escuela> escuelas;

    public University() {
        escuelas = new ArrayList<>();
    }

    public University(String pName, String pAddress, String pPhoneNumber) {
        this.name = pName;
        this.address = pAddress;
        this.phoneNumber = pPhoneNumber;
        this.escuelas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        this.name = pName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String pAddress) {
        this.address = pAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String pPhoneNumber) {
        this.phoneNumber = pPhoneNumber;
    }

    public void updateData(String pNewAddress, String pNewPhoneNumber) {
        this.address = pNewAddress;
        this.phoneNumber = pNewPhoneNumber;
    }

    public void addSchool(Escuela pNewSchool) {
        if (pNewSchool != null) {
            escuelas.add(pNewSchool);
        }
    }

    public List<Escuela> getEscuelas() {
        return escuelas;
    }

    public String toString() {
        return "University Name: " + name +
               ", Address: " + address +
               ", Phone Number: " + phoneNumber;
    }
}

