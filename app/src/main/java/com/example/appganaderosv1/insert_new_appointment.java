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

import com.example.appganaderosv1.entidades.Citas;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class insert_new_appointment extends AppCompatActivity {

    public static boolean process;
    public static boolean dateAppointment;
    public static int id_new_person;
    public static String DateAppointment;
    int idPersonAppointment;
    static String action;
    int idAppointmentModifie;

    ImageButton cancelButton, add_person_appointment, select_date_appointment;
    TextView action_to_do, name_person_appointment, cellphone_person_appointment, address_person_appointment, extra_data_person_appointment, date_appointment;
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
        action_to_do = findViewById(R.id.action_to_do);
        name_person_appointment = findViewById(R.id.name_person_appointment);
        cellphone_person_appointment = findViewById(R.id.cellphone_person_appointment);
        address_person_appointment = findViewById(R.id.address_person_appointment);
        extra_data_person_appointment = findViewById(R.id.extra_data_person_appointment);
        date_appointment = findViewById(R.id.date_appointment);

        //Edit Texts
        number_animals_appointment = findViewById(R.id.number_animals_appointment);
        extra_data_appointment = findViewById(R.id.extra_data_appointment);

        consultListPeople_appointment();

        Bundle actionToDo = getIntent().getExtras();

        if(actionToDo != null){

            action = actionToDo.getSerializable("action").toString();

            switch(action){
                case "insert":
                    action_to_do.setText("Nueva Cita");
                    break;

                case "modifie":
                    action_to_do.setText("Modificar Cita");
                    loadData();
                    break;
            }

        }


    }

    private void loadData() {

        Persona person = null;
        Citas citas = null;

        Bundle data = getIntent().getExtras();

        if(data != null){
            System.out.println("Hola");
            person = (Persona) data.getSerializable("persona");
            citas = (Citas) data.getSerializable("citas");

            for(int x = 1; x <= spinner_person_appointment.getCount()-1; x++){
                if(spinner_person_appointment.getItemAtPosition(x).equals(person.getId_persona() + " - " + person.getNombre())){
                    spinner_person_appointment.setSelection(x);
                    break;
                }
            }

            idAppointmentModifie = citas.getId_citas();

            date_appointment.setText(citas.getFecha());
            number_animals_appointment.setText(citas.getCantidad_ganado().toString());
            extra_data_appointment.setText(citas.getDatos());

        }
    }

    //When you start another activity and come back this method will be executed
    public void onRestart(){
        super.onRestart();

        System.out.println("Id de la nueva persona " + id_new_person);
        if(id_new_person != -1 && process == true) {

            consultListPeople_appointment();
            //So after restarting it will check what person was just inserted and it will select it automatically
            for(int x = 1; x <= spinner_person_appointment.getCount()-1; x++){

                if(spinner_person_appointment.getItemAtPosition(x).equals(peopleData.get(x-1).getId_persona() + " - " + peopleData.get(x-1).getNombre())){
                    spinner_person_appointment.setSelection(x);
                    break;
                }
            }
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
            peopleList.add(peopleData.get(i).getId_persona() + " - " + peopleData.get(i).getNombre());
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
        boolean complete = false;
        if(
           spinner_person_appointment.getSelectedItemId() == 0 ||
           date_appointment.getText().toString().isEmpty()||
           number_animals_appointment.getText().toString().isEmpty()||
           extra_data_appointment.getText().toString().isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "¡¡Campos Vacios!!", Toast.LENGTH_LONG).show();
        }else{
            if(action == "insert") {
                boolean inserted = insertNewAppointment(
                        Integer.valueOf(number_animals_appointment.getText().toString()),
                        extra_data_appointment.getText().toString(),
                        date_appointment.getText().toString(),
                        idPersonAppointment);

                if(inserted == true){
                    Toast.makeText(getApplicationContext(), "Datos Insertados", Toast.LENGTH_LONG).show();
                    complete = true;
                }else {
                    Toast.makeText(getApplicationContext(), "¡¡Datos No Insertados!!", Toast.LENGTH_LONG).show();
                }


            }else if(action == "modifie"){
                System.out.println("Entro");
                int modified = modifieAppointment(
                        Integer.valueOf(number_animals_appointment.getText().toString()),
                        extra_data_appointment.getText().toString(),
                        date_appointment.getText().toString(),
                        idPersonAppointment
                );

                if(modified == 1){
                    Toast.makeText(getApplicationContext(), "Se han actualizado los datos", Toast.LENGTH_LONG).show();
                    complete = true;
                }else{
                    Toast.makeText(getApplicationContext(), "Datos no actualizados", Toast.LENGTH_LONG).show();
                }

            }

            if(complete == true){
                finish();
            }
        }
    }


    private boolean insertNewAppointment(int numberAnimals, String data, String date, int idPerson) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_RESPALDO_CITAS, 0);
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

    private int modifieAppointment(int numberAnimals, String data, String date, int idPerson){
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_appointment = {String.valueOf(idAppointmentModifie)};

        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_CANTIDAD_GANADO, numberAnimals);
        values.put(Utilidades.CAMPO_DATOS, data);
        values.put(Utilidades.CAMPO_FECHA_CITAS, date);
        values.put(Utilidades.CAMPO_PERSONA_CITA, idPerson);

        int updated = db.update(Utilidades.TABLA_CITAS, values, Utilidades.CAMPO_ID_CITAS + " = ?", id_appointment);

        db.close();

        return updated;
    }
}




