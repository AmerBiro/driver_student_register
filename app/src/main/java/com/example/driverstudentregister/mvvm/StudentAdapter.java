package com.example.driverstudentregister.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driverstudentregister.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class StudentAdapter extends FirestoreRecyclerAdapter <StudentModel, StudentAdapter.StudentViewHolder> {

    private OnStudentItemClicked onStudentItemClicked;

    public StudentAdapter(@NonNull FirestoreRecyclerOptions<StudentModel> options, OnStudentItemClicked onStudentItemClicked) {
        super(options);
        this.onStudentItemClicked = onStudentItemClicked;
    }

//    public StudentAdapter(@NonNull FirestoreRecyclerOptions<StudentModel> options) {
//        super(options);
//    }

    @Override
    protected void onBindViewHolder(@NonNull StudentViewHolder holder, int position, @NonNull StudentModel model) {
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, date;
        LinearLayout button;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            button = itemView.findViewById(R.id.button);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStudentItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public interface OnStudentItemClicked {
        void onItemClicked(int postion);
    }

}