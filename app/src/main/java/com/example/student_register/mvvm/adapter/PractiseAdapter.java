package com.example.student_register.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_register.R;
import com.example.student_register.mvvm.model.PractiseModel;

import java.util.List;

public class PractiseAdapter extends RecyclerView.Adapter<PractiseAdapter.PractiseViewHolder> {

    private List<PractiseModel> practiseModels;
    private OnPractiseItemClicked onPractiseItemClicked;

    public PractiseAdapter(OnPractiseItemClicked onPractiseItemClicked) {
        this.onPractiseItemClicked = onPractiseItemClicked;
    }

    public void setPractiseModels(List<PractiseModel> practiseModels) {
        this.practiseModels = practiseModels;
    }

    @NonNull
    @Override
    public PractiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_practise, parent, false);
        return new PractiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PractiseViewHolder holder, int position) {
        holder.title.setText(practiseModels.get(position).getTitle() + " " + (practiseModels.get(position).getNumber() + 1));
        holder.date.setText(practiseModels.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        if (practiseModels == null) {
            return 0;
        } else {
            return practiseModels.size();
        }
    }


    public class PractiseViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private EditText date;
        private Button button;

        public PractiseViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.single_item_practise_title);
            date = itemView.findViewById(R.id.single_item_practise_date);
            button = itemView.findViewById(R.id.practise_select_date);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPractiseItemClicked.onDateItemClicked(getAdapterPosition(), date);
                }
            });
        }

    }

    public interface OnPractiseItemClicked {
        public void onItemClicked(int position);

        public void onDateItemClicked(int position, EditText date);
    }

}