package com.example.appganaderosv1.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.appganaderosv1.Adapter.Adapter_sales;
import com.example.appganaderosv1.ConexionSQLiteHelper;
import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Ventas;
import com.example.appganaderosv1.insert_new_sales;
import com.example.appganaderosv1.sales_details;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

import static com.example.appganaderosv1.MainActivity.administrator;

public class SalesFragment extends Fragment {

    ArrayList<Persona> listaPersonas;
    ArrayList<Ventas> listaVentas;

    RecyclerView recycler_view;

    ImageButton agregar_venta, borrar;

    boolean deleteButton = false;

    ConexionSQLiteHelper conn;

    String normalUser = "SELECT * FROM " + Utilidades.VIEW_VENTAS;
    String admin = "SELECT " +
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
            Utilidades.CAMPO_PERSONA_VENTA + " = " + Utilidades.CAMPO_ID_PERSONA +
            " AND " +
            Utilidades.CAMPO_RESPALDO_VENTAS + " = " + 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_ganado", null, 2);

        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        agregar_venta = view.findViewById(R.id.agregar_venta);

        borrar = view.findViewById(R.id.borrar);

        listaPersonas = new ArrayList<>();
        listaVentas = new ArrayList<>();

        if (administrator) {
            borrar.setVisibility(View.VISIBLE);
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (deleteButton) {
                        deleteButton = false;
                        borrar.setBackgroundColor(Color.TRANSPARENT);
                    } else if (!false) {
                        deleteButton = true;
                        borrar.setBackgroundColor(Color.LTGRAY);
                    }
                    fillList();
                }
            });
        } else {
            borrar.setVisibility(View.GONE);
        }

        fillList();

        return view;
    }

    public void onResume() {
        super.onResume();
        fillList();
    }

    private void fillList() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Ventas ventas = null;
        Persona persona = null;

        listaVentas = new ArrayList<Ventas>();
        listaPersonas = new ArrayList<Persona>();

        String SELECT;

        if (deleteButton) {
            SELECT = admin;
        } else if (!false) {
            SELECT = normalUser;
        }

        Cursor cursor = db.rawQuery(
                SELECT, null
        );

        while (cursor.moveToNext()) {
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
            listaPersonas.add(persona);
        }

        cursor.close();
        db.close();

        Adapter_sales adapter_sales = new Adapter_sales(listaPersonas, listaVentas);

        adapter_sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ventas ventas = listaVentas.get(recycler_view.getChildAdapterPosition(view));
                Persona persona = listaPersonas.get(recycler_view.getChildAdapterPosition(view));


                Intent intent = new Intent(view.getContext(), sales_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);
                bundle.putSerializable("ventas", ventas);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recycler_view.setAdapter(adapter_sales);

    }


    public void newSale(View view) {

        Intent miIntent = null;
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.agregar_venta:
                miIntent = new Intent(view.getContext(), insert_new_sales.class);
                bundle.putSerializable("action", "insert");
                bundle.putSerializable("owner", "no");
                break;
        }
        if (miIntent != null) {
            miIntent.putExtras(bundle);
            view.getContext().startActivity(miIntent);
        }

    }

}
