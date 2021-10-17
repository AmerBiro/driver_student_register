package com.example.student_register.data.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_register.R;
import com.example.student_register.data.mvvm.model.StudentModel;

import java.util.List;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<StudentModel> studentModels;
    private OnStudentItemClicked onStudentItemClicked;

    public StudentAdapter(OnStudentItemClicked onStudentItemClicked) {
        this.onStudentItemClicked = onStudentItemClicked;
    }

    public void setStudentModels(List<StudentModel> studentModels) {
        this.studentModels = studentModels;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.single_item_name.setText(studentModels.get(position).getName());
        holder.single_item_date.setText(studentModels.get(position).getDate().toString());
        holder.student_number.setText((position+1) + "");
    }

    @Override
    public int getItemCount() {
        if (studentModels == null) {
            return 0;
        } else {
            return studentModels.size();
        }
    }


    public class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView single_item_name;
        private TextView single_item_date;
        private TextView student_number;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            single_item_name = itemView.findViewById(R.id.single_item_name);
            single_item_date = itemView.findViewById(R.id.single_item_date);
            student_number = itemView.findViewById(R.id.student_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStudentItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnStudentItemClicked {
        public void onItemClicked(int position);
    }

}