package com.example.appganaderosv1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.entidades.VentaDetalle;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class select_animal extends AppCompatActivity {

    EditText precio_venta, tare_sale;
    TextView name_owner, cellphone_owner, address_owner, extra_data_owner;
    TextView typeAnimal, raceAnimal, weightAnimal, priceAnimal, tareAnimal, earingNumberAnimal, total_pagado, total_cobrar_CT;
    Spinner spinner_owner, spinner_purchase_date, spinner_animal;
    ImageButton saveSale;

    public static String action = "";
    public static String owner;

    public static boolean loadingData;
    public static boolean selected = false;

    static int id_sale_modifie;

    int idOwner;
    String purchaseDate;
    int idAnimal;

    //ArrayLists Spinner Person
    ArrayList<String> peopleList;
    ArrayList<Persona> peopleData;

    //ArrayList Spinner Purchase Date
    ArrayList<String> purchaseList;
    ArrayList<Compras> purchaseData;

    //ArrayLists Spinner Animals
    ArrayList<String> animalList;
    ArrayList<CompraDetalle> animalData;
    ArrayList<Ganado> animalType;
    ArrayList<Raza> animalRace;

    ConexionSQLiteHelper conn;

    CompraDetalle compraDetalle = null;
    Persona persona = null;
    VentaDetalle ventaDetalle = null;
    Ganado ganado = null;
    Raza raza = null;

    int posA , posB, posC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_animal);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //EditText
        precio_venta = findViewById(R.id.precio_venta);
        tare_sale = findViewById(R.id.tare_sale);

        //TextView
        name_owner = findViewById(R.id.name_owner);
        cellphone_owner = findViewById(R.id.cellphone_owner);
        address_owner = findViewById(R.id.address_owner);
        extra_data_owner = findViewById(R.id.extra_data_owner);

        typeAnimal = findViewById(R.id.typeAnimal);
        raceAnimal = findViewById(R.id.raceAnimal);
        weightAnimal = findViewById(R.id.weightAnimal);
        priceAnimal = findViewById(R.id.priceAnimal);
        tareAnimal = findViewById(R.id.tareAnimal);
        earingNumberAnimal = findViewById(R.id.earingNumberAnimal);
        total_pagado = findViewById(R.id.total_pagado);
        total_cobrar_CT = findViewById(R.id.total_cobrar_CT);

        //Spinner
        spinner_owner = findViewById(R.id.spinner_owner);
        spinner_purchase_date = findViewById(R.id.spinner_purchase_date);
        spinner_animal = findViewById(R.id.spinner_animal);

        //ImageButton
        saveSale = findViewById(R.id.saveSale);

        Bundle actionToDo = getIntent().getExtras();

        consultListOwners();
        consultListAnimals();
        consultListDates();

        precio_venta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tare_sale.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (actionToDo != null) {

            action = actionToDo.getSerializable("action").toString();
            owner = actionToDo.getSerializable("owner").toString();

            switch (action) {
                case "insert":
                    System.out.println("insertar");
                    break;

                case "modifie":
                    loadingData = false;

                    if (actionToDo != null) {
                        persona = (Persona) actionToDo.getSerializable("persona");
                        ventaDetalle = (VentaDetalle) actionToDo.getSerializable("ventaDetalle");
                        compraDetalle = (CompraDetalle) actionToDo.getSerializable("compraDetalle");
                        ganado = (Ganado) actionToDo.getSerializable("ganado");
                        raza = (Raza) actionToDo.getSerializable("raza");
                        purchaseDate = actionToDo.getSerializable("fechaCompra").toString();

                        loadingData = true;
                    }

                    loadData();
                    break;
            }

        }

    }

    public void loadData() {

        if (loadingData) {

            if (owner.equals("no")) {

                id_sale_modifie = ventaDetalle.getId_venta_detalle();

                for (int i = 0; i < peopleData.size(); i++) {
                    if (peopleData.get(i).getId_persona().equals(persona.getId_persona())) {
                        spinner_owner.setSelection(i + 1);
                        posA = i+1;
                        consultListDates();
                    }
                }

                for (int i = 0; i < purchaseData.size(); i++) {
                    if (purchaseData.get(i).getFecha_compra().equals(purchaseDate)) {
                        spinner_purchase_date.setSelection(i + 1);
                        posB = i+1;
                        consultListAnimals();
                    }
                }

                for (int i = 0; i < animalData.size(); i++) {
                    if (animalData.get(i).getId_compra_detalle().equals(compraDetalle.getId_compra_detalle())) {
                        spinner_animal.setSelection(i + 1);
                        posC = i+1;
                    }
                }
            }


            precio_venta.setText(ventaDetalle.getPrecio_venta().toString());
            tare_sale.setText(ventaDetalle.getTara_venta().toString());
        }

        if(posA != 0 && posB != 0 && posC != 0){
            loadingData = false;
            selected = true;
            System.out.println(posA + " " + posB + " " + posC);
            spinner_owner.setSelection(posA);
            spinner_purchase_date.setSelection(posB);
            spinner_animal.setSelection(posC);
        }
    }

    private void consultListOwners() {

        SQLiteDatabase db = conn.getWritableDatabase();
        Persona persona = null;
        peopleData = new ArrayList<Persona>();


        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " +
                        "p.*" +
                        " FROM " +
                        Utilidades.TABLA_PERSONA + " p, " +
                        Utilidades.TABLA_COMPRAS + " c " +
                        " WHERE " +
                        Utilidades.CAMPO_PERSONA_COMPRO + " = " + Utilidades.CAMPO_ID_PERSONA +
                        " ORDER BY " +
                        Utilidades.CAMPO_NOMBRE, null
        );
        while (cursor.moveToNext()) {
            persona = new Persona();
            persona.setId_persona(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setTelefono(cursor.getString(2));
            persona.setDomicilio(cursor.getString(3));
            persona.setDatos_extras(cursor.getString(4));

            peopleData.add(persona);
        }
        cursor.close();
        db.close();

        obtainList_owner();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, peopleList);

        spinner_owner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    idOwner = peopleData.get(position - 1).getId_persona();
                    name_owner.setText(peopleData.get(position - 1).getNombre());
                    cellphone_owner.setText(peopleData.get(position - 1).getTelefono());
                    address_owner.setText(peopleData.get(position - 1).getDomicilio());
                    extra_data_owner.setText(peopleData.get(position - 1).getDatos_extras());

                    consultListDates();
                    consultListAnimals();

                    //In this part I select the date when I modifie it, it's the only way
                    if (loadingData == true && purchaseData.size() > 0) {
                        loadData();
                    }


                } else {
                    idOwner = 0;
                    name_owner.setText("");
                    cellphone_owner.setText("");
                    address_owner.setText("");
                    extra_data_owner.setText("");
                    consultListDates();
                    consultListAnimals();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_owner.setAdapter(adapter);
    }

    private void obtainList_owner() {
        peopleList = new ArrayList<String>();
        peopleList.add("Seleccione:");

        for (int i = 0; i < peopleData.size(); i++) {
            peopleList.add(peopleData.get(i).getNombre());
        }
    }

    private void consultListDates() {
        SQLiteDatabase db = conn.getWritableDatabase();

        Compras compras = null;

        String[] parameters = {String.valueOf(idOwner)};

        purchaseData = new ArrayList<Compras>();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT " +
                        Utilidades.CAMPO_FECHA_COMPRAS +
                        " FROM " +
                        Utilidades.TABLA_COMPRAS +
                        " WHERE " +
                        Utilidades.CAMPO_PERSONA_COMPRO + " = ?", parameters
        );

        while (cursor.moveToNext()) {
            compras = new Compras();
            compras.setFecha_compra(cursor.getString(0));

            purchaseData.add(compras);
        }

        cursor.close();
        db.close();

        obtainListDates();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, purchaseList);

        spinner_purchase_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    purchaseDate = purchaseData.get(position - 1).getFecha_compra();


                    consultListAnimals();

                    if (loadingData == true && animalData.size() > 0) {
                        loadData();
                    }

                } else {

                    purchaseDate = "";

                    consultListAnimals();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_purchase_date.setAdapter(adapter);

    }

    private void obtainListDates() {
        purchaseList = new ArrayList<String>();
        purchaseList.add("Seleccione:");

        for (int i = 0; i < purchaseData.size(); i++) {
            purchaseList.add(purchaseData.get(i).getFecha_compra());
        }
    }

    private void consultListAnimals() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String[] parameters = {String.valueOf(idOwner)};

        CompraDetalle compraDetalle = null;
        Ganado ganado = null;
        Raza raza = null;

        animalData = new ArrayList<CompraDetalle>();
        animalType = new ArrayList<Ganado>();
        animalRace = new ArrayList<Raza>();

        Cursor cursor = null;

        //In this part of code I compare if im goingto to modifie it, if thats the case the same animal must appear.
        System.out.println(loadingData);
        if (action.equals("modifie")) {
            cursor = db.rawQuery(
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
                            Utilidades.TABLA_COMPRAS + ", " +
                            Utilidades.TABLA_COMPRA_DETALLE + ", " +
                            Utilidades.TABLA_GANADO + ", " +
                            Utilidades.TABLA_RAZA +
                            " WHERE " +
                            Utilidades.CAMPO_GANADO + " = " + Utilidades.CAMPO_ID_GANADO +
                            " AND " +
                            Utilidades.CAMPO_RAZA + " = " + Utilidades.CAMPO_ID_RAZA +
                            " AND " +
                            Utilidades.CAMPO_COMPRA + " = " + Utilidades.CAMPO_ID_COMPRA +
                            " AND " +
                            Utilidades.CAMPO_FECHA_COMPRAS + " = '" + purchaseDate + "'" +
                            " AND " +
                            "NOT EXISTS (" +
                                "SELECT " +
                                    "* " +
                                " FROM "
                                    + Utilidades.TABLA_VENTA_DETALLE +
                                " WHERE "
                                    + Utilidades.CAMPO_ID_COMPRA_DETALLE + " = " + Utilidades.CAMPO_COMPRA_GANADO +
                                " AND "
                                    + Utilidades.CAMPO_ID_VENTA_DETALLE + " != " + id_sale_modifie +
                            ")" +
                            " AND " +
                            Utilidades.CAMPO_PERSONA_COMPRO + " = ?", parameters
            );
        } else {
            cursor = db.rawQuery(
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
                            Utilidades.TABLA_COMPRAS + ", " +
                            Utilidades.TABLA_COMPRA_DETALLE + ", " +
                            Utilidades.TABLA_GANADO + ", " +
                            Utilidades.TABLA_RAZA +
                            " WHERE " +
                            Utilidades.CAMPO_GANADO + " = " + Utilidades.CAMPO_ID_GANADO +
                            " AND " +
                            Utilidades.CAMPO_RAZA + " = " + Utilidades.CAMPO_ID_RAZA +
                            " AND " +
                            Utilidades.CAMPO_COMPRA + " = " + Utilidades.CAMPO_ID_COMPRA +
                            " AND " +
                            Utilidades.CAMPO_FECHA_COMPRAS + " = '" + purchaseDate + "'" +
                            " AND " +
                            "NOT EXISTS (" +
                            "SELECT * FROM " + Utilidades.TABLA_VENTA_DETALLE + " WHERE " + Utilidades.CAMPO_ID_COMPRA_DETALLE + " = " + Utilidades.CAMPO_COMPRA_GANADO +
                            ")" +
                            " AND " +
                            Utilidades.CAMPO_PERSONA_COMPRO + " = ?", parameters
            );
        }

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

            animalData.add(compraDetalle);
            animalType.add(ganado);
            animalRace.add(raza);
        }

        cursor.close();
        db.close();

        obtainListAnimals();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, animalList);

        spinner_animal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {

                    idAnimal = animalData.get(position - 1).getId_compra_detalle();
                    typeAnimal.setText(animalType.get(position - 1).getTipo_ganado());
                    raceAnimal.setText(animalRace.get(position - 1).getTipo_raza());
                    weightAnimal.setText(animalData.get(position - 1).getPeso().toString());
                    priceAnimal.setText(animalData.get(position - 1).getPrecio().toString());
                    tareAnimal.setText(animalData.get(position - 1).getTara().toString());
                    earingNumberAnimal.setText(animalData.get(position - 1).getNumero_arete().toString());
                    total_pagado.setText(animalData.get(position - 1).getTotal().toString());

                } else {

                    idAnimal = 0;
                    typeAnimal.setText("");
                    raceAnimal.setText("");
                    weightAnimal.setText("");
                    priceAnimal.setText("");
                    tareAnimal.setText("");
                    earingNumberAnimal.setText("");
                    total_pagado.setText("");

                }

                calculateTotal();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_animal.setAdapter(adapter);

        if(posA != 0 && posB != 0 && posC != 0 && spinner_owner.getSelectedItemId() == posA && spinner_purchase_date.getSelectedItemId() == posB && selected){
            System.out.println("hola");
            System.out.println(posA + " " + posB + " " + posC);
            spinner_owner.setSelection(posA);
            spinner_purchase_date.setSelection(posB);
            spinner_animal.setSelection(posC);
        }

    }

    private void obtainListAnimals() {

        animalList = new ArrayList<String>();
        animalList.add("Seleccione:");

        for (int i = 0; i < animalData.size(); i++) {
            animalList.add(animalType.get(i).getTipo_ganado() + " " + animalRace.get(i).getTipo_raza() + " " + animalData.get(i).getPeso() + "kg");
        }

    }

    public void calculateTotal() {
        int tare = 0;
        double weight = 0, price_sale = 0, total = 0, sum;
        if (!precio_venta.getText().toString().isEmpty()) {
            price_sale = Integer.valueOf(precio_venta.getText().toString());
        }
        if (!tare_sale.getText().toString().isEmpty()) {
            tare = Integer.valueOf(tare_sale.getText().toString());
        }
        if (!weightAnimal.getText().toString().isEmpty()) {
            weight = Double.parseDouble(weightAnimal.getText().toString());
        }

        total = (price_sale * weight);

        sum = (total - ((total * tare) / 100));

        total_cobrar_CT.setText(String.valueOf(sum));
    }

    public void saveSale(View view) {
        boolean complete = false;

        boolean noBlankSpaces = true;
        if (spinner_owner.getSelectedItemId() == 0 && spinner_animal.getSelectedItemId() == 0 && spinner_purchase_date.getSelectedItemId() == 0 && precio_venta.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "¡¡Campos Vacios!!", Toast.LENGTH_LONG).show();
            noBlankSpaces = false;
        } else {
            if (spinner_owner.getSelectedItemId() == 0) {
                Toast.makeText(getApplicationContext(), "¡¡Selecione El Dueño!!", Toast.LENGTH_LONG).show();
                noBlankSpaces = false;
            }
            if (spinner_animal.getSelectedItemId() == 0) {
                Toast.makeText(getApplicationContext(), "¡¡Selecione El Ganado!!", Toast.LENGTH_LONG).show();
                noBlankSpaces = false;
            }
            if (spinner_purchase_date.getSelectedItemId() == 0) {
                Toast.makeText(getApplicationContext(), "¡¡Selecione La Fecha de Compra!!", Toast.LENGTH_LONG).show();
                noBlankSpaces = false;
            }
            if (precio_venta.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "¡¡Ingrese El Precio De Venta!!", Toast.LENGTH_LONG).show();
                noBlankSpaces = false;
            }
        }

        if (noBlankSpaces) {
            int tare = 0;
            if (!tare_sale.getText().toString().isEmpty()) {
                tare = Integer.valueOf(tare_sale.getText().toString());
            }
            if (action.equals("insert")) {
                boolean inserted = false;

                if (owner.equals("no")) {
                    inserted = insertNewSale(
                            idAnimal,
                            Double.parseDouble(precio_venta.getText().toString()),
                            tare,
                            Double.parseDouble(total_cobrar_CT.getText().toString())
                    );
                } else if (owner.equals("yes")) {

                }


                if (inserted) {
                    Toast.makeText(getApplicationContext(), "Datos Insertados", Toast.LENGTH_LONG).show();
                    complete = true;
                } else {
                    Toast.makeText(getApplicationContext(), "¡¡Datos No Insertados!!", Toast.LENGTH_LONG).show();
                }
            } else if (action.equals("modifie")) {
                int modified = 0;
                if (owner.equals("no")) {
                    modified = modifieSaleNoOwner(id_sale_modifie, idAnimal,
                            Double.parseDouble(precio_venta.getText().toString()),
                            tare,
                            Double.parseDouble(total_cobrar_CT.getText().toString()));
                } else if (owner.equals("yes")) {

                }

                if (modified == 1) {
                    Toast.makeText(getApplicationContext(), "Se han actualizado los datos", Toast.LENGTH_LONG).show();
                    complete = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Datos no actualizados", Toast.LENGTH_LONG).show();
                }
            }

            if (complete) {
                insert_new_sales.newSale = true;
                finish();
            }
        }
    }

    private int modifieSaleNoOwner(int id_sale, int idAnimal, double price, int tare, double total) {
        SQLiteDatabase db = conn.getWritableDatabase();

        String[] id_sale_mod = {String.valueOf(id_sale)};

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_COMPRA_GANADO, idAnimal);
        values.put(Utilidades.CAMPO_PRECIO_VENTA, price);
        values.put(Utilidades.CAMPO_TARA_VENTA, tare);
        values.put(Utilidades.CAMPO_TOTAL_VENTA, total);

        int updated = db.update(Utilidades.TABLA_VENTA_DETALLE, values, Utilidades.CAMPO_ID_VENTA_DETALLE + " = ?", id_sale_mod);

        db.close();

        return updated;
    }

    private boolean insertNewSale(int idAnimal, double price, int tare, double total) {

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_COMPRA_GANADO, idAnimal);
        values.put(Utilidades.CAMPO_PRECIO_VENTA, price);
        values.put(Utilidades.CAMPO_TARA_VENTA, tare);
        values.put(Utilidades.CAMPO_TOTAL_VENTA, total);

        long idResult = db.insert(Utilidades.TABLA_VENTA_DETALLE, Utilidades.CAMPO_ID_VENTA_DETALLE, values);

        db.close();

        if (idResult == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void cancel(View view) {
        finish();
    }
}
