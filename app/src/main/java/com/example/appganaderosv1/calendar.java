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
import java.util.GregorianCalendar;

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String[] dateCal = sdf.format(new Date(calendar.getDate())).split("/");

        String[] dateCeros = new String[3];

        dateCeros[0] = dateCal[0];
        dateCeros[1] = dateCal[1];
        dateCeros[2] = dateCal[2];

        calendarDate.setText((dateCeros[0] + "-" + dateCeros[1] + "-" + dateCeros[2]));

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int monthOfYear, int dayOfMonth) {

                int month = monthOfYear + 1;

                dateCeros[0] = String.valueOf(year);
                dateCeros[1] = String.valueOf(month);
                dateCeros[2] = String.valueOf(dayOfMonth);

                if(month < 10){
                    dateCeros[1] = "0"+month;
                }
                if (dayOfMonth < 10){
                    dateCeros[2] = "0"+dayOfMonth;
                }

                String date = (dateCeros[0] + "-" + dateCeros[1] + "-" + dateCeros[2]);

                calendarDate.setText(date);
            }
        });
    }

    public void class_that_came_from() {
        Intent intent = getIntent();
        button_class = intent.getExtras().getString("button");
    }

    public void saveDate(View view) {
        if (calendarDate.getText().toString().equals("aaaa-mm-dd")) {
            Toast.makeText(getApplicationContext(), "Selecione una fecha", Toast.LENGTH_LONG).show();
        } else {
            switch (button_class) {
                case "appointment":
                    insert_new_appointment.DateAppointment = calendarDate.getText().toString();
                    insert_new_appointment.dateAppointment = true;
                    break;
                case "purchase":
                    insert_new_purchases.DatePurchase = calendarDate.getText().toString();
                    insert_new_purchases.datePurchase = true;
                    break;
                case "sale":
                    insert_new_sales.DateSale = calendarDate.getText().toString();
                    insert_new_sales.dateSale = true;
            }
            finish();
        }
    }
}
