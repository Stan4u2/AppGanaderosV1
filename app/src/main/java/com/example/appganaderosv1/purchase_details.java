package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.Adapter.Adapter_animals;
import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.utilidades.Utilidades;

import static com.example.appganaderosv1.MainActivity.administrator;

import java.util.ArrayList;

public class purchase_details extends AppCompatActivity {

    ConexionSQLiteHelper conn;

    Persona person = null;
    Compras compras = null;

    static int idPurchase;

    TextView name_person_purchase, cellphone_person_purchase, address_person_purchase, extra_data_person_purchase;
    TextView date_purchase, amount_animals_purchase, amount_to_pay;
    RecyclerView recycler_view_purchase;
    ImageButton modifie_purchase, delete_purchase, restore_purchase;

    //These ArrayLists are for the Lists Views
    ArrayList<CompraDetalle> listViewAnimalsBought;
    ArrayList<Ganado> listViewTypeAnimal;
    ArrayList<Raza> listViewRaceAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //TextView
        //Person
        name_person_purchase = findViewById(R.id.name_person_purchase);
        cellphone_person_purchase = findViewById(R.id.cellphone_person_purchase);
        address_person_purchase = findViewById(R.id.address_person_purchase);
        extra_data_person_purchase = findViewById(R.id.extra_data_person_purchase);
        //Purchase
        date_purchase = findViewById(R.id.date_purchase);
        amount_animals_purchase = findViewById(R.id.amount_animals_purchase);
        amount_to_pay = findViewById(R.id.amount_to_pay);

        //Recycler View
        recycler_view_purchase = findViewById(R.id.recycler_view_purchase);
        recycler_view_purchase.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        modifie_purchase = findViewById(R.id.modifie_purchase);
        delete_purchase = findViewById(R.id.delete_purchase);
        restore_purchase = findViewById(R.id.restore_purchase);

        listViewAnimalsBought = new ArrayList<>();
        listViewTypeAnimal = new ArrayList<>();
        listViewRaceAnimal = new ArrayList<>();

        Bundle objectSent = getIntent().getExtras();


        if (objectSent != null) {
            person = (Persona) objectSent.getSerializable("persona");
            compras = (Compras) objectSent.getSerializable("compras");

            name_person_purchase.setText(person.getNombre());
            cellphone_person_purchase.setText(person.getTelefono());
            address_person_purchase.setText(person.getDomicilio());
            extra_data_person_purchase.setText(person.getDatos_extras());

            idPurchase = compras.getId_compras();
            date_purchase.setText(compras.getFecha_compra());
            amount_animals_purchase.setText(compras.getCantidad_animales_compra().toString());
            amount_to_pay.setText(compras.getCantidad_pagar().toString());

            if (compras.getRespaldo() == 0) {
                delete_purchase.setVisibility(View.VISIBLE);
                restore_purchase.setVisibility(View.GONE);
            } else if (compras.getRespaldo() == 1) {
                restore_purchase.setVisibility(View.VISIBLE);
                delete_purchase.setVisibility(View.VISIBLE);
            }
        }

