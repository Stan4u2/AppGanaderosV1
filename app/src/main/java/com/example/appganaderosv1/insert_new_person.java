package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appganaderosv1.utilidades.Utilidades;

public class insert_new_person extends AppCompatActivity {

    EditText name_person, cellphone_person, address_person, extra_data_person;

    String button_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_person);

        name_person = findViewById(R.id.name_person);
        cellphone_person = findViewById(R.id.cellphone_person);
        address_person = findViewById(R.id.address_person);
        extra_data_person = findViewById(R.id.extra_data_person);

        class_that_came_from();
    }

    public void class_that_came_from(){
        Intent intent = getIntent();
        button_class = intent.getExtras().getString("button");
    }

    public void save_person (View view){

        boolean inserted = false;

        if(name_person.getText().toString().isEmpty() || cellphone_person.getText().toString().isEmpty() || address_person.getText().toString().isEmpty() || extra_data_person.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"¡¡Llene todos los campos!!",Toast.LENGTH_LONG).show();
        }else{
            inserted = registerPerson(name_person.getText().toString(), cellphone_person.getText().toString(), address_person.getText().toString(), extra_data_person.getText().toString());
        }

        if(inserted == true){
            Toast.makeText(insert_new_person.this, "Datos Insertados", Toast.LENGTH_LONG).show();
            switch (button_class){
                case "appointment":
                    insert_new_appointment.process = true;
                    break;
                case "purchase":
                    insert_new_purchases.process = true;
                    break;
            }
            finish();
        }else {
            Toast.makeText(insert_new_person.this, "¡¡Datos No Insertados!!", Toast.LENGTH_LONG).show();
        }

    }

    private boolean registerPerson(String name, String cellphone, String address, String extraData){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NOMBRE, name);
        values.put(Utilidades.CAMPO_TELEFONO, cellphone);
        values.put(Utilidades.CAMPO_DOMICILIO, address);
        values.put(Utilidades.CAMPO_DATOS_EXTRAS, extraData);

        Long idResult = db.insert(Utilidades.TABLA_PERSONA, Utilidades.CAMPO_ID_PERSONA, values);

        db.close();

        if(idResult == -1){
            return false;
        }else{
            switch (button_class){
                case "appointment":
                    insert_new_appointment.id_new_person = idResult.intValue();
                    break;
                case "purchase":
                    insert_new_purchases.id_new_person = idResult.intValue();
                    break;
            }

            return true;
        }
    }

    public void cancel_person (View view){
        switch (button_class){
            case "appointment":
                finish();
                break;
            case "purchase":
                finish();
                break;
        }
    }

}
