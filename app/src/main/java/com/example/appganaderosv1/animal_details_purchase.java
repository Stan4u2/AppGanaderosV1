package com.example.appganaderosv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.utilidades.Utilidades;

public class animal_details_purchase extends AppCompatActivity {

    TextView typeAnimal, raceAnimal, weightAnimal, priceAnimal, tareAnimal, earingNumberAnimal, total;

    CompraDetalle compraDetalle = null;
    Ganado ganado = null;
    Raza raza = null;

    static int id_animal_modifie;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details_purchase);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);

        typeAnimal = findViewById(R.id.typeAnimal);
        raceAnimal = findViewById(R.id.raceAnimal);
        weightAnimal = findViewById(R.id.weightAnimal);
        priceAnimal = findViewById(R.id.priceAnimal);
        tareAnimal = findViewById(R.id.tareAnimal);
        earingNumberAnimal = findViewById(R.id.earingNumberAnimal);
        total = findViewById(R.id.total);

        Bundle objectSent = getIntent().getExtras();

        if(objectSent != null){
            compraDetalle = (CompraDetalle) objectSent.getSerializable("compraDetalle");
            ganado = (Ganado) objectSent.getSerializable("ganado");
            raza = (Raza) objectSent.getSerializable("raza");

            id_animal_modifie = compraDetalle.getId_compra_detalle();
            typeAnimal.setText(ganado.getTipo_ganado());
            raceAnimal.setText(raza.getTipo_raza());
            weightAnimal.setText(compraDetalle.getPeso().toString());
            priceAnimal.setText(compraDetalle.getPrecio().toString());
            tareAnimal.setText(compraDetalle.getTara().toString());
            earingNumberAnimal.setText(compraDetalle.getNumero_arete().toString());
            total.setText(compraDetalle.getTotal().toString());

        }

    }

    public void onRestart(){
        super.onRestart();

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] id_animal = {String.valueOf(id_animal_modifie)};

        try{
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

                            Utilidades.CAMPO_ID_GANADO + ", " +
                            Utilidades.CAMPO_TIPO_GANADO + ", " +

                            Utilidades.CAMPO_ID_RAZA + ", " +
                            Utilidades.CAMPO_TIPO_RAZA +
                            " FROM " +
                            Utilidades.TABLA_COMPRA_DETALLE + ", " +
                            Utilidades.TABLA_GANADO + ", " +
                            Utilidades.TABLA_RAZA +
                            " WHERE " +
                            Utilidades.CAMPO_GANADO + " = " + Utilidades.CAMPO_ID_GANADO +
                            " AND " +
                            Utilidades.CAMPO_RAZA + " = " + Utilidades.CAMPO_ID_RAZA +
                            " AND " +
                            Utilidades.CAMPO_ID_COMPRA_DETALLE + " = ?" , id_animal);

            cursor.moveToFirst();

            //Here I set the values to the objects if the user wants to modifie it again
            compraDetalle = new CompraDetalle();
            compraDetalle.setId_compra_detalle(cursor.getInt(0));
            compraDetalle.setGanado(cursor.getInt(1));
            compraDetalle.setRaza(cursor.getInt(2));
            compraDetalle.setPeso(cursor.getDouble(3));
            compraDetalle.setPrecio(cursor.getDouble(4));
            compraDetalle.setTara(cursor.getInt(5));
            compraDetalle.setTotal(cursor.getDouble(6));
            compraDetalle.setNumero_arete(cursor.getInt(7));

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(8));
            ganado.setTipo_ganado(cursor.getString(9));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(10));
            raza.setTipo_raza(cursor.getString(11));

            typeAnimal.setText(ganado.getTipo_ganado());
            raceAnimal.setText(raza.getTipo_raza());
            weightAnimal.setText(compraDetalle.getPeso().toString());
            priceAnimal.setText(compraDetalle.getPrecio().toString());
            tareAnimal.setText(compraDetalle.getTara().toString());
            earingNumberAnimal.setText(compraDetalle.getNumero_arete().toString());
            total.setText(compraDetalle.getTotal().toString());

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }

    public void modifieAnimal(View view){
        Intent intent = new Intent(view.getContext(), insert_new_animal.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("action", "modifie");
        bundle.putSerializable("compraDetalle", compraDetalle);
        bundle.putSerializable("ganado", ganado);
        bundle.putSerializable("raza", raza);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
