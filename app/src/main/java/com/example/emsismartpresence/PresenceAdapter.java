package com.example.emsismartpresence;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PresenceAdapter extends RecyclerView.Adapter<PresenceAdapter.ViewHolder> {

    private final List<Student> studentList;

    public PresenceAdapter(List<Student> students) {
        this.studentList = students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.name.setText(student.getName());
        holder.checkBox.setChecked(student.isPresent());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            student.setPresent(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public List<Student> getAbsentStudents() {
        List<Student> absents = new ArrayList<>();
        for (Student s : studentList) {
            if (!s.isPresent()) absents.add(s);
        }
        return absents;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_student_name);
            checkBox = itemView.findViewById(R.id.checkbox_presence);
        }
    }
}
