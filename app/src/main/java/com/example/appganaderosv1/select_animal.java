package com.example.appganaderosv1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class select_animal extends AppCompatActivity {

    EditText total_cobrar, tare_sale;
    TextView name_owner, cellphone_owner, address_owner, extra_data_owner;
    TextView typeAnimal, raceAnimal, weightAnimal, priceAnimal, tareAnimal, earingNumberAnimal, total_pagado, total_cobrar_CT;
    Spinner spinner_owner, spinner_animal;
    ImageButton saveSale;

    int idOwner;
    
    //ArrayLists Spinner Person
    ArrayList<String> peopleList;
    ArrayList<Persona> peopleData;

    //ArrayLists Spinner Animals
    ArrayList<String> animalList;
    ArrayList<CompraDetalle> animalData;
    ArrayList<Ganado> animalType;
    ArrayList<Raza> animalRace;

    ConexionSQLiteHelper conn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_animal);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //EditText
        total_cobrar = findViewById(R.id.total_cobrar);
        tare_sale = findViewById(R.id.tare_sale);

        //TextView
        name_owner = findViewById(R.id.name_owner);
        cellphone_owner = findViewById(R.id.cellphone_owner);
        address_owner = findViewById(R.id.address_owner);
        extra_data_owner = findViewById(R.id.extra_data_owner);

        typeAnimal = findViewById(R.id.typeAnimal);
        raceAnimal = findViewById(R.id.raceAnimal);
        weightAnimal = findViewById(R.id.weightAnimal);
        priceAnimal = findViewById(R.id.priceAnimal);
        tareAnimal = findViewById(R.id.tareAnimal);
        earingNumberAnimal = findViewById(R.id.earingNumberAnimal);
        total_pagado = findViewById(R.id.total_pagado);
        total_cobrar_CT = findViewById(R.id.total_cobrar_CT);

        //Spinner
        spinner_owner = findViewById(R.id.spinner_owner);
        spinner_animal = findViewById(R.id.spinner_animal);
        
        //ImageButton
        saveSale = findViewById(R.id.saveSale);
        
        consultListOwners();
        consultListAnimals();
    }

    private void consultListOwners() {

        SQLiteDatabase db = conn.getWritableDatabase();
        Persona persona = null;
        peopleData = new ArrayList<Persona>();


        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "p.*" +
                        " FROM " +
                        Utilidades.TABLA_PERSONA + " p, " +
                        Utilidades.TABLA_COMPRAS + " c " +
                        " WHERE " +
                        Utilidades.CAMPO_PERSONA_COMPRO + " = " + Utilidades.CAMPO_ID_PERSONA +
                        " ORDER BY " +
                        Utilidades.CAMPO_NOMBRE, null
        );
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

        obtainList_owner();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, peopleList);

        spinner_owner.setAdapter(adapter);

        spinner_owner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    idOwner = peopleData.get(position - 1).getId_persona();
                    name_owner.setText(peopleData.get(position - 1).getNombre());
                    cellphone_owner.setText(peopleData.get(position - 1).getTelefono());
                    address_owner.setText(peopleData.get(position - 1).getDomicilio());
                    extra_data_owner.setText(peopleData.get(position - 1).getDatos_extras());

                    consultListAnimals();

                } else {

                    idOwner = 0;
                    name_owner.setText("");
                    cellphone_owner.setText("");
                    address_owner.setText("");
                    extra_data_owner.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void obtainList_owner() {
        peopleList = new ArrayList<String>();
        peopleList.add("Seleccione:");

        for (int i = 0; i < peopleData.size(); i++) {
            peopleList.add(peopleData.get(i).getNombre());
        }
    }


    private void consultListAnimals() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {String.valueOf(idOwner)};

        CompraDetalle compraDetalle = null;
        Ganado ganado = null;
        Raza raza = null;

        animalData = new ArrayList<CompraDetalle>();
        animalType = new ArrayList<Ganado>();
        animalRace = new ArrayList<Raza>();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_ID_COMPRA_DETALLE + ", " +
                        Utilidades.CAMPO_GANADO + ", " +
                        Utilidades.CAMPO_RAZA + ", " +
                        Utilidades.CAMPO_PESO + ", " +
                        Utilidades.CAMPO_PRECIO + ", " +
                        Utilidades.CAMPO_TARA + ", " +
                        Utilidades.CAMPO_TOTAL_PAGAR + ", " +
                        Utilidades.CAMPO_NUMERO_ARETE + ", " +
                        Utilidades.CAMPO_COMPRA + ", " +

                        Utilidades.CAMPO_ID_GANADO + ", " +
                        Utilidades.CAMPO_TIPO_GANADO + ", " +

                        Utilidades.CAMPO_ID_RAZA + ", " +
                        Utilidades.CAMPO_TIPO_RAZA +
                        " FROM " +
                        Utilidades.TABLA_COMPRAS + ", " +
                        Utilidades.TABLA_COMPRA_DETALLE + ", " +
                        Utilidades.TABLA_GANADO + ", " +
                        Utilidades.TABLA_RAZA +
                        " WHERE " +
                        Utilidades.CAMPO_GANADO + " = " + Utilidades.CAMPO_ID_GANADO +
                        " AND " +
                        Utilidades.CAMPO_RAZA + " = " + Utilidades.CAMPO_ID_RAZA +
                        " AND " +
                        Utilidades.CAMPO_COMPRA + " = " + Utilidades.CAMPO_ID_COMPRA +
                        " AND " +
                        Utilidades.CAMPO_PERSONA_COMPRO + " = ?", parameters
        );

        while (cursor.moveToNext()) {
            compraDetalle = new CompraDetalle();
            compraDetalle.setId_compra_detalle(cursor.getInt(0));
            compraDetalle.setGanado(cursor.getInt(1));
            compraDetalle.setRaza(cursor.getInt(2));
            compraDetalle.setPeso(cursor.getDouble(3));
            compraDetalle.setPrecio(cursor.getDouble(4));
            compraDetalle.setTara(cursor.getInt(5));
            compraDetalle.setTotal(cursor.getDouble(6));
            compraDetalle.setNumero_arete(cursor.getInt(7));
            compraDetalle.setCompra(cursor.getInt(8));

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(9));
            ganado.setTipo_ganado(cursor.getString(10));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(11));
            raza.setTipo_raza(cursor.getString(12));

            animalData.add(compraDetalle);
            animalType.add(ganado);
            animalRace.add(raza);
        }

        cursor.close();
        db.close();

        obtainListAnimals();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, animalList);

        spinner_animal.setAdapter(adapter);

        spinner_animal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    typeAnimal.setText(animalType.get(position - 1).getTipo_ganado());
                    raceAnimal.setText(animalRace.get(position - 1).getTipo_raza());
                    weightAnimal.setText(animalData.get(position - 1).getPeso().toString());
                    priceAnimal.setText(animalData.get(position - 1).getPrecio().toString());
                    tareAnimal.setText(animalData.get(position - 1).getTara().toString());
                    earingNumberAnimal.setText(animalData.get(position - 1).getNumero_arete().toString());
                    total_pagado.setText(animalData.get(position - 1).getTotal().toString());

                } else {

                    typeAnimal.setText("");
                    raceAnimal.setText("");
                    weightAnimal.setText("");
                    priceAnimal.setText("");
                    tareAnimal.setText("");
                    earingNumberAnimal.setText("");
                    total_pagado.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void obtainListAnimals() {

        animalList = new ArrayList<String>();
        animalList.add("Seleccione:");

        for (int i = 0; i < animalData.size(); i++) {
            animalList.add(animalType.get(i).getTipo_ganado() +" " + animalRace.get(i).getTipo_raza() + " " + animalData.get(i).getPeso() + "kg");
        }

    }

}
