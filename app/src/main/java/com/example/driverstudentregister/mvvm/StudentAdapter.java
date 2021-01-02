package com.example.driverstudentregister.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driverstudentregister.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter <StudentAdapter.StudentViewHolder> {

    private List<StudentModel> studentModels;

    public void setStudentModels(List<StudentModel> studentModels) {
        this.studentModels = studentModels;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.name.setText(studentModels.get(position).getName());
        holder.date.setText(studentModels.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        if(studentModels == null){
            return 0;
        } else {
            return studentModels.size();
        }
    }


    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView name, date;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
        }
    }

}
