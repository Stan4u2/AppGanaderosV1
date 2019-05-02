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
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.entidades.VentaDetalle;
import com.example.appganaderosv1.utilidades.Utilidades;

public class animal_details_sale extends AppCompatActivity {

    TextView name_owner, cellphone_owner, address_owner, extra_data_owner;
    TextView purchaseDate, typeAnimal, raceAnimal, weightAnimal, priceAnimal, tareAnimal, earingNumberAnimal, total_pagado;
    TextView sale_price, tare_sale, total_cobrar_CT;

    Persona persona = null;
    VentaDetalle ventaDetalle = null;
    CompraDetalle compraDetalle = null;
    Ganado ganado = null;
    Raza raza = null;

    static int id_sale_modifie;

    static int id_sales;

    int id_sale;

    public static String owner;

    public String purchase_date;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_details_sale);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //TextView
        name_owner = findViewById(R.id.name_owner);
        cellphone_owner = findViewById(R.id.cellphone_owner);
        address_owner = findViewById(R.id.address_owner);
        extra_data_owner = findViewById(R.id.extra_data_owner);

        purchaseDate = findViewById(R.id.purchaseDate);
        typeAnimal = findViewById(R.id.typeAnimal);
        raceAnimal = findViewById(R.id.raceAnimal);
        weightAnimal = findViewById(R.id.weightAnimal);
        priceAnimal = findViewById(R.id.priceAnimal);
        tareAnimal = findViewById(R.id.tareAnimal);
        earingNumberAnimal = findViewById(R.id.earingNumberAnimal);
        total_pagado = findViewById(R.id.total_pagado);

        sale_price = findViewById(R.id.sale_price);
        tare_sale = findViewById(R.id.tare_sale);
        total_cobrar_CT = findViewById(R.id.total_cobrar_CT);

        Bundle objectSent = getIntent().getExtras();

        if(objectSent != null) {
            owner = objectSent.getSerializable("owner").toString();
            persona = (Persona) objectSent.getSerializable("persona");
            ventaDetalle = (VentaDetalle) objectSent.getSerializable("ventaDetalle");
            compraDetalle = (CompraDetalle) objectSent.getSerializable("compraDetalle");
            ganado = (Ganado) objectSent.getSerializable("ganado");
            raza = (Raza) objectSent.getSerializable("raza");
            purchase_date = objectSent.getSerializable("fechaCompra").toString();

            if(owner.equals("yes")){
                System.out.println("si entro");
                id_sale = ventaDetalle.getId_venta();
            }

            id_sale_modifie = ventaDetalle.getId_venta_detalle();

            name_owner.setText(persona.getNombre());
            cellphone_owner.setText(persona.getTelefono());
            address_owner.setText(persona.getDomicilio());
            extra_data_owner.setText(persona.getDatos_extras());

            purchaseDate.setText(purchase_date);
            typeAnimal.setText(ganado.getTipo_ganado());
            raceAnimal.setText(raza.getTipo_raza());
            weightAnimal.setText(compraDetalle.getPeso().toString());
            priceAnimal.setText(compraDetalle.getPrecio().toString());
            tareAnimal.setText(compraDetalle.getTara().toString());
            earingNumberAnimal.setText(compraDetalle.getNumero_arete().toString());
            total_pagado.setText(compraDetalle.getTotal().toString());

            sale_price.setText(ventaDetalle.getPrecio_venta().toString());
            tare_sale.setText(ventaDetalle.getTara_venta().toString());
            total_cobrar_CT.setText(ventaDetalle.getTotal_venta().toString());
        }
    }

    public void onRestart() {
        super.onRestart();

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] id_sale = {String.valueOf(id_sale_modifie)};

        try{
            Cursor cursor = db.rawQuery(
                    "SELECT DISTINCT " +
                            Utilidades.CAMPO_ID_PERSONA + ", " +
                            Utilidades.CAMPO_NOMBRE + ", " +
                            Utilidades.CAMPO_TELEFONO + ", " +
                            Utilidades.CAMPO_DOMICILIO + ", " +
                            Utilidades.CAMPO_DATOS_EXTRAS + ", " +

                            Utilidades.CAMPO_ID_VENTA_DETALLE + ", " +
                            Utilidades.CAMPO_COMPRA_GANADO + ", " +
                            Utilidades.CAMPO_PRECIO_VENTA  + ", " +
                            Utilidades.CAMPO_TARA_VENTA + ", " +
                            Utilidades.CAMPO_TOTAL_VENTA + ", " +

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
                            Utilidades.CAMPO_ID_VENTA_DETALLE + " = ?" , id_sale);

            cursor.moveToFirst();

            //Here I set the values to the objects if the user wants to modifie it again
            persona = new Persona();
            persona.setId_persona(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));
            persona.setDomicilio(cursor.getString(3));
            persona.setDatos_extras(cursor.getString(4));

            ventaDetalle = new VentaDetalle();
            ventaDetalle.setId_venta_detalle(cursor.getInt(5));
            ventaDetalle.setId_ganado(cursor.getInt(6));
            ventaDetalle.setPrecio_venta(cursor.getInt(7));
            ventaDetalle.setTara_venta(cursor.getInt(8));
            ventaDetalle.setTotal_venta(cursor.getInt(9));

            compraDetalle = new CompraDetalle();
            compraDetalle.setId_compra_detalle(cursor.getInt(10));
            compraDetalle.setGanado(cursor.getInt(11));
            compraDetalle.setRaza(cursor.getInt(12));
            compraDetalle.setPeso(cursor.getDouble(13));
            compraDetalle.setPrecio(cursor.getDouble(14));
            compraDetalle.setTara(cursor.getInt(15));
            compraDetalle.setTotal(cursor.getDouble(16));
            compraDetalle.setNumero_arete(cursor.getInt(17));

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(18));
            ganado.setTipo_ganado(cursor.getString(19));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(20));
            raza.setTipo_raza(cursor.getString(21));

            purchase_date = cursor.getString(22);

            cursor.close();
            db.close();

            name_owner.setText(persona.getNombre());
            cellphone_owner.setText(persona.getTelefono());
            address_owner.setText(persona.getDomicilio());
            extra_data_owner.setText(persona.getDatos_extras());

            purchaseDate.setText(purchase_date);
            typeAnimal.setText(ganado.getTipo_ganado());
            raceAnimal.setText(raza.getTipo_raza());
            weightAnimal.setText(compraDetalle.getPeso().toString());
            priceAnimal.setText(compraDetalle.getPrecio().toString());
            tareAnimal.setText(compraDetalle.getTara().toString());
            earingNumberAnimal.setText(compraDetalle.getNumero_arete().toString());
            total_pagado.setText(compraDetalle.getTotal().toString());

            sale_price.setText(ventaDetalle.getPrecio_venta().toString());
            tare_sale.setText(ventaDetalle.getTara_venta().toString());
            total_cobrar_CT.setText(ventaDetalle.getTotal_venta().toString());

            insert_new_sales.owner = owner;

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void modifieSale(View view){
        Intent intent = new Intent(view.getContext(), select_animal.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("action", "modifie");
        bundle.putSerializable("owner", owner);
        bundle.putSerializable("persona", persona);
        bundle.putSerializable("fechaCompra", purchase_date);
        bundle.putSerializable("ventaDetalle", ventaDetalle);
        bundle.putSerializable("compraDetalle", compraDetalle);
        bundle.putSerializable("ganado", ganado);
        bundle.putSerializable("raza", raza);
        if(owner.equals("yes")){
            bundle.putSerializable("idSale", id_sale);
        }


        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void deleteSale(View view){
        SQLiteDatabase db = conn.getReadableDatabase();
        boolean continueProcess = false;
        String[] id_sale = {String.valueOf(id_sale_modifie)};

        //In this part I delete the animal but I still have to change the number of animals
        int deleted = db.delete(Utilidades.TABLA_VENTA_DETALLE, Utilidades.CAMPO_ID_VENTA_DETALLE + " = ?", id_sale);

        if(deleted == 1){
            insert_new_sales.saleDeleted = true;
            Toast.makeText(getApplicationContext(), "Se ha eliminado el animal", Toast.LENGTH_LONG).show();
            continueProcess = true;
        }else {
            Toast.makeText(getApplicationContext(), "Datos no eliminados", Toast.LENGTH_LONG).show();
        }

        //Now I compare if the user did really delete something, and if he did, then I´ll modify the purchase to substract one animal.
        if(continueProcess == true){
            String[] id_sale_mod = {String.valueOf(id_sales)};

            db.execSQL(
                    "UPDATE " +
                            Utilidades.TABLA_VENTAS +
                            " SET " +
                            Utilidades.CAMPO_CANTIDAD_ANIMALES_VENTAS + " = " + Utilidades.CAMPO_CANTIDAD_ANIMALES_VENTAS + " - 1" +
                            " WHERE " +
                            Utilidades.CAMPO_ID_VENTAS + " = ? ", id_sale_mod);
            finish();
        }

        db.close();
    }
}