package com.example.student_register.ui.others.functions;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.TextView;

import java.util.Calendar;

public class CustomDatePicker {

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar calendar = Calendar.getInstance();
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH);
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    public CustomDatePicker() {
    }

    public void datePicker(Activity activity, TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                dateSetListener, year, month, day);
        datePickerDialog.show();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth + "/" + month + "/" + year;
                textView.setText(date);
            }
        };
    }

}
