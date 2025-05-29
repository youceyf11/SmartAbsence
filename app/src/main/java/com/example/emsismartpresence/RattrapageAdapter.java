package com.example.emsismartpresence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RattrapageAdapter extends RecyclerView.Adapter<RattrapageAdapter.RattrapageViewHolder> {

    private List<RattrapageActivity.RattrapageItem> rattrapageList;
    private Context context;

    public RattrapageAdapter(List<RattrapageActivity.RattrapageItem> rattrapageList, Context context) {
        this.rattrapageList = rattrapageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RattrapageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rattrapage, parent, false);
        return new RattrapageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RattrapageViewHolder holder, int position) {
        RattrapageActivity.RattrapageItem item = rattrapageList.get(position);

        holder.txtMatiere.setText(item.getMatiere());
        holder.txtDateHeure.setText(item.getDateHeure());
        holder.txtSalle.setText(item.getSalle());
        holder.txtGroupe.setText(item.getGroupe());
        holder.txtStatut.setText(item.getStatut());

        // Set status background color based on status
        int statusColor;
        switch (item.getStatut()) {
            case "En attente":
                statusColor = ContextCompat.getColor(context, android.R.color.holo_orange_dark);
                break;
            case "Confirmé":
                statusColor = ContextCompat.getColor(context, android.R.color.holo_blue_dark);
                break;
            case "Terminé":
                statusColor = ContextCompat.getColor(context, android.R.color.holo_green_dark);
                break;
            case "Annulé":
                statusColor = ContextCompat.getColor(context, android.R.color.holo_red_dark);
                break;
            default:
                statusColor = ContextCompat.getColor(context, android.R.color.darker_gray);
                break;
        }
        holder.txtStatut.setBackgroundColor(statusColor);

        // Set button visibility based on status
        if (item.getStatut().equals("En attente")) {
            holder.btnAction.setVisibility(View.VISIBLE);
            holder.btnAction.setText("Confirmer");
        } else if (item.getStatut().equals("Confirmé")) {
            holder.btnAction.setVisibility(View.VISIBLE);
            holder.btnAction.setText("Marquer terminé");
        } else {
            holder.btnAction.setVisibility(View.GONE);
        }

        // Set click listeners
        holder.btnDetails.setOnClickListener(v -> {
            Toast.makeText(context, "Détails pour " + item.getMatiere(), Toast.LENGTH_SHORT).show();
            // TODO: Implement details view
        });

        holder.btnAction.setOnClickListener(v -> {
            if (item.getStatut().equals("En attente")) {
                item.setStatut("Confirmé");
                Toast.makeText(context, "Rattrapage confirmé", Toast.LENGTH_SHORT).show();
            } else if (item.getStatut().equals("Confirmé")) {
                item.setStatut("Terminé");
                Toast.makeText(context, "Rattrapage marqué comme terminé", Toast.LENGTH_SHORT).show();
            }
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return rattrapageList.size();
    }

    public static class RattrapageViewHolder extends RecyclerView.ViewHolder {
        TextView txtMatiere, txtDateHeure, txtSalle, txtGroupe, txtStatut;
        Button btnDetails, btnAction;

        public RattrapageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMatiere = itemView.findViewById(R.id.txt_rattrapage_matiere);
            txtDateHeure = itemView.findViewById(R.id.txt_rattrapage_date);
            txtSalle = itemView.findViewById(R.id.txt_rattrapage_salle);
            txtGroupe = itemView.findViewById(R.id.txt_rattrapage_groupe);
            txtStatut = itemView.findViewById(R.id.txt_rattrapage_statut);
            btnDetails = itemView.findViewById(R.id.btn_rattrapage_details);
            btnAction = itemView.findViewById(R.id.btn_rattrapage_action);
        }
    }
}
