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

import com.example.appganaderosv1.Adapter.Adapter_appointment;
import com.example.appganaderosv1.ConexionSQLiteHelper;
import com.example.appganaderosv1.R;
import com.example.appganaderosv1.appointment_details;
import com.example.appganaderosv1.entidades.Citas;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.insert_new_appointment;
import com.example.appganaderosv1.utilidades.Utilidades;

import static com.example.appganaderosv1.MainActivity.administrator;

import java.util.ArrayList;

public class AppointmentFragment extends Fragment implements View.OnClickListener {

    ArrayList<Persona> listaPersona;
    ArrayList<Citas> listaCitas;

    RecyclerView recycler_view;
    ImageButton agregar, borrar;

    boolean deleteButton = false;

    ConexionSQLiteHelper conn;

    String normalUser = "SELECT * FROM appointment_view";

    String admin = "SELECT " +
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
            Utilidades.CAMPO_PERSONA_CITA + " = " + Utilidades.CAMPO_ID_PERSONA +
            " AND " +
            Utilidades.CAMPO_RESPALDO_CITAS + " = " + 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_appointment, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_ganado", null, 2);

        recycler_view = vista.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        agregar = vista.findViewById(R.id.agregar);

        borrar = vista.findViewById(R.id.borrar);

        listaPersona = new ArrayList<>();
        listaCitas = new ArrayList<>();

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

        return vista;

    }

    //This part of the code updates the ListView in case that you made a modification
    public void onResume() {
        super.onResume();
        fillList();
    }

    private void fillList() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Citas citas = null;
        Persona persona = null;


        listaPersona = new ArrayList<Persona>();
        listaCitas = new ArrayList<Citas>();

        String SELECT;

        if (deleteButton) {
            SELECT = admin;
        } else if (!false) {
            SELECT = normalUser;
        }

        Cursor cursor = db.rawQuery(SELECT, null);

        while (cursor.moveToNext()) {
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
            listaPersona.add(persona);
        }

        cursor.close();
        db.close();

        Adapter_appointment adapter_appointment = new Adapter_appointment(listaPersona, listaCitas);

        adapter_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = listaPersona.get(recycler_view.getChildAdapterPosition(view));
                Citas citas = listaCitas.get(recycler_view.getChildAdapterPosition(view));

                Intent intent = new Intent(view.getContext(), appointment_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);
                bundle.putSerializable("citas", citas);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recycler_view.setAdapter(adapter_appointment);
    }

    public void onClick(View view) {
        Intent miIntent = null;
        Bundle bundle = new Bundle();

        switch (view.getId()) {
            case R.id.agregar:
                miIntent = new Intent(view.getContext(), insert_new_appointment.class);
                bundle.putSerializable("action", "insert");
                break;
        }
        if (miIntent != null) {
            miIntent.putExtras(bundle);
            view.getContext().startActivity(miIntent);
        }
    }


}
