package com.example.progettotnvandroid.model;

import java.util.Date;

public class Utente {
    private int id;
    private String username;
    private String password;
    private String citta;
    private Date dataNascita;
    private boolean isAdmin;

    public Utente() {
    }

    public Utente(String username, String password, String citta, Date dataNascita, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.citta = citta;
        this.dataNascita = dataNascita;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}