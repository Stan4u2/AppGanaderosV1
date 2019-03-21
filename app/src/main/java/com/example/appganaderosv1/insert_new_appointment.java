package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class insert_new_appointment extends AppCompatActivity {

    public static boolean process;
    public static boolean dateAppointment;
    public static int id_new_person;
    public static String DateAppointment;
    int idPersonAppointment;

    ImageButton cancelButton, add_person_appointment, select_date_appointment;
    TextView name_person_appointment, cellphone_person_appointment, address_person_appointment, extra_data_person_appointment, date_appointment;
    EditText number_animals_appointment, extra_data_appointment;
    Spinner spinner_person_appointment;

    ArrayList<String> peopleList;
    ArrayList<Persona> peopleData;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_appointment);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);

        //Image Buttons
        cancelButton = findViewById(R.id.cancelButton);
        add_person_appointment = findViewById(R.id.add_person_appointment);
        select_date_appointment = findViewById(R.id.select_date_appointment);

        //Spinners
        spinner_person_appointment = findViewById(R.id.spinner_person_appointment);

        //Textviews
        name_person_appointment = findViewById(R.id.name_person_appointment);
        cellphone_person_appointment = findViewById(R.id.cellphone_person_appointment);
        address_person_appointment = findViewById(R.id.address_person_appointment);
        extra_data_person_appointment = findViewById(R.id.extra_data_person_appointment);
        date_appointment = findViewById(R.id.date_appointment);

        //Edit Texts
        number_animals_appointment = findViewById(R.id.number_animals_appointment);
        extra_data_appointment = findViewById(R.id.extra_data_appointment);

        consultListPeople_appointment();
    }

    //When you start another activity and come back this method will be executed
    public void onRestart(){
        super.onRestart();

        System.out.println("Id de la nueva persona " + id_new_person);
        if(id_new_person != -1 && process == true) {

            consultListPeople_appointment();
            spinner_person_appointment.setSelection(id_new_person);
            process = false;

        }

        if(dateAppointment == true){
            dateAppointment = false;
            date_appointment.setText(DateAppointment);
        }
    }


    public void consultListPeople_appointment() {
        SQLiteDatabase db = conn.getWritableDatabase();
        Persona persona = null;
        peopleData = new ArrayList<Persona>();

        //Select * from personas;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERSONA, null);
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

        obtainList_appointment();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, peopleList);

        spinner_person_appointment.setAdapter(adapter);

        spinner_person_appointment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if( position!= 0){

                    idPersonAppointment = peopleData.get(position-1).getId_persona();
                    name_person_appointment.setText(peopleData.get(position-1).getNombre());
                    cellphone_person_appointment.setText(peopleData.get(position-1).getTelefono());
                    address_person_appointment.setText(peopleData.get(position-1).getDomicilio());
                    extra_data_person_appointment.setText(peopleData.get(position-1).getDatos_extras());

                }else{

                    idPersonAppointment = 0;
                    name_person_appointment.setText("");
                    cellphone_person_appointment.setText("");
                    address_person_appointment.setText("");
                    extra_data_person_appointment.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void obtainList_appointment() {
        peopleList = new ArrayList<String>();
        peopleList.add("Seleccione:");

        for (int i = 0; i < peopleData.size(); i++) {
            peopleList.add(peopleData.get(i).getNombre());
        }
    }


    public void cancel_appointment(View view) {
        Intent intent = null;
        if (view.getId() == R.id.cancelButton) {
            intent = new Intent(insert_new_appointment.this, homeActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }


    public void new_person_appointment(View view){
        Intent intent = null;
        if (view.getId() == R.id.add_person_appointment) {
            intent = new Intent(insert_new_appointment.this, insert_new_person.class);
            intent.putExtra("button", "appointment");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }


    public void select_date_appointment(View view){
        Intent intent = null;
        if (view.getId() == R.id.select_date_appointment) {
            intent = new Intent(insert_new_appointment.this, calendar.class);
            intent.putExtra("button", "appointment");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }


    public void saveAppointmentDB(View view){
        boolean inserted = false;
        if(
           spinner_person_appointment.getSelectedItemId() == 0 ||
           date_appointment.getText().toString().isEmpty()||
           number_animals_appointment.getText().toString().isEmpty()||
           extra_data_appointment.getText().toString().isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "¡¡Campos Vacios!!", Toast.LENGTH_LONG).show();
        }else{
            inserted = insertNewAppointment(
                    Integer.valueOf(number_animals_appointment.getText().toString()),
                    extra_data_appointment.getText().toString(),
                    date_appointment.getText().toString(),
                    idPersonAppointment);
        }

        if(inserted == true){
            Toast.makeText(getApplicationContext(), "Datos Insertados", Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "¡¡Datos No Insertados!!", Toast.LENGTH_LONG).show();
        }
    }


    private boolean insertNewAppointment(int numberAnimals, String data, String date, int idPerson) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_CANTIDAD_GANADO, numberAnimals);
        values.put(Utilidades.CAMPO_DATOS, data);
        values.put(Utilidades.CAMPO_FECHA_CITAS, date);
        values.put(Utilidades.CAMPO_PERSONA_CITA, idPerson);

        Long idResult = db.insert(Utilidades.TABLA_CITAS, Utilidades.CAMPO_ID_CITAS, values);

        db.close();

        if(idResult == -1){
            return false;
        }else{
            insert_new_appointment.id_new_person = idResult.intValue();
            return true;
        }
    }
}




