package com.example.appganaderosv1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.utilidades.Utilidades;

public class people_details extends AppCompatActivity {

    Persona person = null;

    ConexionSQLiteHelper conn;

    EditText NamePerson, PhonePerson, AddressPerson, ExtraDataPerson;

    ImageButton cancel, edit, save;

    boolean editable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        NamePerson = findViewById(R.id.NamePerson);
        PhonePerson = findViewById(R.id.PhonePerson);
        AddressPerson = findViewById(R.id.AddressPerson);
        ExtraDataPerson = findViewById(R.id.ExtraDataPerson);

        cancel = findViewById(R.id.cancel);
        edit = findViewById(R.id.edit);
        save = findViewById(R.id.save);

        Bundle objectSent = getIntent().getExtras();


        if (objectSent != null) {
            person = (Persona) objectSent.getSerializable("persona");
            loadData();
            lockEditTexts();
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editable){
                    lockEditTexts();
                    loadData();
                    save.setClickable(false);
                }else{
                    unlockEditTexts();
                    save.setClickable(true);
                }
                editable = !editable;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(NamePerson.getText().toString(), PhonePerson.getText().toString(), AddressPerson.getText().toString(), ExtraDataPerson.getText().toString());
            }
        });
    }

    public void loadData(){
        NamePerson.setText(person.getNombre());
        PhonePerson.setText(person.getTelefono());
        AddressPerson.setText(person.getDomicilio());
        ExtraDataPerson.setText(person.getDatos_extras());
    }

    public void lockEditTexts(){
        NamePerson.setEnabled(false);
        PhonePerson.setEnabled(false);
        AddressPerson.setEnabled(false);
        ExtraDataPerson.setEnabled(false);
    }

    public void unlockEditTexts(){
        NamePerson.setEnabled(true);
        PhonePerson.setEnabled(true);
        AddressPerson.setEnabled(true);
        ExtraDataPerson.setEnabled(true);
    }

    public void cancel(View view) {

        if (
                !NamePerson.getText().toString().equals(person.getNombre())
                ||
                !PhonePerson.getText().toString().equals(person.getTelefono())
                ||
                !AddressPerson.getText().toString().equals(person.getDomicilio())
                ||
                !ExtraDataPerson.getText().toString().equals(person.getDatos_extras())
        ) {
            Toast.makeText(getApplicationContext(), "No se modifico ningun dato.", Toast.LENGTH_LONG).show();
        }

        finish();

    }

    public void save(String Name, String Phone, String Address, String ExtraData){
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        String[] id_person = {String.valueOf(person.getId_persona())};

        values.put(Utilidades.CAMPO_NOMBRE, Name);
        values.put(Utilidades.CAMPO_TELEFONO, Phone);
        values.put(Utilidades.CAMPO_DOMICILIO, Address);
        values.put(Utilidades.CAMPO_DATOS_EXTRAS, ExtraData);

        int updated = db.update(Utilidades.TABLA_PERSONA, values, Utilidades.CAMPO_ID_PERSONA + " = ?", id_person);

        db.close();

        if(updated >= 1){
            Toast.makeText(getApplicationContext(), "Datos Modificados.", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
