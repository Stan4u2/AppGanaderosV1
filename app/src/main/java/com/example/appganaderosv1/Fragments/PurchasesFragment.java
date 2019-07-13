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

import com.example.appganaderosv1.Adapter.Adapter_purchases;
import com.example.appganaderosv1.ConexionSQLiteHelper;
import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.insert_new_purchases;
import com.example.appganaderosv1.purchase_details;
import com.example.appganaderosv1.utilidades.Utilidades;

import static com.example.appganaderosv1.MainActivity.administrator;

import java.util.ArrayList;

public class PurchasesFragment extends Fragment {

    ArrayList<Persona> listaPersonas;
    ArrayList<Compras> listaCompras;

    RecyclerView recycler_view;

    ImageButton agregar, borrar;

    boolean deleteButton = false;

    ConexionSQLiteHelper conn;

    String normalUser = "SELECT * FROM " + Utilidades.VIEW_COMPRAS;
    String admin = "SELECT " +
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
            Utilidades.CAMPO_PERSONA_COMPRO + " = " + Utilidades.CAMPO_ID_PERSONA +
            " AND " +
            Utilidades.CAMPO_RESPALDO_COMPRAS + " = " + 1 +
            " ORDER BY " +
            "DATE(" + Utilidades.CAMPO_FECHA_COMPRAS + ")";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchases, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_ganado", null, 2);

        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        agregar = view.findViewById(R.id.agregar);

        borrar = view.findViewById(R.id.borrar);

        listaPersonas = new ArrayList<>();
        listaCompras = new ArrayList<>();

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

        Compras compras = null;
        Persona persona = null;

        listaPersonas = new ArrayList<Persona>();
        listaCompras = new ArrayList<Compras>();

        String SELECT;

        if (deleteButton) {
            SELECT = admin;
        } else if (!false) {
            SELECT = normalUser;
        }

        Cursor cursor = db.rawQuery(SELECT, null);

        while (cursor.moveToNext()) {
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
            listaPersonas.add(persona);
        }

        cursor.close();
        db.close();

        Adapter_purchases adapter_purchases = new Adapter_purchases(listaPersonas, listaCompras);

        adapter_purchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Compras compras = listaCompras.get(recycler_view.getChildAdapterPosition(view));
                Persona persona = listaPersonas.get(recycler_view.getChildAdapterPosition(view));

                Intent intent = new Intent(view.getContext(), purchase_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);
                bundle.putSerializable("compras", compras);
                bundle.putSerializable("garbage", "false");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recycler_view.setAdapter(adapter_purchases);

    }

    public void newPurchase(View view) {
        Intent miIntent = null;
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.agregar:
                miIntent = new Intent(view.getContext(), insert_new_purchases.class);
                bundle.putSerializable("action", "insert");
                break;
        }
        if (miIntent != null) {
            miIntent.putExtras(bundle);
            view.getContext().startActivity(miIntent);
        }
    }

}
