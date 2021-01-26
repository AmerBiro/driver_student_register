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
import com.example.student_register.mvvm.model.TheoryModel;
import java.util.List;

public class TheoryAdapter extends RecyclerView.Adapter<TheoryAdapter.TheoryViewHolder> {

    private List<TheoryModel> theoryModels;
    private OnTheoryItemClicked onTheoryItemClicked;


    public TheoryAdapter(OnTheoryItemClicked onTheoryItemClicked) {
        this.onTheoryItemClicked = onTheoryItemClicked;
    }

    public void setTheoryModels(List<TheoryModel> theoryModels) {
        this.theoryModels = theoryModels;
    }

    @NonNull
    @Override
    public TheoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_theory, parent, false);
        return new TheoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheoryViewHolder holder, int position) {
        holder.title.setText(theoryModels.get(position).getTitle() + " " +  (theoryModels.get(position).getNumber()+1));
        holder.date.setText(theoryModels.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        if (theoryModels == null) {
            return 0;
        } else {
            return theoryModels.size();
        }
    }


    public class TheoryViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private EditText date;
        private Button button;

        public TheoryViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.single_item_theory_title);
            date = itemView.findViewById(R.id.single_item_theory_date);
            button = itemView.findViewById(R.id.select_date);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTheoryItemClicked.onDateItemClicked(getAdapterPosition(), date);
                }
            });
        }


    }

    public interface OnTheoryItemClicked {
        public void onItemClicked(int position);
        public void onDateItemClicked(int position, EditText date);
    }

}