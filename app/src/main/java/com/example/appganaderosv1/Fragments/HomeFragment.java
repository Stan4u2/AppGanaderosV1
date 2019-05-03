package com.example.appganaderosv1.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.appganaderosv1.ConexionSQLiteHelper;
import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Citas;
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Ventas;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {

    ConexionSQLiteHelper conn;

    Button appointment, purchase, sales;

    CalendarView calendarView;

    RecyclerView recycler_view_home;

    //RecyclerView Data
    ArrayList<Persona> listaPersona;
    //Appointment
    ArrayList<Citas> listaCitas;

    //Purchase
    ArrayList<Compras> listaCompras;

    //Sales
    ArrayList<Ventas> listaVentas;

    String date;

    Boolean appoint = false, purch = false, sale = false;

    List<EventDay> events = new ArrayList<>();



    ArrayList<String> dates = new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_ganado", null, 2);

        appointment = view.findViewById(R.id.appointment);
        purchase = view.findViewById(R.id.purchase);
        sales = view.findViewById(R.id.sales);

        calendarView = view.findViewById(R.id.calendarView);

        //RecyclerView Data
        listaPersona = new ArrayList<>();
        //Appointment
        listaCitas = new ArrayList<>();

        //Purchase
        listaCompras = new ArrayList<>();

        //Sales
        listaVentas = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();

        date = sdf.format(cal.getTime());

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                date = sdf.format(clickedDayCalendar.getTime());
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllFalse();
                appoint = true;
                appointment.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorSelected));
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllFalse();
                purch = true;
                purchase.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorSelected));
            }
        });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllFalse();
                sale = true;
                sales.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorSelected));
            }
        });

        fillListDates();

        return view;
    }

    private void fillListDates() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor appointment = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_FECHA_CITAS +
                        " FROM " +
                        Utilidades.TABLA_CITAS, null
        );

        while (appointment.moveToNext()) {
            dates.add(appointment.getString(0));
        }

        Cursor purchase = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_FECHA_COMPRAS +
                        " FROM " +
                        Utilidades.TABLA_COMPRAS, null
        );

        while (purchase.moveToNext()) {
            if (dates.isEmpty()) {
                dates.add(purchase.getString(0));
            } else {
                if (!dates.contains(purchase.getString(0))) {
                    dates.add(purchase.getString(0));
                }
            }
        }

        Cursor sale = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_FECHA_VENTAS +
                        " FROM " +
                        Utilidades.TABLA_VENTAS, null
        );

        while (sale.moveToNext()) {
            if (dates.isEmpty()) {
                dates.add(sale.getString(0));
            } else {
                if (!dates.contains(sale.getString(0))) {
                    dates.add(sale.getString(0));
                }
            }
        }

        for(String object : dates){
            Calendar calendar = Calendar.getInstance();
            System.out.println(object);
            String [] dateCal = object.split("/");

            calendar.set(Integer.valueOf(dateCal[2]), Integer.valueOf(dateCal[1])-1, Integer.valueOf(dateCal[0]));
            events.add(new EventDay(calendar, R.drawable.toro));

        }

        calendarView.setEvents(events);
    }

    @Override
    public void onClick(View view) {

    }

    private void setAllFalse() {
        appoint = false;
        purch = false;
        sale = false;

        appointment.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorNotSelected));
        purchase.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorNotSelected));
        sales.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorNotSelected));
    }

}
