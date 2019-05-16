package com.example.appganaderosv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {
    //This variable is used for further methods to know if the user signed in is an administrator
    public static Boolean administrator;

    TextView Usuario, Contrasena;

    public static String userName;


    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Usuario = (TextView) findViewById(R.id.Usuario);
        Contrasena = (TextView) findViewById(R.id.Contrasena);
    }

    public void onResume(){
        super.onResume();
        limpiar();
    }

    public void verificarUsuarios(View view) {
        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_ganado", null, 2);
        SQLiteDatabase db = conn.getReadableDatabase();

        administrator = null;

        String[] parametros = {Usuario.getText().toString(), Contrasena.getText().toString()};
        String[] campo = {Utilidades.CAMPO_TIPO_USUARIO};

        try {
            boolean Login = false;
            Cursor cursor = db.query(
                    Utilidades.TABLA_USUARIO,
                    campo,
                    Utilidades.CAMPO_USUARIO + "= ? AND " + Utilidades.CAMPO_CONTRASENA + "= ?",
                    parametros,
                    null, null, null
            );

            cursor.moveToFirst();

            if ((cursor.getString(0).equals("Normal"))) {
                administrator = false;
                Toast.makeText(getApplicationContext(), "Sesion usuario normal iniciada", Toast.LENGTH_LONG).show();
                Login = true;
            } else if ((cursor.getString(0).equals("Administrador"))) {
                administrator = true;
                Toast.makeText(getApplicationContext(), "Sesion administrador iniciada", Toast.LENGTH_LONG).show();
                Login = true;
            }


            if (Login == true) {
                userName = Usuario.getText().toString();
                Intent miIntent = new Intent(MainActivity.this, homeActivity.class);
                startActivity(miIntent);
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Usuario o contraseña no valido", Toast.LENGTH_LONG).show();
            limpiar();
        }
    }

    private void limpiar() {
        Usuario.setText("");
        Contrasena.setText("");
    }
}
