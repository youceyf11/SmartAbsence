package com.example.emsismartpresence;

import java.util.List;

public class Centre {
    private String nom;
    private List<Groupe> groupes;

    public Centre(String nom, List<Groupe> groupes) {
        this.nom = nom;
        this.groupes = groupes;
    }

    public String getNom() {
        return nom;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }
}