        fillAnimalList();

    }

    public void onRestart() {
        super.onRestart();

        calculateQuantityAnimals();
        calculateSumPayAnimals();
        getPurchaseData();
        fillAnimalList();

    }

    private void getPurchaseData() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        Utilidades.CAMPO_ID_COMPRA + ", " +
                        Utilidades.CAMPO_FECHA_COMPRAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_ANIMALES_COMPRAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_PAGAR + ", " +

                        Utilidades.CAMPO_ID_PERSONA + ", " +
                        Utilidades.CAMPO_NOMBRE + ", " +
                        Utilidades.CAMPO_TELEFONO + ", " +
                        Utilidades.CAMPO_DOMICILIO + ", " +
                        Utilidades.CAMPO_DATOS_EXTRAS +
                        " FROM " +
                        Utilidades.TABLA_PERSONA + ", " +
                        Utilidades.TABLA_COMPRAS +
                        " WHERE " +
                        Utilidades.CAMPO_PERSONA_COMPRO + " = " + Utilidades.CAMPO_ID_PERSONA +
                        " AND " +
                        Utilidades.CAMPO_ID_COMPRA + " = " + idPurchase +
                        " AND " +
                        Utilidades.CAMPO_RESPALDO_COMPRAS + " = " + 0, null
        );

        cursor.moveToFirst();

        compras = new Compras();
        compras.setId_compras(cursor.getInt(0));
        compras.setFecha_compra(cursor.getString(1));
        compras.setCantidad_animales_compra(cursor.getInt(2));
        compras.setCantidad_pagar(cursor.getInt(3));

        person = new Persona();
        person.setId_persona(cursor.getInt(4));
        person.setNombre(cursor.getString(5));
        person.setTelefono(cursor.getString(6));
        person.setDomicilio(cursor.getString(7));
        person.setDatos_extras(cursor.getString(8));

        date_purchase.setText(cursor.getString(1));

        name_person_purchase.setText(cursor.getString(5));
        cellphone_person_purchase.setText(cursor.getString(6));
        address_person_purchase.setText(cursor.getString(7));
        extra_data_person_purchase.setText(cursor.getString(8));

        cursor.close();
        db.close();
    }

    private void fillAnimalList() {
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parameters = {String.valueOf(compras.getId_compras())};

        CompraDetalle compraDetalle = null;
        Ganado ganado = null;
        Raza raza = null;

        listViewAnimalsBought = new ArrayList<CompraDetalle>();
        listViewTypeAnimal = new ArrayList<Ganado>();
        listViewRaceAnimal = new ArrayList<Raza>();

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
                        Utilidades.CAMPO_COMPRA + ", " +

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
                        Utilidades.CAMPO_COMPRA + " = ?", parameters
        );

        while (cursor.moveToNext()) {
            compraDetalle = new CompraDetalle();
            compraDetalle.setId_compra_detalle(cursor.getInt(0));
            compraDetalle.setGanado(cursor.getInt(1));
            compraDetalle.setRaza(cursor.getInt(2));
            compraDetalle.setPeso(cursor.getDouble(3));
            compraDetalle.setPrecio(cursor.getDouble(4));
            compraDetalle.setTara(cursor.getInt(5));
            compraDetalle.setTotal(cursor.getDouble(6));
            compraDetalle.setNumero_arete(cursor.getInt(7));
            compraDetalle.setCompra(cursor.getInt(8));

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(9));
            ganado.setTipo_ganado(cursor.getString(10));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(11));
            raza.setTipo_raza(cursor.getString(12));

            listViewAnimalsBought.add(compraDetalle);
            listViewTypeAnimal.add((ganado));
            listViewRaceAnimal.add(raza);
        }


        db.close();
        cursor.close();

        Adapter_animals adapter_animals = new Adapter_animals(listViewAnimalsBought, listViewTypeAnimal, listViewRaceAnimal);

        adapter_animals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompraDetalle compraDetalle = listViewAnimalsBought.get(recycler_view_purchase.getChildAdapterPosition(view));
                Ganado ganado = listViewTypeAnimal.get(recycler_view_purchase.getChildAdapterPosition(view));
                Raza raza = listViewRaceAnimal.get(recycler_view_purchase.getChildAdapterPosition(view));

                Intent intent = new Intent(getApplicationContext(), animal_details_purchase.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("compraDetalle", compraDetalle);
                bundle.putSerializable("ganado", ganado);
                bundle.putSerializable("raza", raza);
                bundle.putSerializable("tipo", "existente");

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recycler_view_purchase.setAdapter(adapter_animals);

    }

    private void calculateQuantityAnimals() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_COMPRA_DETALLE + " WHERE " + Utilidades.CAMPO_COMPRA + " = " + idPurchase, null);

        int count = cursor.getCount();

        amount_animals_purchase.setText(String.valueOf(count));

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_CANTIDAD_ANIMALES_COMPRAS, count);

        db.update(Utilidades.TABLA_COMPRAS, values, Utilidades.CAMPO_ID_COMPRA + " = " + idPurchase, null);

        db.close();
        cursor.close();
    }

    private void calculateSumPayAnimals() {
        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "SUM(" + Utilidades.CAMPO_TOTAL_PAGAR + ") " +
                        " FROM " +
                        Utilidades.TABLA_COMPRA_DETALLE +
                        " WHERE " +
                        Utilidades.CAMPO_COMPRA + " = " + idPurchase
                , null);

        if (cursor.moveToFirst()) {
            double total = cursor.getDouble(0);

            amount_to_pay.setText(String.valueOf(total));

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_CANTIDAD_PAGAR, total);

            db.update(Utilidades.TABLA_COMPRAS, values, Utilidades.CAMPO_ID_COMPRA + " = " + idPurchase, null);

        }

        db.close();
        cursor.close();
    }

    public void modifie_purchase(View view) {
        Intent intent = new Intent(view.getContext(), insert_new_purchases.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("action", "modifie");

        bundle.putSerializable("persona", person);
        bundle.putSerializable("compras", compras);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void delete_purchase(View view) {
        if (administrator) {
            delete();
        } else if (!false) {
            sendGarbage();
        }
    }

    private void sendGarbage() {
        //In this method I don´t really delete the data, i just send it tto the garbage can.
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_purchase = {String.valueOf(idPurchase)};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_RESPALDO_COMPRAS, 1);

        int updated = db.update(Utilidades.TABLA_COMPRAS, values, Utilidades.CAMPO_ID_COMPRA + " = ?", id_purchase);

        if (updated == 1) {
            Toast.makeText(getApplicationContext(), "Se ha mandado al bote de basura.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Ha ocurrdio un error.", Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    private void delete() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] id_purchase = {String.valueOf(compras.getId_compras())};

        int quantityAnimalsSold = 0;

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "COUNT(*)" +
                        " FROM " +
                        "compra_detalle cd, " +
                        "venta_detalle vd" +
                        " WHERE " +
                        "cd.compra = ?" +
                        " AND " +
                        "vd.compra_animal = cd.id_compra_detalle", id_purchase);

        if (cursor.moveToFirst()) {
            quantityAnimalsSold = cursor.getInt(0);
        }

        if (quantityAnimalsSold > 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "¡¡No puede eliminar esta compra!!\nYa realizo la venta de uno de estos animales", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        } else {
            int deleted = db.delete(Utilidades.TABLA_COMPRAS, Utilidades.CAMPO_ID_COMPRA + " = ?", id_purchase);

            if (deleted == 1) {
                Toast.makeText(getApplicationContext(), "Compra Eliminada", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Datos no eliminados", Toast.LENGTH_LONG).show();
            }
        }


        db.close();
        finish();
    }

    public void restorePurchase(View view) {
        //In this method I restore the data.
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_purchase = {String.valueOf(compras.getId_compras())};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_RESPALDO_COMPRAS, 0);

        int updated = db.update(Utilidades.TABLA_COMPRAS, values, Utilidades.CAMPO_ID_COMPRA + " = ?", id_purchase);

        if (updated == 1) {
            Toast.makeText(getApplicationContext(), "Se ha recuperado la compra.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Ha ocurrdio un error.", Toast.LENGTH_LONG).show();
        }

        db.close();
    }
}
