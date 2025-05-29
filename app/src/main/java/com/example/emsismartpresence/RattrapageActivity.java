package com.example.emsismartpresence;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RattrapageActivity extends AppCompatActivity {

    private Spinner spinnerCentresRattrapage, spinnerStatutRattrapage;
    private Button btnFiltrer;
    private TextView txtCountTotal, txtCountPending, txtCountCompleted;
    private RecyclerView recyclerRattrapages;
    private FloatingActionButton fabAddRattrapage;

    private RattrapageAdapter rattrapageAdapter;
    private List<RattrapageItem> rattrapageList;
    private List<RattrapageItem> filteredList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rattrapage);

        initViews();
        setupSpinners();
        setupRecyclerView();
        setupClickListeners();
        loadRattrapageData();
        updateStatistics();
    }

    private void initViews() {
        spinnerCentresRattrapage = findViewById(R.id.spinner_centres_rattrapage);
        spinnerStatutRattrapage = findViewById(R.id.spinner_statut_rattrapage);
        btnFiltrer = findViewById(R.id.btn_filtrer);
        txtCountTotal = findViewById(R.id.txt_count_total);
        txtCountPending = findViewById(R.id.txt_count_pending);
        txtCountCompleted = findViewById(R.id.txt_count_completed);
        recyclerRattrapages = findViewById(R.id.recycler_rattrapages);
        fabAddRattrapage = findViewById(R.id.fab_add_rattrapage);
    }

    private void setupSpinners() {
        // Setup Centre Spinner
        List<String> centres = Arrays.asList("Tous les centres", "Centre Casablanca", "Centre Rabat", "Centre Marrakech");
        ArrayAdapter<String> centreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, centres);
        centreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCentresRattrapage.setAdapter(centreAdapter);

        // Setup Statut Spinner
        List<String> statuts = Arrays.asList("Tous les statuts", "En attente", "Confirmé", "Terminé", "Annulé");
        ArrayAdapter<String> statutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuts);
        statutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatutRattrapage.setAdapter(statutAdapter);
    }

    private void setupRecyclerView() {
        rattrapageList = new ArrayList<>();
        filteredList = new ArrayList<>();
        rattrapageAdapter = new RattrapageAdapter(filteredList, this);
        recyclerRattrapages.setLayoutManager(new LinearLayoutManager(this));
        recyclerRattrapages.setAdapter(rattrapageAdapter);
    }

    private void setupClickListeners() {
        btnFiltrer.setOnClickListener(v -> applyFilters());

        fabAddRattrapage.setOnClickListener(v -> {
            Toast.makeText(this, "Ajouter un nouveau rattrapage", Toast.LENGTH_SHORT).show();
            // TODO: Implement add rattrapage functionality
        });

        spinnerCentresRattrapage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerStatutRattrapage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadRattrapageData() {
        // Sample data - replace with actual data loading from database/API
        rattrapageList.clear();

        rattrapageList.add(new RattrapageItem(
                "Mathématiques",
                "15 Juin 2024 - 14:00 à 16:00",
                "Salle A101 - Centre Casablanca",
                "Groupe: 2ème année - Section A",
                "En attente",
                "Centre Casablanca"
        ));

        rattrapageList.add(new RattrapageItem(
                "Physique",
                "18 Juin 2024 - 10:00 à 12:00",
                "Salle B205 - Centre Rabat",
                "Groupe: 1ère année - Section B",
                "Confirmé",
                "Centre Rabat"
        ));

        rattrapageList.add(new RattrapageItem(
                "Informatique",
                "20 Juin 2024 - 16:00 à 18:00",
                "Lab Info 1 - Centre Casablanca",
                "Groupe: 3ème année - Section C",
                "Terminé",
                "Centre Casablanca"
        ));

        rattrapageList.add(new RattrapageItem(
                "Chimie",
                "22 Juin 2024 - 08:00 à 10:00",
                "Salle C301 - Centre Marrakech",
                "Groupe: 2ème année - Section A",
                "En attente",
                "Centre Marrakech"
        ));

        rattrapageList.add(new RattrapageItem(
                "Anglais",
                "25 Juin 2024 - 14:00 à 15:30",
                "Salle D102 - Centre Rabat",
                "Groupe: 1ère année - Section A",
                "Confirmé",
                "Centre Rabat"
        ));

        applyFilters();
    }

    private void applyFilters() {
        String selectedCentre = spinnerCentresRattrapage.getSelectedItem().toString();
        String selectedStatut = spinnerStatutRattrapage.getSelectedItem().toString();

        filteredList.clear();

        for (RattrapageItem item : rattrapageList) {
            boolean matchesCentre = selectedCentre.equals("Tous les centres") ||
                    item.getCentre().equals(selectedCentre);
            boolean matchesStatut = selectedStatut.equals("Tous les statuts") ||
                    item.getStatut().equals(selectedStatut);

            if (matchesCentre && matchesStatut) {
                filteredList.add(item);
            }
        }

        rattrapageAdapter.notifyDataSetChanged();
        updateStatistics();
    }

    private void updateStatistics() {
        int total = filteredList.size();
        int enAttente = 0;
        int termines = 0;

        for (RattrapageItem item : filteredList) {
            if (item.getStatut().equals("En attente")) {
                enAttente++;
            } else if (item.getStatut().equals("Terminé")) {
                termines++;
            }
        }

        txtCountTotal.setText(String.valueOf(total));
        txtCountPending.setText(String.valueOf(enAttente));
        txtCountCompleted.setText(String.valueOf(termines));
    }

    // Inner class for Rattrapage data model
    public static class RattrapageItem {
        private String matiere;
        private String dateHeure;
        private String salle;
        private String groupe;
        private String statut;
        private String centre;

        public RattrapageItem(String matiere, String dateHeure, String salle, String groupe, String statut, String centre) {
            this.matiere = matiere;
            this.dateHeure = dateHeure;
            this.salle = salle;
            this.groupe = groupe;
            this.statut = statut;
            this.centre = centre;
        }

        // Getters
        public String getMatiere() { return matiere; }
        public String getDateHeure() { return dateHeure; }
        public String getSalle() { return salle; }
        public String getGroupe() { return groupe; }
        public String getStatut() { return statut; }
        public String getCentre() { return centre; }

        // Setters
        public void setStatut(String statut) { this.statut = statut; }
    }
}
