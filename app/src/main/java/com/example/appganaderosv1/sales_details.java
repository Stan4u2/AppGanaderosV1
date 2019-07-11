package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.Adapter.Adapter_animals;
import com.example.appganaderosv1.Adapter.Adapter_animals_sale;
import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.entidades.VentaDetalle;
import com.example.appganaderosv1.entidades.Ventas;
import com.example.appganaderosv1.utilidades.Utilidades;

import static com.example.appganaderosv1.MainActivity.administrator;

import java.util.ArrayList;

public class sales_details extends AppCompatActivity {

    TextView name_person_sale, cellphone_person_sale, address_person_sale, extra_data_person_sale, date_sale;
    TextView number_animals_sale, amount_to_charge, earnings, sale_paid;

    RecyclerView recycler_view_sale;

    ImageButton modifie_sale, delete_sale, restore_sale;

    public String purchaseDate;

    Persona persona = null;
    Ventas ventas = null;

    public static int idSale;

    //These ArrayLists are for the Lists Views
    ArrayList<CompraDetalle> listViewAnimalsBought;
    ArrayList<Ganado> listViewTypeAnimal;
    ArrayList<Raza> listViewRaceAnimal;

    ArrayList<Persona> listOwners;
    ArrayList<VentaDetalle> listSales;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_details);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //TextView
        name_person_sale = findViewById(R.id.name_person_sale);
        cellphone_person_sale = findViewById(R.id.cellphone_person_sale);
        address_person_sale = findViewById(R.id.address_person_sale);
        extra_data_person_sale = findViewById(R.id.extra_data_person_sale);
        date_sale = findViewById(R.id.date_sale);
        number_animals_sale = findViewById(R.id.number_animals_sale);
        amount_to_charge = findViewById(R.id.amount_to_charge);
        earnings = findViewById(R.id.earnings);
        sale_paid = findViewById(R.id.sale_paid);

        //RecyclerView
        recycler_view_sale = findViewById(R.id.recycler_view_sale);
        recycler_view_sale.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Image Button
        modifie_sale = findViewById(R.id.modifie_sale);
        delete_sale = findViewById(R.id.delete_sale);
        restore_sale = findViewById(R.id.restore_sale);

        listViewAnimalsBought = new ArrayList<>();
        listViewTypeAnimal = new ArrayList<>();
        listViewRaceAnimal = new ArrayList<>();

        Bundle objectSent = getIntent().getExtras();

        if (objectSent != null) {
            persona = (Persona) objectSent.getSerializable("persona");
            ventas = (Ventas) objectSent.getSerializable("ventas");

            idSale = ventas.getId_ventas();

            name_person_sale.setText(persona.getNombre());
            cellphone_person_sale.setText(persona.getTelefono());
            address_person_sale.setText(persona.getDomicilio());
            extra_data_person_sale.setText(persona.getDatos_extras());

            date_sale.setText(ventas.getFecha());
            number_animals_sale.setText(ventas.getCantidad_animales().toString());
            amount_to_charge.setText(ventas.getCantidad_cobrar().toString());
            earnings.setText(ventas.getGanancias().toString());

            if(ventas.getVenta_pagada()){
                sale_paid.setText("Si");
            }else{
                sale_paid.setText("No");
            }

            if (ventas.getRespaldo() == 0) {
                delete_sale.setVisibility(View.VISIBLE);
                restore_sale.setVisibility(View.GONE);
            } else if (ventas.getRespaldo() == 1) {
                restore_sale.setVisibility(View.VISIBLE);
                delete_sale.setVisibility(View.VISIBLE);
            }
        }

        fillAnimalList();
    }

    public void onRestart() {
        super.onRestart();

        calculateQuantityAnimals();
        calculateSumChargeAnimals();
        calculateSumEarningsAnimals();
        getPurchaseData();
        fillAnimalList();
    }

    private void calculateSumEarningsAnimals() {

        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "(SUM(" + Utilidades.CAMPO_TOTAL_VENTA + ") - SUM(" + Utilidades.CAMPO_TOTAL_PAGAR + "))" +
                        " FROM " +
                        Utilidades.TABLA_VENTA_DETALLE + ", " +
                        Utilidades.TABLA_COMPRA_DETALLE +
                        " WHERE " +
                        Utilidades.CAMPO_VENTA + " = " + idSale +
                        " AND " +
                        Utilidades.CAMPO_ID_COMPRA_DETALLE + " = " + Utilidades.CAMPO_COMPRA_GANADO
                , null);

        if (cursor.moveToFirst()) {
            double total = cursor.getDouble(0);

            earnings.setText(String.valueOf(total));

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_GANANCIAS, total);

            db.update(Utilidades.TABLA_VENTAS, values, Utilidades.CAMPO_ID_VENTAS + " = " + idSale, null);

        }

        db.close();
        cursor.close();

    }

    private void calculateSumChargeAnimals() {

        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "SUM(" + Utilidades.CAMPO_TOTAL_VENTA + ") " +
                        " FROM " +
                        Utilidades.TABLA_VENTA_DETALLE +
                        " WHERE " +
                        Utilidades.CAMPO_VENTA + " = " + idSale
                , null);

        if (cursor.moveToFirst()) {
            double total = cursor.getDouble(0);

            amount_to_charge.setText(String.valueOf(total));

            ContentValues values = new ContentValues();
            values.put(Utilidades.CAMPO_CANTIDAD_COBRAR, total);

            db.update(Utilidades.TABLA_VENTAS, values, Utilidades.CAMPO_ID_VENTAS + " = " + idSale, null);

        }

        db.close();
        cursor.close();

    }

    private void calculateQuantityAnimals() {

        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_VENTA_DETALLE + " WHERE " + Utilidades.CAMPO_VENTA + " = " + idSale, null);

        int count = cursor.getCount();

        number_animals_sale.setText(String.valueOf(count));

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_CANTIDAD_ANIMALES_VENTAS, count);

        db.update(Utilidades.TABLA_VENTAS, values, Utilidades.CAMPO_ID_VENTAS + " = " + idSale, null);

        db.close();
        cursor.close();

    }

    private void getPurchaseData() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        Utilidades.CAMPO_ID_VENTAS + ", " +
                        Utilidades.CAMPO_FECHA_VENTAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_ANIMALES_VENTAS + ", " +
                        Utilidades.CAMPO_CANTIDAD_COBRAR + ", " +
                        Utilidades.CAMPO_GANANCIAS + ", " +
                        Utilidades.CAMPO_VENTA_PAGADA + ", " +

                        Utilidades.CAMPO_ID_PERSONA + ", " +
                        Utilidades.CAMPO_NOMBRE + ", " +
                        Utilidades.CAMPO_TELEFONO + ", " +
                        Utilidades.CAMPO_DOMICILIO + ", " +
                        Utilidades.CAMPO_DATOS_EXTRAS +
                        " FROM " +
                        Utilidades.TABLA_PERSONA + ", " +
                        Utilidades.TABLA_VENTAS +
                        " WHERE " +
                        Utilidades.CAMPO_PERSONA_VENTA + " = " + Utilidades.CAMPO_ID_PERSONA +
                        " AND " +
                        Utilidades.CAMPO_ID_VENTAS + " = " + idSale +
                        " AND " +
                        Utilidades.CAMPO_RESPALDO_VENTAS + " = " + 0, null
        );

        cursor.moveToFirst();

        ventas = new Ventas();
        ventas.setId_ventas(cursor.getInt(0));
        ventas.setFecha(cursor.getString(1));
        ventas.setCantidad_animales(cursor.getInt(2));
        ventas.setCantidad_cobrar(cursor.getInt(3));
        ventas.setGanancias(cursor.getInt(4));

        if(cursor.getInt(5) == 1){
            ventas.setVenta_pagada(true);
        } else if (cursor.getInt(5) == 0){
            ventas.setVenta_pagada(false);
        }

        persona = new Persona();
        persona.setId_persona(cursor.getInt(5));
        persona.setNombre(cursor.getString(6));
        persona.setTelefono(cursor.getString(7));
        persona.setDomicilio(cursor.getString(8));
        persona.setDatos_extras(cursor.getString(9));

        name_person_sale.setText(persona.getNombre());
        cellphone_person_sale.setText(persona.getTelefono());
        address_person_sale.setText(persona.getDomicilio());
        extra_data_person_sale.setText(persona.getDatos_extras());

        date_sale.setText(ventas.getFecha());
        number_animals_sale.setText(ventas.getCantidad_animales().toString());
        amount_to_charge.setText(ventas.getCantidad_cobrar().toString());
        earnings.setText(ventas.getGanancias().toString());

        if(ventas.getVenta_pagada()){
            sale_paid.setText("Si");
        }else{
            sale_paid.setText("No");
        }

        cursor.close();
        db.close();
    }

    private void fillAnimalList() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Persona persona = null;
        VentaDetalle ventaDetalle = null;
        CompraDetalle compraDetalle = null;
        Ganado ganado = null;
        Raza raza = null;

        listOwners = new ArrayList<Persona>();
        listSales = new ArrayList<VentaDetalle>();
        listViewAnimalsBought = new ArrayList<CompraDetalle>();
        listViewTypeAnimal = new ArrayList<Ganado>();
        listViewRaceAnimal = new ArrayList<Raza>();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_ID_PERSONA + ", " +
                        Utilidades.CAMPO_NOMBRE + ", " +
                        Utilidades.CAMPO_TELEFONO + ", " +
                        Utilidades.CAMPO_DOMICILIO + ", " +
                        Utilidades.CAMPO_DATOS_EXTRAS + ", " +

                        Utilidades.CAMPO_ID_VENTA_DETALLE + ", " +
                        Utilidades.CAMPO_COMPRA_GANADO + ", " +
                        Utilidades.CAMPO_PESO_CANAL_VENTA + ", " +
                        Utilidades.CAMPO_PRECIO_VENTA + ", " +
                        Utilidades.CAMPO_TARA_VENTA + ", " +
                        Utilidades.CAMPO_TOTAL_VENTA + ", " +
                        Utilidades.CAMPO_VENTA + ", " +

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
                        Utilidades.CAMPO_TIPO_RAZA + ", " +

                        Utilidades.CAMPO_FECHA_COMPRAS +
                        " FROM " +
                        Utilidades.TABLA_PERSONA + ", " +
                        Utilidades.TABLA_VENTA_DETALLE + ", " +
                        Utilidades.TABLA_COMPRAS + ", " +
                        Utilidades.TABLA_COMPRA_DETALLE + ", " +
                        Utilidades.TABLA_GANADO + ", " +
                        Utilidades.TABLA_RAZA +
                        " WHERE " +
                        Utilidades.CAMPO_PERSONA_COMPRO + " = " + Utilidades.CAMPO_ID_PERSONA +
                        " AND " +
                        Utilidades.CAMPO_COMPRA + " = " + Utilidades.CAMPO_ID_COMPRA +
                        " AND " +
                        Utilidades.CAMPO_COMPRA_GANADO + " = " + Utilidades.CAMPO_ID_COMPRA_DETALLE +
                        " AND " +
                        Utilidades.CAMPO_GANADO + " = " + Utilidades.CAMPO_ID_GANADO +
                        " AND " +
                        Utilidades.CAMPO_RAZA + " = " + Utilidades.CAMPO_ID_RAZA +
                        " AND " +
                        Utilidades.CAMPO_VENTA + " = " + ventas.getId_ventas(), null
        );

        while (cursor.moveToNext()) {
            persona = new Persona();
            persona.setId_persona(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));
            persona.setDomicilio(cursor.getString(3));
            persona.setDatos_extras(cursor.getString(4));

            ventaDetalle = new VentaDetalle();
            ventaDetalle.setId_venta_detalle(cursor.getInt(5));
            ventaDetalle.setId_ganado(cursor.getInt(6));
            ventaDetalle.setPeso_canal_venta(cursor.getDouble(7));
            ventaDetalle.setPrecio_venta(cursor.getDouble(8));
            ventaDetalle.setTara_venta(cursor.getInt(9));
            ventaDetalle.setTotal_venta(cursor.getDouble(10));
            ventaDetalle.setId_venta(cursor.getInt(11));

            compraDetalle = new CompraDetalle();
            compraDetalle.setId_compra_detalle(cursor.getInt(12));
            compraDetalle.setGanado(cursor.getInt(13));
            compraDetalle.setRaza(cursor.getInt(14));
            compraDetalle.setPeso_pie_compra(cursor.getDouble(15));
            compraDetalle.setPeso_canal_compra(cursor.getDouble(16));
            compraDetalle.setPrecio(cursor.getDouble(17));
            compraDetalle.setTara(cursor.getInt(18));
            compraDetalle.setTotal(cursor.getDouble(19));
            compraDetalle.setNumero_arete(cursor.getString(20));

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(21));
            ganado.setTipo_ganado(cursor.getString(22));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(23));
            raza.setTipo_raza(cursor.getString(24));

            purchaseDate = cursor.getString(25);

            listOwners.add(persona);
            listSales.add(ventaDetalle);
            listViewAnimalsBought.add(compraDetalle);
            listViewTypeAnimal.add((ganado));
            listViewRaceAnimal.add(raza);
        }

        db.close();
        cursor.close();

        Adapter_animals_sale adapter_animals_sale = new Adapter_animals_sale(listSales, listViewAnimalsBought, listViewTypeAnimal, listViewRaceAnimal);

        adapter_animals_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = listOwners.get(recycler_view_sale.getChildAdapterPosition(view));
                VentaDetalle ventaDetalle = listSales.get(recycler_view_sale.getChildAdapterPosition(view));
                CompraDetalle compraDetalle = listViewAnimalsBought.get(recycler_view_sale.getChildAdapterPosition(view));
                Ganado ganado = listViewTypeAnimal.get(recycler_view_sale.getChildAdapterPosition(view));
                Raza raza = listViewRaceAnimal.get(recycler_view_sale.getChildAdapterPosition(view));

                Intent intent = new Intent(getApplicationContext(), animal_details_sale.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("persona", persona);
                bundle.putSerializable("fechaCompra", purchaseDate);
                bundle.putSerializable("ventaDetalle", ventaDetalle);
                bundle.putSerializable("compraDetalle", compraDetalle);
                bundle.putSerializable("ganado", ganado);
                bundle.putSerializable("raza", raza);
                bundle.putSerializable("owner", "yes");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recycler_view_sale.setAdapter(adapter_animals_sale);
    }

    public void modifySale(View view) {
        Intent intent = new Intent(view.getContext(), insert_new_sales.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("action", "modifie");
        bundle.putSerializable("owner", "yes");
        bundle.putSerializable("persona", persona);
        bundle.putSerializable("ventas", ventas);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void delete_sale(View view) {
        if (administrator) {
            delete();
        } else if (!false) {
            sendGarbage();
        }
    }

    private void sendGarbage() {
        //In this method I don´t really delete the data, i just send it tto the garbage can.
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_sale = {String.valueOf(idSale)};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_RESPALDO_VENTAS, 1);

        int updated = db.update(Utilidades.TABLA_VENTAS, values, Utilidades.CAMPO_ID_VENTAS + " = ?", id_sale);

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
        String[] id_sale = {String.valueOf(ventas.getId_ventas())};

        int deleted = db.delete(Utilidades.TABLA_VENTAS, Utilidades.CAMPO_ID_VENTAS + " = ?", id_sale);

        if (deleted == 1) {
            Toast.makeText(getApplicationContext(), "Venta Eliminada", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Datos no eliminados", Toast.LENGTH_LONG).show();
        }

        db.close();
        finish();
    }

    public void restoreSale(View view) {
        //In this method I restore the data.
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_sale = {String.valueOf(ventas.getId_ventas())};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_RESPALDO_VENTAS, 0);

        int updated = db.update(Utilidades.TABLA_VENTAS, values, Utilidades.CAMPO_ID_VENTAS + " = ?", id_sale);

        if (updated == 1) {
            Toast.makeText(getApplicationContext(), "Se ha recuperado la venta.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Ha ocurrdio un error.", Toast.LENGTH_LONG).show();
        }

        db.close();
    }
}
