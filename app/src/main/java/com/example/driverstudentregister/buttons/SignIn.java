package com.example.driverstudentregister.buttons;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.driverstudentregister.R;


public class SignIn implements CustomButton {

    private ProgressBar progressBar;
    private Button check_mark, error;
    private TextView textView;
    private View view;

    public SignIn() {

    }

    @Override
    public void setView(View view) {
        this.progressBar = view.findViewById(R.id.custom_button_sign_in_progress_bar);
        this.check_mark = view.findViewById(R.id.custom_button_sign_in_check_mark);
        this.error = view.findViewById(R.id.custom_button_sign_in_error);
        this.textView = view.findViewById(R.id.custom_button_sign_in_text);
        this.view = view;
        this.view = view.findViewById(R.id.custom_button_sign_in);
    }

    @Override
    public void setDefaultText(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.GONE);
        check_mark.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.GONE);
        this.error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRepeat(String s) {
        String text = this.textView.getText().toString();
        text = s;
        this.textView.setText(text);
        this.progressBar.setVisibility(View.GONE);
        this.error.setVisibility(View.GONE);
    }

    public View getView() {
        return view;
    }


}
