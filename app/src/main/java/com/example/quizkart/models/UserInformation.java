package com.example.quizkart.models;

public class UserInformation {

    public String name;
    public String surname;
    public String phoneno;

    public UserInformation(){
    }

    public UserInformation(String name,String surname, String phoneno){
        this.name = name;
        this.surname = surname;
        this.phoneno = phoneno;
    }
    public String getUserName() {
        return name;
    }
    public String getUserSurname() {
        return surname;
    }
    public String getUserPhoneno() {
        return phoneno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
