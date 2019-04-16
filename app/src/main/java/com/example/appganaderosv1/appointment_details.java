package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.entidades.Citas;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.utilidades.Utilidades;

public class appointment_details extends AppCompatActivity {

    TextView name_person_appointment, cellphone_person_appointment, address_person_appointment;
    TextView extra_data_person_appointment, date_appointment, number_animals_appointment, extra_data_appointment;

    Persona person = null;
    Citas citas = null;

    public static int id_appointment;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        name_person_appointment = findViewById(R.id.name_person_appointment);
        cellphone_person_appointment = findViewById(R.id.cellphone_person_appointment);
        address_person_appointment = findViewById(R.id.address_person_appointment);
        extra_data_person_appointment = findViewById(R.id.extra_data_person_appointment);
        date_appointment = findViewById(R.id.date_appointment);
        number_animals_appointment = findViewById(R.id.number_animals_appointment);
        extra_data_appointment = findViewById(R.id.extra_data_appointment);

        Bundle objectSent = getIntent().getExtras();


        if(objectSent != null){
            person = (Persona) objectSent.getSerializable("persona");
            citas = (Citas) objectSent.getSerializable("citas");

            name_person_appointment.setText(person.getNombre());
            cellphone_person_appointment.setText(person.getTelefono());
            address_person_appointment.setText(person.getDomicilio());
            extra_data_person_appointment.setText(person.getDatos_extras());

            id_appointment = citas.getId_citas();
            date_appointment.setText(citas.getFecha());
            number_animals_appointment.setText(citas.getCantidad_ganado().toString());
            extra_data_appointment.setText(citas.getDatos());

        }

    }

    public void onRestart(){
        super.onRestart();

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parameters = {String.valueOf(id_appointment)};

        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            Utilidades.CAMPO_ID_PERSONA + ", " +//0
                            Utilidades.CAMPO_NOMBRE + ", " +//1
                            Utilidades.CAMPO_TELEFONO + ", " +//2
                            Utilidades.CAMPO_DOMICILIO + ", " +//3
                            Utilidades.CAMPO_DATOS_EXTRAS + ", " +//4

                            Utilidades.CAMPO_ID_CITAS + ", " +//5
                            Utilidades.CAMPO_FECHA_CITAS + ", " +//6
                            Utilidades.CAMPO_CANTIDAD_GANADO + ", " +//7
                            Utilidades.CAMPO_DATOS +//8
                        " FROM " +
                            Utilidades.TABLA_CITAS + ", " +
                            Utilidades.TABLA_PERSONA +
                        " WHERE " +
                            Utilidades.CAMPO_PERSONA_CITA + " = " + Utilidades.CAMPO_ID_PERSONA +
                            " AND " +
                            Utilidades.CAMPO_ID_CITAS + " = ?", parameters);

            cursor.moveToFirst();

            //So here I set all the text the modefied values.
            name_person_appointment.setText(cursor.getString(1));
            cellphone_person_appointment.setText(cursor.getString(2));
            address_person_appointment.setText(cursor.getString(3));
            extra_data_person_appointment.setText(cursor.getString(4));

            date_appointment.setText(cursor.getString(6));
            number_animals_appointment.setText(cursor.getString(7));
            extra_data_appointment.setText(cursor.getString(8));

            //And here I set the values to the objects if the user wants to modifie it again
            person = new Persona();
            person.setId_persona(cursor.getInt(0));
            person.setNombre(cursor.getString(1));
            person.setTelefono(cursor.getString(2));
            person.setDomicilio(cursor.getString(3));
            person.setDatos_extras(cursor.getString(4));

            citas = new Citas();
            citas.setId_citas(cursor.getInt(5));
            citas.setFecha(cursor.getString(6));
            citas.setCantidad_ganado(cursor.getInt(7));
            citas.setDatos(cursor.getString(8));

            cursor.close();
            db.close();


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteAppointment(View view){
        //In this method I don´t really delete the data, i just send it tto the garbage can.
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_appointment = {String.valueOf(citas.getId_citas())};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_RESPALDO_CITAS, 1);

        int updated = db.update(Utilidades.TABLA_CITAS, values, Utilidades.CAMPO_ID_CITAS + " = ?", id_appointment);

        if (updated == 1){
            Toast.makeText(getApplicationContext(), "Se ha mandado al bote de basura.", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Ha ocurrdio un error.", Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    public void modifieAppointment(View view){
        Intent intent = new Intent(view.getContext(), insert_new_appointment.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("action", "modifie");
        bundle.putSerializable("persona", person);
        bundle.putSerializable("citas", citas);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
