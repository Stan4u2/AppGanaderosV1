package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
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

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);

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

            date_appointment.setText(citas.getFecha());
            number_animals_appointment.setText(citas.getCantidad_ganado().toString());
            extra_data_appointment.setText(citas.getDatos());

        }

    }

    public void deleteAppointment(View view){
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_appointment = {String.valueOf(citas.getId_citas())};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_RESPALDO_CITAS, 1);

        int updated = db.update(Utilidades.TABLA_CITAS, values, Utilidades.CAMPO_ID_CITAS + " = ?", id_appointment);

        if (updated == 1){
            Toast.makeText(getApplicationContext(), "Se ha mandado al bote de basura.", Toast.LENGTH_LONG).show();
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
