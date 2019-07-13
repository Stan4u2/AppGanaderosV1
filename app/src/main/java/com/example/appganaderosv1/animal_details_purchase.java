package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.text.DecimalFormat;

public class animal_details_purchase extends AppCompatActivity {

    TextView typeAnimal, raceAnimal, weightAnimal, priceAnimal, tareAnimal, earingNumberAnimal, total, typeWeightAnimal;

    CompraDetalle compraDetalle = null;
    Ganado ganado = null;
    Raza raza = null;

    static int id_animal_modifie;
    static int id_purchase_modifie;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details_purchase);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        typeAnimal = findViewById(R.id.typeAnimal);
        raceAnimal = findViewById(R.id.raceAnimal);
        weightAnimal = findViewById(R.id.weightAnimal);
        priceAnimal = findViewById(R.id.priceAnimal);
        tareAnimal = findViewById(R.id.tareAnimal);
        earingNumberAnimal = findViewById(R.id.earingNumberAnimal);
        total = findViewById(R.id.total);
        typeWeightAnimal = findViewById(R.id.typeWeightAnimal);

        Bundle objectSent = getIntent().getExtras();

        if (objectSent != null) {
            compraDetalle = (CompraDetalle) objectSent.getSerializable("compraDetalle");
            ganado = (Ganado) objectSent.getSerializable("ganado");
            raza = (Raza) objectSent.getSerializable("raza");

            if (String.valueOf(objectSent.getSerializable("tipo")).equals("existente")) {
                id_purchase_modifie = compraDetalle.getCompra();
            }
            id_animal_modifie = compraDetalle.getId_compra_detalle();
            typeAnimal.setText(ganado.getTipo_ganado());
            raceAnimal.setText(raza.getTipo_raza());

            if((compraDetalle.getPeso_pie_compra().toString().equals("0.0"))&&(compraDetalle.getPeso_canal_compra().toString().equals("0.0"))){
                weightAnimal.setText("0.0");
            }else if(!compraDetalle.getPeso_pie_compra().toString().equals("0.0")){
                typeWeightAnimal.setText("Pie ");
                weightAnimal.setText(compraDetalle.getPeso_pie_compra().toString());
            }else if (!compraDetalle.getPeso_canal_compra().toString().equals("0.0")){
                typeWeightAnimal.setText("Canal ");
                weightAnimal.setText(compraDetalle.getPeso_canal_compra().toString());
            }

            priceAnimal.setText(compraDetalle.getPrecio().toString());
            tareAnimal.setText(compraDetalle.getTara().toString());
            earingNumberAnimal.setText(compraDetalle.getNumero_arete());
            total.setText(compraDetalle.getTotal().toString());

        }

    }

    public void onRestart() {
        super.onRestart();

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] id_animal = {String.valueOf(id_animal_modifie)};

        try {
            Cursor cursor = db.rawQuery(
                    "SELECT DISTINCT " +
                            Utilidades.CAMPO_ID_COMPRA_DETALLE + ", " +
                            Utilidades.CAMPO_GANADO + ", " +
                            Utilidades.CAMPO_RAZA + ", " +
                            Utilidades.CAMPO_PESO_PIE_COMPRA + ", " +
                            Utilidades.CAMPO_PESO_CANAL_COMPRA + ", " +
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
                            Utilidades.CAMPO_ID_COMPRA_DETALLE + " = ?", id_animal);

            cursor.moveToFirst();

            //Here I set the values to the objects if the user wants to modifie it again
            compraDetalle = new CompraDetalle();
            compraDetalle.setId_compra_detalle(cursor.getInt(0));
            compraDetalle.setGanado(cursor.getInt(1));
            compraDetalle.setRaza(cursor.getInt(2));
            compraDetalle.setPeso_pie_compra(cursor.getDouble(3));
            compraDetalle.setPeso_canal_compra(cursor.getDouble(4));
            compraDetalle.setPrecio(cursor.getDouble(5));
            compraDetalle.setTara(cursor.getInt(6));
            compraDetalle.setTotal(cursor.getDouble(7));
            compraDetalle.setNumero_arete(cursor.getString(8));

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(9));
            ganado.setTipo_ganado(cursor.getString(10));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(11));
            raza.setTipo_raza(cursor.getString(12));

            typeAnimal.setText(ganado.getTipo_ganado());
            raceAnimal.setText(raza.getTipo_raza());

            if((compraDetalle.getPeso_pie_compra().toString().equals("0.0"))&&(compraDetalle.getPeso_canal_compra().toString().equals("0.0"))){
                weightAnimal.setText("0.0");
            }else if(!compraDetalle.getPeso_pie_compra().toString().equals("0.0")){
                typeWeightAnimal.setText("Pie ");
                weightAnimal.setText(compraDetalle.getPeso_pie_compra().toString());
            }else if (!compraDetalle.getPeso_canal_compra().toString().equals("0.0")){
                typeWeightAnimal.setText("Canal ");
                weightAnimal.setText(compraDetalle.getPeso_canal_compra().toString());
            }

            priceAnimal.setText(compraDetalle.getPrecio().toString());
            tareAnimal.setText(compraDetalle.getTara().toString());
            earingNumberAnimal.setText(compraDetalle.getNumero_arete());
            total.setText(compraDetalle.getTotal().toString());

            cursor.close();
            db.close();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }

    public void modifieAnimal(View view) {
        Intent intent = new Intent(view.getContext(), insert_new_animal.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("action", "modifie");
        bundle.putSerializable("WhereCameFrom", "change");
        bundle.putSerializable("compraDetalle", compraDetalle);
        bundle.putSerializable("ganado", ganado);
        bundle.putSerializable("raza", raza);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void deleteAnimal(View view) {
        SQLiteDatabase db = conn.getReadableDatabase();
        boolean continueProcess = false;
        String[] id_animal = {String.valueOf(id_animal_modifie)};
        //In this part I delete the animal but I still have to change the number of animals

        int exists = 0;

        Cursor cursor = db.rawQuery(
                "SELECT EXISTS (SELECT * FROM venta_detalle where compra_animal = ?);", id_animal);

        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0);
        }

        if (exists == 0) {
            int deleted = db.delete(Utilidades.TABLA_COMPRA_DETALLE, Utilidades.CAMPO_ID_COMPRA_DETALLE + " = ?", id_animal);

            if (deleted == 1) {
                insert_new_purchases.animalDeleted = true;
                Toast.makeText(getApplicationContext(), "Se ha eliminado el animal", Toast.LENGTH_LONG).show();
                continueProcess = true;
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Datos no eliminados", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "¡¡No puede eliminar esta compra!!\nYa realizo la venta de este animal", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }


        db.close();
    }
}
