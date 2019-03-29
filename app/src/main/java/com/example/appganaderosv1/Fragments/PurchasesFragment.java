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
import android.widget.ImageButton;

import com.example.appganaderosv1.Adapter.Adapter_appointment;
import com.example.appganaderosv1.Adapter.Adapter_purchases;
import com.example.appganaderosv1.ConexionSQLiteHelper;
import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.insert_new_purchases;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class PurchasesFragment extends Fragment {

    ArrayList<Persona> listaPersonas;
    ArrayList<Compras> listaCompras;

    RecyclerView recycler_view;

    ImageButton agregar, borrar, modificar;

    ConexionSQLiteHelper conn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchases, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_ganado", null, 1);

        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        agregar = view.findViewById(R.id.agregar);

        borrar = view.findViewById(R.id.borrar);

        modificar = view.findViewById(R.id.modificar);

        listaPersonas = new ArrayList<>();
        listaCompras = new ArrayList<>();

        fillList();

        return view;


    }

    private void fillList() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Compras compras = null;
        Persona persona = null;

        listaPersonas = new ArrayList<Persona>();
        listaCompras = new ArrayList<Compras>();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        Utilidades.CAMPO_ID_COMPRA + ", " +
                        Utilidades.CAMPO_FECHA_COMPRAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_ANIMALES_COMPRAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_PAGAR + ", " +

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
                        Utilidades.CAMPO_RESPALDO_COMPRAS + " = " + 0,
                null
        );

        while(cursor.moveToNext()){
            compras = new Compras();
            compras.setId_compras(cursor.getInt(0));
            compras.setFecha_compra(cursor.getString(1));
            compras.setCantidad_animales_compra(cursor.getInt(2));
            compras.setCantidad_pagar(cursor.getInt(3));

            persona = new Persona();
            persona.setId_persona(cursor.getInt(4));
            persona.setNombre(cursor.getString(5));
            persona.setTelefono(cursor.getString(6));
            persona.setDomicilio(cursor.getString(7));
            persona.setDatos_extras(cursor.getString(8));


            listaPersonas.add(persona);
            listaCompras.add(compras);
        }

        Adapter_purchases adapter_purchases = new Adapter_purchases(listaPersonas, listaCompras);

        recycler_view.setAdapter(adapter_purchases);

    }

    public void newPurchase(View view) {
        Intent miIntent = null;
        Bundle bundle = new Bundle();

        switch (view.getId()){
            case R.id.agregar:
                miIntent = new Intent(view.getContext(), insert_new_purchases.class);
                bundle.putSerializable("action", "insert");
                break;
        }
        if(miIntent!=null){
            miIntent.putExtras(bundle);
            view.getContext().startActivity(miIntent);
        }
    }
}
