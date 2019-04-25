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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appganaderosv1.Adapter.Adapter_animals;
import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.entidades.VentaDetalle;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class insert_new_sales extends AppCompatActivity {

    ImageButton cancelButton, add_person_sale, select_date_sale, saveSale;
    TextView action_to_do, name_person_sale, cellphone_person_sale, address_person_sale, extra_data_person_sale, date_sale;
    TextView number_animals_sale, amount_to_charge;
    Button add_animal;
    Spinner spinner_person_sale;
    RecyclerView recycler_view;

    int idPersonSale;

    double earnings;

    public static int id_new_person;
    public static boolean process;
    public static boolean dateSale;
    public static String DateSale;
    public static boolean newSale;

    public static boolean saleDeleted;

    public static String action;
    public static String owner;

    ArrayList<String> peopleList;
    ArrayList<Persona> peopleData;

    //These ArrayLists are for the Lists Views
    ArrayList<CompraDetalle> listViewAnimalsBought;
    ArrayList<Ganado> listViewTypeAnimal;
    ArrayList<Raza> listViewRaceAnimal;

    ArrayList<Persona> listOwners;
    ArrayList<VentaDetalle> listSales;

    public String purchaseDate;


    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_sales);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //Image Button
        cancelButton = findViewById(R.id.cancelButton);
        add_person_sale = findViewById(R.id.add_person_sale);
        select_date_sale = findViewById(R.id.select_date_sale);
        saveSale = findViewById(R.id.saveSale);

        //TextView
        action_to_do = findViewById(R.id.action_to_do);
        name_person_sale = findViewById(R.id.name_person_sale);
        cellphone_person_sale = findViewById(R.id.cellphone_person_sale);
        address_person_sale = findViewById(R.id.address_person_sale);
        extra_data_person_sale = findViewById(R.id.extra_data_person_sale);
        date_sale = findViewById(R.id.date_sale);
        number_animals_sale = findViewById(R.id.number_animals_sale);
        amount_to_charge = findViewById(R.id.amount_to_charge);

        //Button
        add_animal = findViewById(R.id.add_animal);

        //Spinner
        spinner_person_sale = findViewById(R.id.spinner_person_sale);

        //RecyclerView
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        listViewAnimalsBought = new ArrayList<>();
        listViewTypeAnimal = new ArrayList<>();
        listViewRaceAnimal = new ArrayList<>();

        consultListPeopleSale();

        Bundle objectSent = getIntent().getExtras();

        if (objectSent != null) {
            action = objectSent.getSerializable("action").toString();

            switch (action) {
                case "insert":
                    action_to_do.setText("Nueva Venta");
                    fillAnimalListNoOwner();
                    calculateQuantityAnimalsNotSaved();
                    calculateSumPayAnimalsNotSaved();
                    break;
                case "modifie":
                    action_to_do.setText("Modificar Venta");
                    break;
            }
        }

    }

    public void onRestart(){
        super.onRestart();

        if (id_new_person != -1 && process) {

            consultListPeopleSale();

            for (int i = 0; i < peopleData.size(); i++) {
                if (peopleData.get(i).getId_persona() == id_new_person) {
                    spinner_person_sale.setSelection(i + 1);
                }
            }
            process = false;

        }

        if (dateSale) {
            dateSale = false;
            date_sale.setText(DateSale);
        }

        if(newSale){
            newSale = false;
            switch (action) {
                case "insert":
                    if(owner.equals("no")) {
                        fillAnimalListNoOwner();
                        calculateQuantityAnimalsNotSaved();
                        calculateSumPayAnimalsNotSaved();
                    }else if(owner.equals("yes")){

                    }
                    break;
                case "modifie":
                    break;
            }

        }

        if(saleDeleted == true){
            if(owner.equals("no")) {
                fillAnimalListNoOwner();
                calculateQuantityAnimalsNotSaved();
                calculateSumPayAnimalsNotSaved();
            }else if(owner.equals("yes")){

            }
        }
    }

    private void consultListPeopleSale() {

        SQLiteDatabase db = conn.getWritableDatabase();
        Persona persona = null;
        peopleData = new ArrayList<Persona>();

        //Select * from personas;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERSONA + " ORDER BY " + Utilidades.CAMPO_NOMBRE, null);
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

        obtainList_sale();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, peopleList);

        spinner_person_sale.setAdapter(adapter);

        spinner_person_sale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    idPersonSale = peopleData.get(position - 1).getId_persona();
                    name_person_sale.setText(peopleData.get(position - 1).getNombre());
                    cellphone_person_sale.setText(peopleData.get(position - 1).getTelefono());
                    address_person_sale.setText(peopleData.get(position - 1).getDomicilio());
                    extra_data_person_sale.setText(peopleData.get(position - 1).getDatos_extras());

                } else {

                    idPersonSale = 0;
                    name_person_sale.setText("");
                    cellphone_person_sale.setText("");
                    address_person_sale.setText("");
                    extra_data_person_sale.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void obtainList_sale() {

        peopleList = new ArrayList<String>();
        peopleList.add("Seleccione:");

        for (int i = 0; i < peopleData.size(); i++) {
            peopleList.add(peopleData.get(i).getNombre());
        }

    }

    public void new_person_sale(View view) {
        Intent intent = null;
        if (view.getId() == R.id.add_person_sale) {
            intent = new Intent(getApplicationContext(), insert_new_person.class);
            intent.putExtra("button", "sale");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void select_date_sale(View view) {
        Intent intent = null;
        if (view.getId() == R.id.select_date_sale) {
            intent = new Intent(getApplicationContext(), calendar.class);
            intent.putExtra("button", "sale");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void select_animal(View view){
        Intent intent = null;
        Bundle bundle = new Bundle();
        switch (action) {
            case "insert":
                if(view.getId() == R.id.add_animal){
                    intent = new Intent(getApplicationContext(),select_animal.class);
                    bundle.putSerializable("action", "insert");
                    bundle.putSerializable("owner", "no");
                    owner = "no";
                }
                break;
            case "modifie":

                break;
        }

        if (intent != null) {
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void fillAnimalListNoOwner(){
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
                "SELECT * FROM " + Utilidades.VIEW_ANIMAL_SALE_NO_OWNER, null
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

        recycler_view.setAdapter(adapter_animals);
    }

    public void calculateQuantityAnimalsNotSaved() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_VENTA_DETALLE + " WHERE " + Utilidades.CAMPO_VENTA + " IS NULL", null);

        int count = cursor.getCount();

        number_animals_sale.setText(String.valueOf(count));

        db.close();
        cursor.close();
    }

    public void calculateSumPayAnimalsNotSaved() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "SUM(" + Utilidades.CAMPO_TOTAL_VENTA + ")" +
                        " FROM " +
                        Utilidades.TABLA_VENTA_DETALLE +
                        " WHERE " +
                        Utilidades.CAMPO_VENTA + " IS NULL"
                , null);

        if (cursor.moveToFirst()) {
            double total = cursor.getDouble(0);

            amount_to_charge.setText(String.valueOf(total));
        }

        db.close();
        cursor.close();
    }

    public void calculateEarningsNoOwner(){
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "SUM(" + Utilidades.CAMPO_TOTAL_PAGAR + ")" +
                     " FROM " +
                            Utilidades.TABLA_VENTA_DETALLE + ", " +
                            Utilidades.TABLA_COMPRA_DETALLE +
                     " WHERE " +
                            Utilidades.CAMPO_COMPRA_GANADO + " = " + Utilidades.CAMPO_ID_COMPRA_DETALLE +
                            " AND " +
                            Utilidades.CAMPO_VENTA + " IS NULL",null
        );

        if (cursor.moveToFirst()) {
            double amountPayed = cursor.getDouble(0);

            earnings = (Double.valueOf(amount_to_charge.getText().toString()) - amountPayed);
        }

        db.close();
        cursor.close();
    }

    public void saveSale(View view) {

        boolean noBlankSpaces = true;
        boolean complete = false;

        if (spinner_person_sale.getSelectedItemId() == 0) {
            Toast.makeText(getApplicationContext(), "¡¡Selecione El Comprador!!", Toast.LENGTH_LONG).show();
            noBlankSpaces = false;
        } else if (date_sale.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "¡¡Selecione La Fecha!!", Toast.LENGTH_LONG).show();
            noBlankSpaces = false;
        } else if (number_animals_sale.getText().toString().equals("0")) {
            Toast.makeText(getApplicationContext(), "¡¡Venda Almenos 1 Animal!!", Toast.LENGTH_LONG).show();
            noBlankSpaces = false;
        }

        if (noBlankSpaces) {
            if (action.equals("insert")) {
                calculateEarningsNoOwner();
                boolean inserted = insertSale(
                        idPersonSale,
                        DateSale,
                        Integer.valueOf(number_animals_sale.getText().toString()),
                        Double.valueOf(amount_to_charge.getText().toString()),
                        earnings
                );

                if (inserted) {
                    Toast.makeText(getApplicationContext(), "Datos Insertados", Toast.LENGTH_LONG).show();
                    complete = true;
                } else {
                    Toast.makeText(getApplicationContext(), "¡¡Datos No Insertados!!", Toast.LENGTH_LONG).show();
                }
            }else if (action.equals("modifie")){

            }

            if(complete) finish();
        }
    }

    private boolean insertSale(int idOwner, String date, int amountAnimals, double amountCharge, double earnings) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_PERSONA_VENTA, idOwner);
        values.put(Utilidades.CAMPO_FECHA_VENTAS, date);
        values.put(Utilidades.CAMPO_CANTIDAD_ANIMALES_VENTAS, amountAnimals);
        values.put(Utilidades.CAMPO_CANTIDAD_COBRAR, amountCharge);
        values.put(Utilidades.CAMPO_GANANCIAS, earnings);
        values.put(Utilidades.CAMPO_RESPALDO_VENTAS, 0);

        Long idResult = db.insert(Utilidades.TABLA_VENTAS, Utilidades.CAMPO_ID_VENTAS, values);

        db.close();

        if (idResult == -1) {
            return false;
        } else {
            if(setIDSale(idResult.intValue())){
                return true;
            }else{
                return false;
            }

        }
    }

    public boolean setIDSale(int idSale) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_VENTA, idSale);

        int updated =db.update(Utilidades.TABLA_VENTA_DETALLE, values, Utilidades.CAMPO_VENTA + " IS NULL", null);

        db.close();
        if(updated < 1){
            Toast.makeText(getApplicationContext(), "Animales no insertados", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}
