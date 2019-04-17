package com.example.appganaderosv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class insert_new_sales extends AppCompatActivity {

    ImageButton cancelButton, add_person_sale, select_date_sale, saveSale;
    TextView action_to_do, name_person_sale, cellphone_person_sale, address_person_sale, extra_data_person_sale, date_sale;
    TextView number_animals_sale, amount_to_charge;
    Button add_animal;
    Spinner spinner_person_sale;
    RecyclerView recycler_view;

    int idPersonSale;

    public static int id_new_person;
    public static boolean process;
    public static boolean dateSale;
    public static String DateSale;

    public static String action;

    ArrayList<String> peopleList;
    ArrayList<Persona> peopleData;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_sales);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //Image Button
        cancelButton = findViewById(R.id.cancelButton);
        add_person_sale = findViewById(R.id.add_person_sale);
        select_date_sale = findViewById(R.id.select_date_sale);
        saveSale = findViewById(R.id.saveSale);

        //TextView
        action_to_do = findViewById(R.id.action_to_do);
        name_person_sale = findViewById(R.id.name_person_sale);
        cellphone_person_sale = findViewById(R.id.cellphone_person_sale);
        address_person_sale = findViewById(R.id.address_person_sale);
        extra_data_person_sale = findViewById(R.id.extra_data_person_sale);
        date_sale = findViewById(R.id.date_sale);
        number_animals_sale = findViewById(R.id.number_animals_sale);
        amount_to_charge = findViewById(R.id.amount_to_charge);

        //Button
        add_animal = findViewById(R.id.add_animal);

        //Spinner
        spinner_person_sale = findViewById(R.id.spinner_person_sale);

        //RecyclerView
        recycler_view = findViewById(R.id.recycler_view);


        consultListPeopleSale();

        Bundle objectSent = getIntent().getExtras();

        if (objectSent != null) {
            action = objectSent.getSerializable("action").toString();

            switch (action) {
                case "insert":
                    action_to_do.setText("Nueva Venta");
                    break;
                case "modifie":
                    action_to_do.setText("Modificar Venta");
                    break;
            }
        }

    }

    public void onRestart(){
        super.onRestart();

        if (id_new_person != -1 && process) {

            consultListPeopleSale();

            for (int i = 0; i < peopleData.size(); i++) {
                if (peopleData.get(i).getId_persona() == id_new_person) {
                    spinner_person_sale.setSelection(i + 1);
                }
            }
            process = false;

        }

        if (dateSale) {
            dateSale = false;
            date_sale.setText(DateSale);
        }
    }

    private void consultListPeopleSale() {

        SQLiteDatabase db = conn.getWritableDatabase();
        Persona persona = null;
        peopleData = new ArrayList<Persona>();

        //Select * from personas;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERSONA + " ORDER BY " + Utilidades.CAMPO_NOMBRE, null);
        while (cursor.moveToNext()) {
            persona = new Persona();
            persona.setId_persona(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));
            persona.setDomicilio(cursor.getString(3));
            persona.setDatos_extras(cursor.getString(4));

            peopleData.add(persona);
        }
        cursor.close();
        db.close();

        obtainList_sale();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, peopleList);

        spinner_person_sale.setAdapter(adapter);

        spinner_person_sale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    idPersonSale = peopleData.get(position - 1).getId_persona();
                    name_person_sale.setText(peopleData.get(position - 1).getNombre());
                    cellphone_person_sale.setText(peopleData.get(position - 1).getTelefono());
                    address_person_sale.setText(peopleData.get(position - 1).getDomicilio());
                    extra_data_person_sale.setText(peopleData.get(position - 1).getDatos_extras());

                } else {

                    idPersonSale = 0;
                    name_person_sale.setText("");
                    cellphone_person_sale.setText("");
                    address_person_sale.setText("");
                    extra_data_person_sale.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void obtainList_sale() {

        peopleList = new ArrayList<String>();
        peopleList.add("Seleccione:");

        for (int i = 0; i < peopleData.size(); i++) {
            peopleList.add(peopleData.get(i).getNombre());
        }

    }

    public void new_person_sale(View view) {
        Intent intent = null;
        if (view.getId() == R.id.add_person_sale) {
            intent = new Intent(getApplicationContext(), insert_new_person.class);
            intent.putExtra("button", "sale");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void select_date_sale(View view) {
        Intent intent = null;
        if (view.getId() == R.id.select_date_sale) {
            intent = new Intent(getApplicationContext(), calendar.class);
            intent.putExtra("button", "sale");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void select_animal(View view){
        Intent intent = null;
        switch (action) {
            case "insert":
                if(view.getId() == R.id.add_animal){
                    intent = new Intent(getApplicationContext(),select_animal.class);
                    intent.putExtra("button", "sale");
                }
                break;
            case "modifie":
                action_to_do.setText("Modificar Venta");
                break;
        }
    }

}
