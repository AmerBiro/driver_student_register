package com.example.driverstudentregister.functions;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.driverstudentregister.R;

import org.w3c.dom.Text;

public class CustomButtonSignIn {

    private ProgressBar progressBar;
    private Button check_mark, error;
    private TextView textView;

    public CustomButtonSignIn(View view) {
        this.progressBar = view.findViewById(R.id.custom_button_sign_in_progress_bar);
        this.check_mark = view.findViewById(R.id.custom_button_sign_in_check_mark);
        this.error = view.findViewById(R.id.custom_button_sign_in_error);
        this.textView = view.findViewById(R.id.custom_button_sign_in_text);
    }

    public void setDefaultText(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.textView.setVisibility(View.VISIBLE);
    }

    public void onClick(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void onSuccess(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.GONE);
        check_mark.setVisibility(View.VISIBLE);
    }

    public void onFailure(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.GONE);
        this.error.setVisibility(View.VISIBLE);
    }

    public void onRepeat(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.GONE);
        this.error.setVisibility(View.GONE);
    }


}
