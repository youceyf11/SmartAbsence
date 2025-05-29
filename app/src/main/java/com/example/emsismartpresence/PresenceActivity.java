package com.example.emsismartpresence;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PresenceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PresenceAdapter adapter;
    private Button btnSubmit;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Spinner spinnerCentres;
    private Spinner spinnerGroupes;
    private List<Centre> centres;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        spinnerCentres = findViewById(R.id.spinner_centres);
        spinnerGroupes = findViewById(R.id.spinner_groupes);
        recyclerView = findViewById(R.id.recycler_presence);
        btnSubmit = findViewById(R.id.btn_submit_presence);

        centres = new ArrayList<>();

        // --- EMSI Moulay Youssef ---
        List<Groupe> groupesRoudani = Arrays.asList(
                new Groupe("Groupe 1", Arrays.asList(
                        new Student("Amine Amrani"),
                        new Student("Chaimaa Kaddouri"),
                        new Student("Ferdaous Idrissi"),
                        new Student("Reda Et-tahri"),
                        new Student("Mehdi Dexter"),
                        new Student("Imad laqsir")
                )),
                new Groupe("Groupe 2", Arrays.asList(
                        new Student("Hicham Massfiwi"),
                        new Student("Nadia El Malki"),
                        new Student("Khalid Amrani"),
                        new Student("Fatima Zahra Lahlou"),
                        new Student("Siham El Idrissi"),
                        new Student("Rania El Khatib")
                )),
                new Groupe("Groupe 3", Arrays.asList(
                        new Student("Soufiane El Alaoui"),
                        new Student("Meryem El Yousfi"),
                        new Student("Hamza El Mansouri"),
                        new Student("Anas Bouzid"),
                        new Student("Youssef El Gharbi"),
                        new Student("Aya Benjelloun")
                )),
                new Groupe("Groupe 4", Arrays.asList(
                        new Student("Zakaria El Hachimi"),
                        new Student("Lina El Fadili"),
                        new Student("Reda El Alaoui"),
                        new Student("Sanae El Amrani"),
                        new Student("Othmane El Kabbaj"),
                        new Student("Nada El Ghazali")
                )),
                new Groupe("Groupe 5", Arrays.asList(
                        new Student("Walid El Idrissi"),
                        new Student("Ilham El Fassi"),
                        new Student("Mehdi Benali"),
                        new Student("Rim El Malki"),
                        new Student("Younes El Amrani"),
                        new Student("Hajar El Alaoui")
                )),
                new Groupe("Groupe 6", Arrays.asList(
                        new Student("Samir El Gharbi"),
                        new Student("Mouna El Khatib"),
                        new Student("Karim El Fadili"),
                        new Student("Siham Benjelloun"),
                        new Student("Adil El Ghazali"),
                        new Student("Sofia El Idrissi")
                ))
        );
        centres.add(new Centre("EMSI Roudani", groupesRoudani));

        // --- EMSI Centre 1 ---
        List<Groupe> groupesCentre1 = Arrays.asList(
                new Groupe("Groupe 1", Arrays.asList(
                        new Student("Amine Bouziane"),
                        new Student("Leila Chraibi"),
                        new Student("Oussama El Hadi"),
                        new Student("Nisrine El Fassi"),
                        new Student("Tarik El Alaoui"),
                        new Student("Siham El Ghazali")
                )),
                new Groupe("Groupe 2", Arrays.asList(
                        new Student("Youssef Benjelloun"),
                        new Student("Rania El Amrani"),
                        new Student("Omar El Fadili"),
                        new Student("Imane El Idrissi"),
                        new Student("Walid El Khatib"),
                        new Student("Sara El Gharbi")
                )),
                new Groupe("Groupe 3", Arrays.asList(
                        new Student("Hicham El Alaoui"),
                        new Student("Meryem El Fadili"),
                        new Student("Hamza Benali"),
                        new Student("Sanae El Ghazali"),
                        new Student("Zakaria El Amrani"),
                        new Student("Nada El Idrissi")
                )),
                new Groupe("Groupe 4", Arrays.asList(
                        new Student("Reda El Fassi"),
                        new Student("Lina El Kabbaj"),
                        new Student("Mehdi El Alaoui"),
                        new Student("Aya El Ghazali"),
                        new Student("Yassine El Gharbi"),
                        new Student("Fatima Zahra El Amrani")
                )),
                new Groupe("Groupe 5", Arrays.asList(
                        new Student("Othmane Benali"),
                        new Student("Ilham El Fadili"),
                        new Student("Karim El Alaoui"),
                        new Student("Rim El Ghazali"),
                        new Student("Younes El Khatib"),
                        new Student("Hajar El Gharbi")
                )),
                new Groupe("Groupe 6", Arrays.asList(
                        new Student("Samir El Fassi"),
                        new Student("Mouna El Alaoui"),
                        new Student("Khalid El Ghazali"),
                        new Student("Siham Benjelloun"),
                        new Student("Adil El Amrani"),
                        new Student("Sofia El Fadili")
                ))
        );
        centres.add(new Centre("EMSI Centre 1", groupesCentre1));

        // --- EMSI Centre 2 ---
        List<Groupe> groupesCentre2 = Arrays.asList(
                new Groupe("Groupe 1", Arrays.asList(
                        new Student("Yassine El Ghazali"),
                        new Student("Sara El Fadili"),
                        new Student("Omar Benali"),
                        new Student("Imane El Khatib"),
                        new Student("Mohamed El Alaoui"),
                        new Student("Salma El Amrani")
                )),
                new Groupe("Groupe 2", Arrays.asList(
                        new Student("Hicham El Fassi"),
                        new Student("Nadia El Gharbi"),
                        new Student("Khalid El Kabbaj"),
                        new Student("Fatima Zahra El Fadili"),
                        new Student("Anas El Alaoui"),
                        new Student("Rania Benjelloun")
                )),
                new Groupe("Groupe 3", Arrays.asList(
                        new Student("Soufiane El Amrani"),
                        new Student("Meryem El Ghazali"),
                        new Student("Hamza El Fadili"),
                        new Student("Siham El Khatib"),
                        new Student("Youssef El Alaoui"),
                        new Student("Aya El Fassi")
                )),
                new Groupe("Groupe 4", Arrays.asList(
                        new Student("Zakaria El Gharbi"),
                        new Student("Lina El Amrani"),
                        new Student("Reda El Fadili"),
                        new Student("Sanae El Kabbaj"),
                        new Student("Othmane El Fassi"),
                        new Student("Nada El Alaoui")
                )),
                new Groupe("Groupe 5", Arrays.asList(
                        new Student("Walid El Ghazali"),
                        new Student("Ilham El Amrani"),
                        new Student("Mehdi El Fadili"),
                        new Student("Rim El Khatib"),
                        new Student("Younes El Fassi"),
                        new Student("Hajar El Alaoui")
                )),
                new Groupe("Groupe 6", Arrays.asList(
                        new Student("Samir El Amrani"),
                        new Student("Mouna El Fadili"),
                        new Student("Karim El Ghazali"),
                        new Student("Siham El Alaoui"),
                        new Student("Adil El Fassi"),
                        new Student("Sofia El Kabbaj")
                ))
        );
        centres.add(new Centre("EMSI Centre 2", groupesCentre2));

        // --- EMSI Les Orangers ---
        List<Groupe> groupesOrangers = Arrays.asList(
                new Groupe("Groupe 1", Arrays.asList(
                        new Student("Amine El Fadili"),
                        new Student("Leila El Ghazali"),
                        new Student("Oussama El Alaoui"),
                        new Student("Nisrine El Amrani"),
                        new Student("Tarik El Fassi"),
                        new Student("Siham El Kabbaj")
                )),
                new Groupe("Groupe 2", Arrays.asList(
                        new Student("Youssef El Ghazali"),
                        new Student("Rania El Fadili"),
                        new Student("Omar El Alaoui"),
                        new Student("Imane El Fassi"),
                        new Student("Walid El Amrani"),
                        new Student("Sara El Kabbaj")
                )),
                new Groupe("Groupe 3", Arrays.asList(
                        new Student("Hicham El Fadili"),
                        new Student("Meryem El Ghazali"),
                        new Student("Hamza El Alaoui"),
                        new Student("Sanae El Amrani"),
                        new Student("Zakaria El Fassi"),
                        new Student("Nada El Kabbaj")
                )),
                new Groupe("Groupe 4", Arrays.asList(
                        new Student("Reda El Ghazali"),
                        new Student("Lina El Fadili"),
                        new Student("Mehdi El Alaoui"),
                        new Student("Aya El Amrani"),
                        new Student("Yassine El Fassi"),
                        new Student("Fatima Zahra El Kabbaj")
                )),
                new Groupe("Groupe 5", Arrays.asList(
                        new Student("Othmane El Fadili"),
                        new Student("Ilham El Ghazali"),
                        new Student("Karim El Alaoui"),
                        new Student("Rim El Amrani"),
                        new Student("Younes El Fassi"),
                        new Student("Hajar El Kabbaj")
                )),
                new Groupe("Groupe 6", Arrays.asList(
                        new Student("Samir El Ghazali"),
                        new Student("Mouna El Fadili"),
                        new Student("Khalid El Alaoui"),
                        new Student("Siham El Amrani"),
                        new Student("Adil El Fassi"),
                        new Student("Sofia El Kabbaj")
                ))
        );
        centres.add(new Centre("EMSI Les Orangers", groupesOrangers));

        // --- EMSI Maarif ---
        List<Groupe> groupesMaarif = Arrays.asList(
                new Groupe("Groupe 1", Arrays.asList(
                        new Student("Amine El Idrissi"),
                        new Student("Sara Benali"),
                        new Student("Omar El Fassi"),
                        new Student("Imane Berrada"),
                        new Student("Mohamed Chafai"),
                        new Student("Salma Lahlou")
                )),
                new Groupe("Groupe 2", Arrays.asList(
                        new Student("Hicham Bouzid"),
                        new Student("Nadia El Alaoui"),
                        new Student("Khalid Amrani"),
                        new Student("Fatima Zahra Lahlou"),
                        new Student("Anas Benjelloun"),
                        new Student("Rania El Khatib")
                )),
                new Groupe("Groupe 3", Arrays.asList(
                        new Student("Soufiane Malki"),
                        new Student("Meryem Yousfi"),
                        new Student("Hamza Mansouri"),
                        new Student("Siham Idrissi"),
                        new Student("Youssef Gharbi"),
                        new Student("Aya Benjelloun")
                )),
                new Groupe("Groupe 4", Arrays.asList(
                        new Student("Zakaria Hachimi"),
                        new Student("Lina Fadili"),
                        new Student("Reda Alaoui"),
                        new Student("Sanae Amrani"),
                        new Student("Othmane Kabbaj"),
                        new Student("Nada Ghazali")
                )),
                new Groupe("Groupe 5", Arrays.asList(
                        new Student("Walid Idrissi"),
                        new Student("Ilham Fassi"),
                        new Student("Mehdi Benali"),
                        new Student("Rim Malki"),
                        new Student("Younes Amrani"),
                        new Student("Hajar Alaoui")
                )),
                new Groupe("Groupe 6", Arrays.asList(
                        new Student("Samir Gharbi"),
                        new Student("Mouna Khatib"),
                        new Student("Karim Fadili"),
                        new Student("Siham Benjelloun"),
                        new Student("Adil Ghazali"),
                        new Student("Sofia Idrissi")
                ))
        );
        centres.add(new Centre("EMSI Maarif", groupesMaarif));

        // --- EMSI Roudani ---
        List<Groupe> groupesmoulay = Arrays.asList(
                new Groupe("Groupe 1", Arrays.asList(
                        new Student("Yassine El Amrani"),
                        new Student("Leila Chraibi"),
                        new Student("Oussama El Hadi"),
                        new Student("Nisrine El Fassi"),
                        new Student("Tarik Alaoui"),
                        new Student("Siham Ghazali")
                )),
                new Groupe("Groupe 2", Arrays.asList(
                        new Student("Youssef Benjelloun"),
                        new Student("Rania Amrani"),
                        new Student("Omar Fadili"),
                        new Student("Imane Idrissi"),
                        new Student("Walid Khatib"),
                        new Student("Sara Gharbi")
                )),
                new Groupe("Groupe 3", Arrays.asList(
                        new Student("Hicham Alaoui"),
                        new Student("Meryem Fadili"),
                        new Student("Hamza Benali"),
                        new Student("Sanae Ghazali"),
                        new Student("Zakaria Amrani"),
                        new Student("Nada Idrissi")
                )),
                new Groupe("Groupe 4", Arrays.asList(
                        new Student("Reda Fassi"),
                        new Student("Lina Kabbaj"),
                        new Student("Mehdi Alaoui"),
                        new Student("Aya Ghazali"),
                        new Student("Yassine Gharbi"),
                        new Student("Fatima Zahra Amrani")
                )),
                new Groupe("Groupe 5", Arrays.asList(
                        new Student("Othmane Benali"),
                        new Student("Ilham Fadili"),
                        new Student("Karim Alaoui"),
                        new Student("Rim Ghazali"),
                        new Student("Younes Khatib"),
                        new Student("Hajar Gharbi")
                )),
                new Groupe("Groupe 6", Arrays.asList(
                        new Student("Samir Fassi"),
                        new Student("Mouna Alaoui"),
                        new Student("Khalid Ghazali"),
                        new Student("Siham Benjelloun"),
                        new Student("Adil Amrani"),
                        new Student("Sofia Fadili")
                ))
        );
        centres.add(new Centre("EMSI moulay", groupesmoulay));

        // Remplir le spinner des centres
        List<String> nomsCentresList = new ArrayList<>();
        for (Centre c : centres) nomsCentresList.add(c.getNom());
        ArrayAdapter<String> centreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomsCentresList);
        centreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCentres.setAdapter(centreAdapter);

        // Listener pour le centre
        spinnerCentres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Centre centre = centres.get(position);
                List<String> nomsGroupes = new ArrayList<>();
                for (Groupe g : centre.getGroupes()) nomsGroupes.add(g.getNom());
                ArrayAdapter<String> groupeAdapter = new ArrayAdapter<>(PresenceActivity.this, android.R.layout.simple_spinner_item, nomsGroupes);
                groupeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGroupes.setAdapter(groupeAdapter);

                // Sélectionner le premier groupe par défaut
                if (!centre.getGroupes().isEmpty()) {
                    updateEtudiants(centre.getGroupes().get(0));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Listener pour le groupe
        spinnerGroupes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Centre centre = centres.get(spinnerCentres.getSelectedItemPosition());
                Groupe groupe = centre.getGroupes().get(position);
                updateEtudiants(groupe);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSubmit.setOnClickListener(v -> saveAbsentStudents());
    }

    private void updateEtudiants(Groupe groupe) {
        adapter = new PresenceAdapter(new ArrayList<>(groupe.getEtudiants()));
        recyclerView.setLayoutManager(new LinearLayoutManager(PresenceActivity.this));
        recyclerView.setAdapter(adapter);
    }

    private void saveAbsentStudents() {
        if (adapter == null) return;
        List<Student> absentStudents = adapter.getAbsentStudents();

        String profId = (mAuth.getCurrentUser() != null) ? mAuth.getCurrentUser().getUid() : "unknown_professor";
        String currentTimestamp = String.valueOf(System.currentTimeMillis());
        String centreNom = spinnerCentres.getSelectedItem().toString();
        String groupeNom = spinnerGroupes.getSelectedItem().toString();

        if (absentStudents.isEmpty()) {
            Toast.makeText(this, "Tous les étudiants sont présents", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Student student : absentStudents) {
            AbsenceRecord absenceRecord = new AbsenceRecord(profId, student.getName(), currentTimestamp, groupeNom + " (" + centreNom + ")");

            db.collection("absences")
                    .add(absenceRecord)
                    .addOnSuccessListener(documentReference -> {
                        // Optionally handle success
                    })
                    .addOnFailureListener(e -> {
                        // Optionally handle failure
                    });
        }

        Toast.makeText(this, "Enregistrement des absences terminé. Vérifiez la base de données.", Toast.LENGTH_LONG).show();
    }
}