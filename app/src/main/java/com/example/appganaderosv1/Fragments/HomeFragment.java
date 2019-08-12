package com.example.appganaderosv1.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.appganaderosv1.Adapter.Adapter_appointment;
import com.example.appganaderosv1.Adapter.Adapter_purchases;
import com.example.appganaderosv1.Adapter.Adapter_sales;
import com.example.appganaderosv1.ConexionSQLiteHelper;
import com.example.appganaderosv1.R;
import com.example.appganaderosv1.appointment_details;
import com.example.appganaderosv1.entidades.Citas;
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Ventas;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.appganaderosv1.purchase_details;
import com.example.appganaderosv1.sales_details;
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
    ArrayList<Persona> listaPersonaCita;
    ArrayList<Persona> listaPersonaCompra;
    ArrayList<Persona> listaPersonaVenta;
    //Appointment
    ArrayList<Citas> listaCitas;

    //Purchase
    ArrayList<Compras> listaCompras;

    //Sales
    ArrayList<Ventas> listaVentas;

    static String date;

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

        recycler_view_home = view.findViewById(R.id.recycler_view_home);
        recycler_view_home.setLayoutManager(new LinearLayoutManager(getContext()));

        //RecyclerView Data
        listaPersonaCita = new ArrayList<>();
        listaPersonaCompra = new ArrayList<>();
        listaPersonaVenta = new ArrayList<>();
        //Appointment
        listaCitas = new ArrayList<>();

        //Purchase
        listaCompras = new ArrayList<>();

        //Sales
        listaVentas = new ArrayList<>();

        fillListDates();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        Calendar cal = Calendar.getInstance();

        String[] dateCal = sdf.format(cal.getTime()).split("/");

        String[] dateCeros = new String[3];

        dateCeros[0] = dateCal[0];
        dateCeros[1] = dateCal[1];
        dateCeros[2] = dateCal[2];

        date = (dateCeros[0] + "-" + dateCeros[1] + "-" + dateCeros[2]);

        System.out.println(date);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                date = sdf.format(clickedDayCalendar.getTime());
                String[] dateCa = sdf.format(clickedDayCalendar.getTime()).split("/");

                dateCeros[0] = dateCa[0];
                dateCeros[1] = dateCa[1];
                dateCeros[2] = dateCa[2];

                date = (dateCeros[0] + "-" + dateCeros[1] + "-" + dateCeros[2]);

                setAllFalse();

                recycler_view_home.setVisibility(View.GONE);

                System.out.println(date);
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler_view_home.setVisibility(View.VISIBLE);
                setAllFalse();
                appointment.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorSelected));
                fillListAppointment();
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler_view_home.setVisibility(View.VISIBLE);
                setAllFalse();
                purchase.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorSelected));
                fillListPurchase();
            }
        });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recycler_view_home.setVisibility(View.VISIBLE);
                setAllFalse();
                sales.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.buttonColorSelected));
                fillListSales();
            }
        });

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

        for (String object : dates) {
            Calendar calendar = Calendar.getInstance();
            String[] dateCal = object.split("-");

            //calendar.set(Integer.valueOf(dateCal[2]), Integer.valueOf(dateCal[1]) - 1, Integer.valueOf(dateCal[0]));

            calendar.set(Integer.valueOf(dateCal[0]), Integer.valueOf(dateCal[1]) - 1, Integer.valueOf(dateCal[2]));

            events.add(new EventDay(calendar, R.drawable.toro));

        }

        calendarView.setEvents(events);
    }

    private void fillListAppointment() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Citas citas = null;
        Persona persona = null;


        listaPersonaCita = new ArrayList<Persona>();
        listaCitas = new ArrayList<Citas>();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_ID_CITAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_GANADO + ", " +
                        Utilidades.CAMPO_DATOS + ", " +
                        Utilidades.CAMPO_FECHA_CITAS + ", " +
                        Utilidades.CAMPO_RESPALDO_CITAS + ", " +

                        Utilidades.CAMPO_ID_PERSONA + ", " +
                        Utilidades.CAMPO_NOMBRE + ", " +
                        Utilidades.CAMPO_TELEFONO + ", " +
                        Utilidades.CAMPO_DOMICILIO + ", " +
                        Utilidades.CAMPO_DATOS_EXTRAS +
                        " FROM " +
                        Utilidades.TABLA_PERSONA + ", " +
                        Utilidades.TABLA_CITAS +
                        " WHERE " +
                        Utilidades.CAMPO_FECHA_CITAS + " = '" + date + "'" +
                        " AND " +
                        Utilidades.CAMPO_PERSONA_CITA + " = " + Utilidades.CAMPO_ID_PERSONA +
                        " AND " +
                        Utilidades.CAMPO_RESPALDO_CITAS + " = " + 0, null);

        while (cursor.moveToNext()) {
            System.out.println(date);
            citas = new Citas();
            citas.setId_citas(cursor.getInt(0));
            citas.setCantidad_ganado(cursor.getInt(1));
            citas.setDatos(cursor.getString(2));
            citas.setFecha(cursor.getString(3));
            citas.setRespaldo(cursor.getInt(4));

            persona = new Persona();
            persona.setId_persona(cursor.getInt(5));
            persona.setNombre(cursor.getString(6));
            persona.setTelefono(cursor.getString(7));
            persona.setDomicilio(cursor.getString(8));
            persona.setDatos_extras(cursor.getString(9));

            listaCitas.add(citas);
            listaPersonaCita.add(persona);
        }

        cursor.close();
        db.close();

        Adapter_appointment adapter_appointment = new Adapter_appointment(listaPersonaCita, listaCitas);

        adapter_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = listaPersonaCita.get(recycler_view_home.getChildAdapterPosition(view));
                Citas citas = listaCitas.get(recycler_view_home.getChildAdapterPosition(view));

                Intent intent = new Intent(view.getContext(), appointment_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);
                bundle.putSerializable("citas", citas);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recycler_view_home.setAdapter(adapter_appointment);
    }

    private void fillListPurchase() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Compras compras = null;
        Persona persona = null;

        listaPersonaCompra = new ArrayList<Persona>();
        listaCompras = new ArrayList<Compras>();

        Cursor cursor = db.rawQuery("SELECT DISTINCT " +
                Utilidades.CAMPO_ID_COMPRA + ", " +
                Utilidades.CAMPO_FECHA_COMPRAS + ", " +
                Utilidades.CAMPO_CANTIDAD_ANIMALES_COMPRAS + ", " +
                Utilidades.CAMPO_CANTIDAD_PAGAR + ", " +
                Utilidades.CAMPO_COMPRA_PAGADA + ", " +
                Utilidades.CAMPO_RESPALDO_COMPRAS + ", " +

                Utilidades.CAMPO_ID_PERSONA + ", " +
                Utilidades.CAMPO_NOMBRE + ", " +
                Utilidades.CAMPO_TELEFONO + ", " +
                Utilidades.CAMPO_DOMICILIO + ", " +
                Utilidades.CAMPO_DATOS_EXTRAS +
                " FROM " +
                Utilidades.TABLA_PERSONA + ", " +
                Utilidades.TABLA_COMPRAS +
                " WHERE " +
                Utilidades.CAMPO_FECHA_COMPRAS + " = '" + date + "'" +
                " AND " +
                Utilidades.CAMPO_PERSONA_COMPRO + " = " + Utilidades.CAMPO_ID_PERSONA +
                " AND " +
                Utilidades.CAMPO_RESPALDO_COMPRAS + " = " + 0, null);

        while (cursor.moveToNext()) {
            System.out.println(date);
            compras = new Compras();
            compras.setId_compras(cursor.getInt(0));
            compras.setFecha_compra(cursor.getString(1));
            compras.setCantidad_animales_compra(cursor.getInt(2));
            compras.setCantidad_pagar(cursor.getInt(3));

            if (cursor.getInt(4) == 1) {
                compras.setCompra_pagada(true);
            } else if (cursor.getInt(4) == 0) {
                compras.setCompra_pagada(false);
            }

            compras.setRespaldo(cursor.getInt(5));

            persona = new Persona();
            persona.setId_persona(cursor.getInt(6));
            persona.setNombre(cursor.getString(7));
            persona.setTelefono(cursor.getString(8));
            persona.setDomicilio(cursor.getString(9));
            persona.setDatos_extras(cursor.getString(10));

            listaCompras.add(compras);
            listaPersonaCompra.add(persona);
        }

        cursor.close();
        db.close();

        Adapter_purchases adapter_purchases = new Adapter_purchases(listaPersonaCompra, listaCompras);

        adapter_purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Compras compras = listaCompras.get(recycler_view_home.getChildAdapterPosition(view));
                Persona persona = listaPersonaCompra.get(recycler_view_home.getChildAdapterPosition(view));

                Intent intent = new Intent(view.getContext(), purchase_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);
                bundle.putSerializable("compras", compras);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recycler_view_home.setAdapter(adapter_purchases);

    }

    private void fillListSales() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Ventas ventas = null;
        Persona persona = null;

        listaVentas = new ArrayList<Ventas>();
        listaPersonaVenta = new ArrayList<Persona>();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_ID_VENTAS + ", " +
                        Utilidades.CAMPO_FECHA_VENTAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_ANIMALES_VENTAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_COBRAR + ", " +
                        Utilidades.CAMPO_GANANCIAS + ", " +
                        Utilidades.CAMPO_VENTA_PAGADA + ", " +
                        Utilidades.CAMPO_RESPALDO_VENTAS + ", " +

                        Utilidades.CAMPO_ID_PERSONA + ", " +
                        Utilidades.CAMPO_NOMBRE + ", " +
                        Utilidades.CAMPO_TELEFONO + ", " +
                        Utilidades.CAMPO_DOMICILIO + ", " +
                        Utilidades.CAMPO_DATOS_EXTRAS +
                        " FROM " +
                        Utilidades.TABLA_PERSONA + ", " +
                        Utilidades.TABLA_VENTAS +
                        " WHERE " +
                        Utilidades.CAMPO_FECHA_VENTAS + " = '" + date + "'" +
                        " AND " +
                        Utilidades.CAMPO_PERSONA_VENTA + " = " + Utilidades.CAMPO_ID_PERSONA +
                        " AND " +
                        Utilidades.CAMPO_RESPALDO_VENTAS + " = " + 0, null
        );

        while (cursor.moveToNext()) {
            System.out.println(date);
            ventas = new Ventas();
            ventas.setId_ventas(cursor.getInt(0));
            ventas.setFecha(cursor.getString(1));
            ventas.setCantidad_animales(cursor.getInt(2));
            ventas.setCantidad_cobrar(cursor.getInt(3));
            ventas.setGanancias(cursor.getInt(4));

            if(cursor.getInt(5) == 1){
                ventas.setVenta_pagada(true);
            } else if (cursor.getInt(5) == 0){
                ventas.setVenta_pagada(false);
            }

            ventas.setRespaldo(cursor.getInt(6));

            persona = new Persona();
            persona.setId_persona(cursor.getInt(7));
            persona.setNombre(cursor.getString(8));
            persona.setTelefono(cursor.getString(9));
            persona.setDomicilio(cursor.getString(10));
            persona.setDatos_extras(cursor.getString(11));

            listaVentas.add(ventas);
            listaPersonaVenta.add(persona);
        }

        cursor.close();
        db.close();

        Adapter_sales adapter_sales = new Adapter_sales(listaPersonaVenta, listaVentas);

        adapter_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ventas ventas = listaVentas.get(recycler_view_home.getChildAdapterPosition(view));
                Persona persona = listaPersonaVenta.get(recycler_view_home.getChildAdapterPosition(view));

                Intent intent = new Intent(view.getContext(), sales_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);
                bundle.putSerializable("ventas", ventas);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recycler_view_home.setAdapter(adapter_sales);

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
