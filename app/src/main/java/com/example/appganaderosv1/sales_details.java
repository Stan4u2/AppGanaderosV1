package com.example.appganaderosv1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.appganaderosv1.Adapter.Adapter_animals;
import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.entidades.VentaDetalle;
import com.example.appganaderosv1.entidades.Ventas;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class sales_details extends AppCompatActivity {

    TextView name_person_sale, cellphone_person_sale, address_person_sale, extra_data_person_sale, date_sale;
    TextView number_animals_sale, amount_to_charge;

    RecyclerView recycler_view_sale;

    public String purchaseDate;

    Persona persona = null;
    Ventas ventas = null;

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

        //RecyclerView
        recycler_view_sale = findViewById(R.id.recycler_view_sale);
        recycler_view_sale.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        listViewAnimalsBought = new ArrayList<>();
        listViewTypeAnimal = new ArrayList<>();
        listViewRaceAnimal = new ArrayList<>();

        Bundle objectSent = getIntent().getExtras();

        if (objectSent != null) {
            persona = (Persona) objectSent.getSerializable("persona");
            ventas = (Ventas) objectSent.getSerializable("ventas");

            name_person_sale.setText(persona.getNombre());
            cellphone_person_sale.setText(persona.getTelefono());
            address_person_sale.setText(persona.getDomicilio());
            extra_data_person_sale.setText(persona.getDatos_extras());

            date_sale.setText(ventas.getFecha());
            number_animals_sale.setText(ventas.getCantidad_animales().toString());
            amount_to_charge.setText(ventas.getCantidad_cobrar().toString());
        }

        fillAnimalList();
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

            purchaseDate = cursor.getString(22);

            listOwners.add(persona);
            listSales.add(ventaDetalle);
            listViewAnimalsBought.add(compraDetalle);
            listViewTypeAnimal.add((ganado));
            listViewRaceAnimal.add(raza);
        }

        db.close();
        cursor.close();

        Adapter_animals adapter_animals = new Adapter_animals(listViewAnimalsBought, listViewTypeAnimal, listViewRaceAnimal);
        /*
        adapter_animals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Persona persona = listOwners.get(recycler_view.getChildAdapterPosition(view));
                VentaDetalle ventaDetalle = listSales.get(recycler_view.getChildAdapterPosition(view));
                CompraDetalle compraDetalle = listViewAnimalsBought.get(recycler_view.getChildAdapterPosition(view));
                Ganado ganado = listViewTypeAnimal.get(recycler_view.getChildAdapterPosition(view));
                Raza raza = listViewRaceAnimal.get(recycler_view.getChildAdapterPosition(view));

                Intent intent = new Intent(getApplicationContext(), animal_details_sale.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("persona", persona);
                bundle.putSerializable("fechaCompra", purchaseDate);
                bundle.putSerializable("ventaDetalle", ventaDetalle);
                bundle.putSerializable("compraDetalle", compraDetalle);
                bundle.putSerializable("ganado", ganado);
                bundle.putSerializable("raza", raza);
                bundle.putSerializable("owner", "no");

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        */
        recycler_view_sale.setAdapter(adapter_animals);
    }
}
