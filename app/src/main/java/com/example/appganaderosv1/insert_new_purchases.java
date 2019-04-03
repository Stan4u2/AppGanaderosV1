package com.example.appganaderosv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class insert_new_purchases extends AppCompatActivity {

    ImageButton cancelButton, add_person_purchase, select_date_purchase, savePurchase;
    TextView action_to_do, name_person_purchase, cellphone_person_purchase, address_person_purchase, extra_data_person_purchase, date_purchase;
    TextView number_animals_purchase, amount_to_pay;
    Button add_animal;
    Spinner spinner_person_purchase;

    int idPersonPurchase;

    public static int id_new_person;
    public static boolean process;
    public static boolean datePurchase;
    public static String DatePurchase;

    ArrayList<String> peopleList;
    ArrayList<Persona> peopleData;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_purchases);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);

        //Image Button
        add_person_purchase = findViewById(R.id.add_person_purchase);
        select_date_purchase = findViewById(R.id.select_date_purchase);
        savePurchase = findViewById(R.id.savePurchase);
        cancelButton = findViewById(R.id.cancelButton);

        //TextView
        action_to_do = findViewById(R.id.action_to_do);
        name_person_purchase = findViewById(R.id.name_person_purchase);
        cellphone_person_purchase = findViewById(R.id.cellphone_person_purchase);
        address_person_purchase = findViewById(R.id.address_person_purchase);
        extra_data_person_purchase = findViewById(R.id.extra_data_person_purchase);
        date_purchase = findViewById(R.id.date_purchase);
        number_animals_purchase = findViewById(R.id.number_animals_purchase);
        amount_to_pay = findViewById(R.id.amount_to_pay);

        //Button
        add_animal = findViewById(R.id.add_animal);

        //Spinner
        spinner_person_purchase = findViewById(R.id.spinner_person_purchase);

        consultListPeople_purchase();


    }

    public void onRestart(){
        super.onRestart();
        if(id_new_person != -1 && process == true) {

            consultListPeople_purchase();

            for (int i = 0; i < peopleData.size(); i++) {
                if(peopleData.get(i).getId_persona() == id_new_person){
                    spinner_person_purchase.setSelection(i+1);
                }
            }
            process = false;

        }

        if(datePurchase == true){
            datePurchase = false;
            date_purchase.setText(DatePurchase);
        }
    }

    private void consultListPeople_purchase() {
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
        db.close();

        obtainList_purchase();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, peopleList);

        spinner_person_purchase.setAdapter(adapter);

        spinner_person_purchase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if( position!= 0){

                    idPersonPurchase = peopleData.get(position-1).getId_persona();
                    name_person_purchase.setText(peopleData.get(position-1).getNombre());
                    cellphone_person_purchase.setText(peopleData.get(position-1).getTelefono());
                    address_person_purchase.setText(peopleData.get(position-1).getDomicilio());
                    extra_data_person_purchase.setText(peopleData.get(position-1).getDatos_extras());

                }else{

                    idPersonPurchase = 0;
                    name_person_purchase.setText("");
                    cellphone_person_purchase.setText("");
                    address_person_purchase.setText("");
                    extra_data_person_purchase.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void obtainList_purchase() {
        peopleList = new ArrayList<String>();
        peopleList.add("Seleccione:");

        for (int i = 0; i < peopleData.size(); i++) {
            peopleList.add(peopleData.get(i).getNombre());
        }
    }

    public void cancel_purchase(View view) {
        finish();
    }

    public void new_person_purchase(View view){
        Intent intent = null;
        if (view.getId() == R.id.add_person_purchase) {
            intent = new Intent(insert_new_purchases.this, insert_new_person.class);
            intent.putExtra("button", "purchase");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void select_date_purchase(View view){
        Intent intent = null;
        if (view.getId() == R.id.select_date_purchase) {
            intent = new Intent(insert_new_purchases.this, calendar.class);
            intent.putExtra("button", "purchase");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void insertNewAnimal(View view){
        Bundle bundle = new Bundle();
        Intent intent = null;
        if (view.getId() == R.id.add_animal) {
            intent = new Intent(insert_new_purchases.this, insert_new_animal.class);
            bundle.putSerializable("action", "insert");
        }

        if (intent != null) {
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
