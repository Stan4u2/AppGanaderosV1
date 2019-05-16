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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.appganaderosv1.Adapter.Adapter_people;
import com.example.appganaderosv1.ConexionSQLiteHelper;
import com.example.appganaderosv1.R;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.people_details;

import java.util.ArrayList;

public class PeopleFragment extends Fragment {

    ConexionSQLiteHelper conn;

    RecyclerView recycler_view;

    EditText SearchText;

    //RecyclerView Data
    ArrayList<Persona> listaPersona;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_people, container, false);

        conn = new ConexionSQLiteHelper(getContext(), "bd_ganado", null, 2);

        SearchText = view.findViewById(R.id.SearchText);

        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));

        listaPersona = new ArrayList<>();

        fillList();

        SearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fillList();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();
        fillList();
    }

    private void fillList() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Persona persona = null;

        listaPersona = new ArrayList<Persona>();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "*" +
                        " FROM " +
                        "persona" +
                        " WHERE " +
                        "nombre like '%" + SearchText.getText().toString() + "%'" +
                        " OR " +
                        "telefono like '%" + SearchText.getText().toString() + "%'" +
                        " OR " +
                        "domicilio like '%" + SearchText.getText().toString() + "%'", null);

        while (cursor.moveToNext()) {

            persona = new Persona();
            persona.setId_persona(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));
            persona.setDomicilio(cursor.getString(3));
            persona.setDatos_extras(cursor.getString(4));

            listaPersona.add(persona);
        }

        cursor.close();
        db.close();

        Adapter_people adapter_people = new Adapter_people(listaPersona);

        adapter_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = listaPersona.get(recycler_view.getChildAdapterPosition(view));

                Intent intent = new Intent(view.getContext(), people_details.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("persona", persona);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recycler_view.setAdapter(adapter_people);
    }
}
