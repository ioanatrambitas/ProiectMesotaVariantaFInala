package com.example.proiectmesotavariantafinala;

public class ElevAdaptor {

    private String Name;
    private String CNP;
    private String Email;
    private String Password;



    public ElevAdaptor(String name, String cnp, String email, String password) {
        Name = name;
        CNP = cnp;
        Email = email;
        Password = password;
    }

    public ElevAdaptor() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String cnp) {
        CNP = cnp;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}