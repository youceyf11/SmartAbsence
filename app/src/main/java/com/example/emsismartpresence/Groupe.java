package com.example.emsismartpresence;

import java.util.List;

public class Groupe {
    private String nom;
    private List<Student> etudiants;

    public Groupe(String nom, List<Student> etudiants) {
        this.nom = nom;
        this.etudiants = etudiants;
    }

    public String getNom() {
        return nom;
    }

    public List<Student> getEtudiants() {
        return etudiants;
    }
}