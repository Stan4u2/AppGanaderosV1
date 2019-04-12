package com.example.appganaderosv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class calendar extends AppCompatActivity {

    CalendarView calendar;
    TextView calendarDate;
    ImageButton saveDate;

    String button_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendar = findViewById(R.id.calendar);
        calendarDate = findViewById(R.id.calendar_date);
        saveDate = findViewById(R.id.saveDate);

        class_that_came_from();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendarDate.setText(sdf.format(new Date(calendar.getDate())));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = (String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year));
                calendarDate.setText(date);
            }
        });
    }

    public void class_that_came_from(){
        Intent intent = getIntent();
        button_class = intent.getExtras().getString("button");
    }

    public void saveDate(View view){
        if(calendarDate.getText().toString().equals("DD/MM/AAAA")){
            Toast.makeText(getApplicationContext(), "Selecione una fecha", Toast.LENGTH_LONG).show();
        }else {
            switch (button_class) {
                case "appointment":
                    insert_new_appointment.DateAppointment = calendarDate.getText().toString();
                    insert_new_appointment.dateAppointment = true;
                    break;
                case "purchase":
                    insert_new_purchases.DatePurchase = calendarDate.getText().toString();
                    insert_new_purchases.datePurchase = true;
                    break;
            }
            finish();
        }
    }
}
