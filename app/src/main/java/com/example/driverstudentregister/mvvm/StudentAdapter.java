package com.example.driverstudentregister.mvvm;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.driverstudentregister.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class StudentAdapter extends FirestoreRecyclerAdapter <StudentModel, StudentAdapter.StudentViewHolder> {

    private OnItemClickedListener listener;

    public StudentAdapter(@NonNull FirestoreRecyclerOptions<StudentModel> options) {
        super(options);
    }

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

    class StudentViewHolder extends RecyclerView.ViewHolder  {
        TextView name, date;
        LinearLayout button;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            button = itemView.findViewById(R.id.button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });

        }

    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    public interface OnItemClickedListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener){
        this.listener = listener;
    }


}