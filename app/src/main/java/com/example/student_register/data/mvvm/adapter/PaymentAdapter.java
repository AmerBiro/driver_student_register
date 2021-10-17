package com.example.student_register.data.mvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.student_register.R;
import com.example.student_register.data.mvvm.model.PaymentModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<PaymentModel> paymentModels;
    private OnPaymentItemClicked onPaymentItemClicked;

    public PaymentAdapter(OnPaymentItemClicked onPaymentItemClicked) {
        this.onPaymentItemClicked = onPaymentItemClicked;
    }

    public void setPaymentModels(List<PaymentModel> paymentModels) {
        this.paymentModels = paymentModels;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        holder.date.setText(paymentModels.get(position).getDate().toString());
        holder.payment.setText(paymentModels.get(position).getPayment() + " DKK");
    }


    @Override
    public int getItemCount() {
        if (paymentModels == null) {
            return 0;
        } else {
            return paymentModels.size();
        }
    }


    public class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private EditText date;
        private TextView payment;
        private FloatingActionButton select_single_payment;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.single_payment_date);
            payment = itemView.findViewById(R.id.single_payment);
            select_single_payment = itemView.findViewById(R.id.select_single_payment);
            select_single_payment.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPaymentItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnPaymentItemClicked {
        public void onItemClicked(int position);
    }

}