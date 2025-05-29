package com.example.emsismartpresence;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReclamationAdapter extends RecyclerView.Adapter<ReclamationAdapter.ReclamationViewHolder> {

    private List<Activity_reclamation.Reclamation> reclamationList;
    private Context context;
    private OnReclamationActionListener listener;

    // Interface pour les actions sur les réclamations
    public interface OnReclamationActionListener {
        void onDeleteReclamation(int position);
        void onEditReclamation(int position);
        void onCancelReclamation(int position);
    }

    public ReclamationAdapter(List<Activity_reclamation.Reclamation> reclamationList, Context context) {
        this.reclamationList = reclamationList;
        this.context = context;
    }

    public void setOnReclamationActionListener(OnReclamationActionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReclamationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reclamation, parent, false);
        return new ReclamationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReclamationViewHolder holder, int position) {
        Activity_reclamation.Reclamation reclamation = reclamationList.get(position);

        holder.txtTitre.setText(reclamation.getTitre());
        holder.txtDescription.setText(reclamation.getDescription());
        holder.txtDate.setText(reclamation.getDate());
        holder.txtPriorite.setText(reclamation.getPriorite());
        holder.txtStatut.setText(reclamation.getStatut());

        // Définir la couleur du statut
        switch (reclamation.getStatut()) {
            case "En attente":
                holder.txtStatut.setBackgroundColor(Color.parseColor("#F39C12")); // Orange
                break;
            case "Traitée":
                holder.txtStatut.setBackgroundColor(Color.parseColor("#3498DB")); // Bleu
                break;
            case "Résolue":
                holder.txtStatut.setBackgroundColor(Color.parseColor("#27AE60")); // Vert
                break;
            case "Rejetée":
                holder.txtStatut.setBackgroundColor(Color.parseColor("#E74C3C")); // Rouge
                break;
            case "Annulée":
                holder.txtStatut.setBackgroundColor(Color.parseColor("#95A5A6")); // Gris
                break;
            default:
                holder.txtStatut.setBackgroundColor(Color.parseColor("#95A5A6")); // Gris
                break;
        }

        // Définir la couleur de la priorité
        switch (reclamation.getPriorite()) {
            case "Normale":
                holder.txtPriorite.setTextColor(Color.parseColor("#3498DB")); // Bleu
                break;
            case "Urgente":
                holder.txtPriorite.setTextColor(Color.parseColor("#F39C12")); // Orange
                break;
            case "Critique":
                holder.txtPriorite.setTextColor(Color.parseColor("#E74C3C")); // Rouge
                break;
            default:
                holder.txtPriorite.setTextColor(Color.parseColor("#95A5A6")); // Gris
                break;
        }

        // Ajouter un effet visuel pour les nouvelles réclamations (position 0)
        if (position == 0 && reclamation.getStatut().equals("En attente")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E8F5E8")); // Fond vert très clair
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE); // Fond blanc normal
        }

        // Gérer la visibilité du bouton de suppression et des actions
        if (reclamation.getStatut().equals("En attente")) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.layoutActions.setVisibility(View.VISIBLE);
        } else {
            holder.btnDelete.setVisibility(View.GONE);
            holder.layoutActions.setVisibility(View.GONE);
        }

        // Listener pour le bouton de suppression
        holder.btnDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(position, reclamation.getTitre());
        });

        // Listener pour le bouton modifier
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditReclamation(position);
            }
        });

        // Listener pour le bouton annuler
        holder.btnCancel.setOnClickListener(v -> {
            showCancelConfirmationDialog(position, reclamation.getTitre());
        });
    }

    private void showDeleteConfirmationDialog(int position, String titre) {
        new AlertDialog.Builder(context)
                .setTitle("Supprimer la réclamation")
                .setMessage("Êtes-vous sûr de vouloir supprimer définitivement la réclamation \"" + titre + "\" ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    if (listener != null) {
                        listener.onDeleteReclamation(position);
                    }
                    Toast.makeText(context, "Réclamation supprimée", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void showCancelConfirmationDialog(int position, String titre) {
        new AlertDialog.Builder(context)
                .setTitle("Annuler la réclamation")
                .setMessage("Êtes-vous sûr de vouloir annuler la réclamation \"" + titre + "\" ?\n\nElle restera dans votre historique avec le statut \"Annulée\".")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("Annuler la réclamation", (dialog, which) -> {
                    if (listener != null) {
                        listener.onCancelReclamation(position);
                    }
                    Toast.makeText(context, "Réclamation annulée", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Retour", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return reclamationList.size();
    }

    public static class ReclamationViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitre, txtDescription, txtDate, txtPriorite, txtStatut;
        ImageView btnDelete;
        LinearLayout layoutActions;
        Button btnEdit, btnCancel;

        public ReclamationViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitre = itemView.findViewById(R.id.txt_reclamation_titre);
            txtDescription = itemView.findViewById(R.id.txt_reclamation_description);
            txtDate = itemView.findViewById(R.id.txt_reclamation_date);
            txtPriorite = itemView.findViewById(R.id.txt_reclamation_priorite);
            txtStatut = itemView.findViewById(R.id.txt_reclamation_statut);
            btnDelete = itemView.findViewById(R.id.btn_delete_reclamation);
            layoutActions = itemView.findViewById(R.id.layout_actions);
            btnEdit = itemView.findViewById(R.id.btn_edit_reclamation);
            btnCancel = itemView.findViewById(R.id.btn_cancel_reclamation);
        }
    }
}
