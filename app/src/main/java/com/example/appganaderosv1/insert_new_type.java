package com.example.appganaderosv1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appganaderosv1.utilidades.Utilidades;

public class insert_new_type extends AppCompatActivity {

    ConexionSQLiteHelper conn;

    EditText typeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_type);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*.50), (int) (height*.50));

        typeEditText = findViewById(R.id.typeEditText);
    }

    public void saveTypeAnimal(View view){
        SQLiteDatabase db = conn.getReadableDatabase();

        String type = typeEditText.getText().toString();

        Boolean noData;

        if(type.isEmpty()){
            Toast.makeText(getApplicationContext(), "¡Campo Vacio!", Toast.LENGTH_LONG).show();
        }else {
            try{
                //These first lines of code are used to compare if the race that is going to be inserted is already in the DB
                //SELECT * FROM raza WHERE tipo_ganado LIKE 'A%';
                Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GANADO + " WHERE " + Utilidades.CAMPO_TIPO_GANADO + " LIKE '"+ type +"%'", null);

                if(cursor.getCount() <= 0){
                    cursor.close();
                    noData = true;
                }else{
                    cursor.close();
                    noData = false;
                    Toast.makeText(getApplicationContext(), "Esa raza ya existe", Toast.LENGTH_LONG).show();
                }

                if(noData == true){
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.CAMPO_TIPO_GANADO, type);

                    Long idResult = db.insert(Utilidades.TABLA_GANADO, Utilidades.CAMPO_ID_GANADO, values);

                    db.close();

                    if(idResult == -1){
                        Toast.makeText(getApplicationContext(), "¡Datos No Insertados!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Datos Insertados", Toast.LENGTH_LONG).show();
                        insert_new_animal.id_new_type = idResult.intValue();
                        insert_new_animal.process_type = true;
                        finish();
                    }
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cancel(View view){
        finish();
    }
}
