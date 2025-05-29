package com.example.emsismartpresence;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Activity_reclamation extends AppCompatActivity implements ReclamationAdapter.OnReclamationActionListener {

    private static final int REQUEST_PICK_FILE = 1;

    private Spinner spinnerTypeReclamation;
    private RadioGroup radioGroupPriorite;
    private EditText editTitreReclamation, editDescriptionReclamation;
    private CheckBox checkboxPieceJointe;
    private Button btnAjouterPieceJointe, btnEnvoyerReclamation;
    private TextView txtNomFichier;
    private ImageView btnBackReclamation;
    private RecyclerView recyclerReclamations;

    private ReclamationAdapter reclamationAdapter;
    private List<Reclamation> reclamationList;
    private Uri pieceJointeUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);

        initViews();
        setupSpinner();
        setupListeners();
        setupRecyclerView();
        loadReclamations();
    }

    private void initViews() {
        spinnerTypeReclamation = findViewById(R.id.spinner_type_reclamation);
        radioGroupPriorite = findViewById(R.id.radio_group_priorite);
        editTitreReclamation = findViewById(R.id.edit_titre_reclamation);
        editDescriptionReclamation = findViewById(R.id.edit_description_reclamation);
        checkboxPieceJointe = findViewById(R.id.checkbox_piece_jointe);
        btnAjouterPieceJointe = findViewById(R.id.btn_ajouter_piece_jointe);
        btnEnvoyerReclamation = findViewById(R.id.btn_envoyer_reclamation);
        txtNomFichier = findViewById(R.id.txt_nom_fichier);
        btnBackReclamation = findViewById(R.id.btn_back_reclamation);
        recyclerReclamations = findViewById(R.id.recycler_reclamations);
    }

    private void setupSpinner() {
        List<String> typesReclamation = new ArrayList<>();
        typesReclamation.add("Sélectionnez un type");
        typesReclamation.add("Problème de salle");
        typesReclamation.add("Matériel défectueux");
        typesReclamation.add("Conflit d'horaire");
        typesReclamation.add("Problème administratif");
        typesReclamation.add("Problème avec un étudiant");
        typesReclamation.add("Autre");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typesReclamation);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeReclamation.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBackReclamation.setOnClickListener(v -> finish());

        checkboxPieceJointe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            btnAjouterPieceJointe.setEnabled(isChecked);
            if (!isChecked) {
                txtNomFichier.setVisibility(View.GONE);
                pieceJointeUri = null;
            }
        });

        btnAjouterPieceJointe.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, REQUEST_PICK_FILE);
        });

        btnEnvoyerReclamation.setOnClickListener(v -> {
            if (validateForm()) {
                envoyerReclamation();
            }
        });

        spinnerTypeReclamation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Premier élément sélectionné (instruction)
                    // Ne rien faire
                } else {
                    String typeSelectionne = parent.getItemAtPosition(position).toString();
                    // Vous pouvez utiliser le type sélectionné si nécessaire
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire
            }
        });
    }

    private void setupRecyclerView() {
        reclamationList = new ArrayList<>();
        reclamationAdapter = new ReclamationAdapter(reclamationList, this);
        reclamationAdapter.setOnReclamationActionListener(this);
        recyclerReclamations.setLayoutManager(new LinearLayoutManager(this));
        recyclerReclamations.setAdapter(reclamationAdapter);
    }

    private void loadReclamations() {
        // Simuler le chargement des réclamations précédentes
        reclamationList.add(new Reclamation(
                "Problème de salle",
                "La salle A101 n'est pas équipée correctement pour les cours de laboratoire.",
                "15/06/2024",
                "Urgente",
                "En attente"
        ));

        reclamationList.add(new Reclamation(
                "Matériel défectueux",
                "Le projecteur de la salle B205 ne fonctionne pas correctement.",
                "10/06/2024",
                "Normale",
                "Traitée"
        ));

        reclamationAdapter.notifyDataSetChanged();
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Vérifier le type de réclamation
        if (spinnerTypeReclamation.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Veuillez sélectionner un type de réclamation", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        // Vérifier le titre
        String titre = editTitreReclamation.getText().toString().trim();
        if (titre.isEmpty()) {
            editTitreReclamation.setError("Le titre est obligatoire");
            isValid = false;
        }

        // Vérifier la description
        String description = editDescriptionReclamation.getText().toString().trim();
        if (description.isEmpty()) {
            editDescriptionReclamation.setError("La description est obligatoire");
            isValid = false;
        }

        return isValid;
    }

    private void envoyerReclamation() {
        // Récupérer les données du formulaire
        String type = spinnerTypeReclamation.getSelectedItem().toString();
        String titre = editTitreReclamation.getText().toString().trim();
        String description = editDescriptionReclamation.getText().toString().trim();

        // Déterminer la priorité sélectionnée
        String priorite = "Normale";
        int selectedRadioButtonId = radioGroupPriorite.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.radio_urgente) {
            priorite = "Urgente";
        } else if (selectedRadioButtonId == R.id.radio_critique) {
            priorite = "Critique";
        }

        // Générer une référence unique pour la réclamation
        String reference = "REC-" + new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(new Date());

        // Créer la nouvelle réclamation avec la date actuelle
        String dateActuelle = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        Reclamation nouvelleReclamation = new Reclamation(titre, description, dateActuelle, priorite, "En attente");

        // Ajouter la réclamation au début de la liste (plus récente en premier)
        reclamationList.add(0, nouvelleReclamation);

        // Notifier l'adaptateur qu'un nouvel élément a été inséré
        reclamationAdapter.notifyItemInserted(0);

        // Faire défiler vers le haut pour voir la nouvelle réclamation
        recyclerReclamations.smoothScrollToPosition(0);

        // Afficher le dialogue de succès
        showSuccessDialog(reference);

        // Réinitialiser le formulaire
        resetForm();

        // Afficher un message de confirmation
        Toast.makeText(this, "Réclamation ajoutée à votre historique", Toast.LENGTH_SHORT).show();
    }

    private void showSuccessDialog(String reference) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_reclamation_success);

        TextView txtReference = dialog.findViewById(R.id.txt_reference_reclamation);
        Button btnOk = dialog.findViewById(R.id.btn_dialog_ok);

        txtReference.setText("Référence: " + reference);

        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void resetForm() {
        spinnerTypeReclamation.setSelection(0);
        editTitreReclamation.setText("");
        editDescriptionReclamation.setText("");
        radioGroupPriorite.check(R.id.radio_normale);
        checkboxPieceJointe.setChecked(false);
        btnAjouterPieceJointe.setEnabled(false);
        txtNomFichier.setVisibility(View.GONE);
        pieceJointeUri = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_FILE && resultCode == RESULT_OK && data != null) {
            pieceJointeUri = data.getData();
            if (pieceJointeUri != null) {
                String fileName = getFileName(pieceJointeUri);
                txtNomFichier.setText(fileName);
                txtNomFichier.setVisibility(View.VISIBLE);
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try {
                String[] projection = {android.provider.MediaStore.Images.Media.DISPLAY_NAME};
                android.database.Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DISPLAY_NAME);
                    result = cursor.getString(columnIndex);
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Implémentation des méthodes de l'interface OnReclamationActionListener
    @Override
    public void onDeleteReclamation(int position) {
        // Supprimer la réclamation de la liste
        reclamationList.remove(position);
        reclamationAdapter.notifyItemRemoved(position);

        // Mettre à jour les positions des éléments suivants
        reclamationAdapter.notifyItemRangeChanged(position, reclamationList.size());
    }

    @Override
    public void onEditReclamation(int position) {
        // TODO: Implémenter la modification d'une réclamation
        Reclamation reclamation = reclamationList.get(position);

        // Pré-remplir le formulaire avec les données de la réclamation
        editTitreReclamation.setText(reclamation.getTitre());
        editDescriptionReclamation.setText(reclamation.getDescription());

        // Définir la priorité
        switch (reclamation.getPriorite()) {
            case "Urgente":
                radioGroupPriorite.check(R.id.radio_urgente);
                break;
            case "Critique":
                radioGroupPriorite.check(R.id.radio_critique);
                break;
            default:
                radioGroupPriorite.check(R.id.radio_normale);
                break;
        }

        // Supprimer l'ancienne réclamation
        reclamationList.remove(position);
        reclamationAdapter.notifyItemRemoved(position);

        Toast.makeText(this, "Réclamation chargée pour modification", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelReclamation(int position) {
        // Changer le statut de la réclamation à "Annulée"
        Reclamation reclamation = reclamationList.get(position);
        reclamation.setStatut("Annulée");
        reclamationAdapter.notifyItemChanged(position);
    }

    // Classe modèle pour les réclamations
    public static class Reclamation {
        private String titre;
        private String description;
        private String date;
        private String priorite;
        private String statut;

        public Reclamation(String titre, String description, String date, String priorite, String statut) {
            this.titre = titre;
            this.description = description;
            this.date = date;
            this.priorite = priorite;
            this.statut = statut;
        }

        public String getTitre() { return titre; }
        public String getDescription() { return description; }
        public String getDate() { return date; }
        public String getPriorite() { return priorite; }
        public String getStatut() { return statut; }

        public void setStatut(String statut) { this.statut = statut; }
    }
}
