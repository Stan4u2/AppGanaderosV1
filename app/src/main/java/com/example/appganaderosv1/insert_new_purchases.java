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
import com.example.appganaderosv1.entidades.Compras;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Persona;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class insert_new_purchases extends AppCompatActivity {

    Persona person = null;
    Compras compras = null;

    ImageButton cancelButton, add_person_purchase, select_date_purchase, savePurchase;
    TextView action_to_do, name_person_purchase, cellphone_person_purchase, address_person_purchase, extra_data_person_purchase, date_purchase;
    TextView number_animals_purchase, amount_to_pay;
    Button add_animal;
    Spinner spinner_person_purchase;
    RecyclerView recycler_view;

    int idPersonPurchase;
    static int idPurchase;

    public static int id_new_person;
    public static boolean process;
    public static boolean datePurchase;
    public static String DatePurchase;
    public static boolean newAnimalInserted;
    public static boolean animalDeleted;

    public static String action;
    public static String InsertNewAnimal;

    ArrayList<String> peopleList;
    ArrayList<Persona> peopleData;

    ConexionSQLiteHelper conn;

    //These ArrayLists are for the Lists Views
    ArrayList<CompraDetalle> listViewAnimalsBought;
    ArrayList<Ganado> listViewTypeAnimal;
    ArrayList<Raza> listViewRaceAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_purchases);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //Image Button
        add_person_purchase = findViewById(R.id.add_person_purchase);
        select_date_purchase = findViewById(R.id.select_date_purchase);
        savePurchase = findViewById(R.id.savePurchase);
        cancelButton = findViewById(R.id.cancelButton);

        //TextView
        action_to_do = findViewById(R.id.action_to_do);
        name_person_purchase = findViewById(R.id.name_person_purchase);
        cellphone_person_purchase = findViewById(R.id.cellphone_person_purchase);
        address_person_purchase = findViewById(R.id.address_person_purchase);
        extra_data_person_purchase = findViewById(R.id.extra_data_person_purchase);
        date_purchase = findViewById(R.id.date_purchase);
        number_animals_purchase = findViewById(R.id.number_animals_purchase);
        amount_to_pay = findViewById(R.id.amount_to_pay);

        //Button
        add_animal = findViewById(R.id.add_animal);

        //Spinner
        spinner_person_purchase = findViewById(R.id.spinner_person_purchase);

        //RelativeView
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        listViewAnimalsBought = new ArrayList<>();
        listViewTypeAnimal = new ArrayList<>();
        listViewRaceAnimal = new ArrayList<>();

        consultListPeople_purchase();

        Bundle objectSent = getIntent().getExtras();

        if (objectSent != null) {
            action = objectSent.getSerializable("action").toString();

            switch (action) {
                case "insert":
                    action_to_do.setText("Nueva Compra");
                    calculateQuantityAnimalsNotSaved();
                    calculateSumPayAnimalsNotSaved();
                    fillAnimalList();
                    break;
                case "modifie":
                    loadData();
                    fillAnimalListOwner();
                    calculateQuantityAnimals();
                    calculateSumPayAnimals();
                    action_to_do.setText("Modificar Compra");
                    break;

            }
        }

    }

    public void loadData(){
        Persona person = null;
        Compras compras = null;

        Bundle data = getIntent().getExtras();
        if(data != null){
            person = (Persona) data.getSerializable("persona");
            compras = (Compras) data.getSerializable("compras");

            for (int i = 0; i < peopleData.size(); i++) {
                if(peopleData.get(i).getId_persona().equals(person.getId_persona())){
                    spinner_person_purchase.setSelection(i+1);
                }
            }

            idPurchase = compras.getId_compras();
            date_purchase.setText(compras.getFecha_compra());
            number_animals_purchase.setText(compras.getCantidad_animales_compra().toString());
            amount_to_pay.setText(compras.getCantidad_pagar().toString());

        }

    }

    public void onRestart() {
        super.onRestart();
        if (id_new_person != -1 && process) {

            consultListPeople_purchase();

            for (int i = 0; i < peopleData.size(); i++) {
                if (peopleData.get(i).getId_persona() == id_new_person) {
                    spinner_person_purchase.setSelection(i + 1);
                }
            }
            process = false;

        }

        if (datePurchase) {
            datePurchase = false;
            date_purchase.setText(DatePurchase);
        }

        if (newAnimalInserted) {
            newAnimalInserted = false;
            switch (action) {
                case "insert":
                    //In these if´s I compare if the animal inserted has an owner, and depending the case will e the list that will be shown
                    if(InsertNewAnimal.equals("no owner")){
                        fillAnimalList();
                    }else if(InsertNewAnimal.equals("owner")){
                        fillAnimalListOwner();
                    }
                    break;
                case "modifie":
                    fillAnimalListOwner();
                    break;

            }
        }

        if (animalDeleted) {
            animalDeleted = false;
            fillAnimalList();
        }

        if (action.equals("insert")) {
            calculateQuantityAnimalsNotSaved();
            calculateSumPayAnimalsNotSaved();
        }

        if(action.equals("modifie")){
            calculateQuantityAnimals();
            calculateSumPayAnimals();
        }
    }

    private void consultListPeople_purchase() {
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

        obtainList_purchase();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, peopleList);

        spinner_person_purchase.setAdapter(adapter);

        spinner_person_purchase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    idPersonPurchase = peopleData.get(position - 1).getId_persona();
                    name_person_purchase.setText(peopleData.get(position - 1).getNombre());
                    cellphone_person_purchase.setText(peopleData.get(position - 1).getTelefono());
                    address_person_purchase.setText(peopleData.get(position - 1).getDomicilio());
                    extra_data_person_purchase.setText(peopleData.get(position - 1).getDatos_extras());

                } else {

                    idPersonPurchase = 0;
                    name_person_purchase.setText("");
                    cellphone_person_purchase.setText("");
                    address_person_purchase.setText("");
                    extra_data_person_purchase.setText("");

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void obtainList_purchase() {
        peopleList = new ArrayList<String>();
        peopleList.add("Seleccione:");

        for (int i = 0; i < peopleData.size(); i++) {
            peopleList.add(peopleData.get(i).getNombre());
        }
    }

    public void cancel_purchase(View view) {
        deleteProgress();
    }

    public void onBackPressed() {
        deleteProgress();
    }

    public void deleteProgress(){
        SQLiteDatabase db = conn.getReadableDatabase();

        int deleted = db.delete(Utilidades.TABLA_COMPRA_DETALLE, Utilidades.CAMPO_COMPRA + " IS NULL", null);

        if(deleted >= 1){
            Toast.makeText(getApplicationContext(), "Compra Cancelada", Toast.LENGTH_LONG).show();
        }

        finish();
    }

    public void new_person_purchase(View view) {
        Intent intent = null;
        if (view.getId() == R.id.add_person_purchase) {
            intent = new Intent(insert_new_purchases.this, insert_new_person.class);
            intent.putExtra("button", "purchase");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void select_date_purchase(View view) {
        Intent intent = null;
        if (view.getId() == R.id.select_date_purchase) {
            intent = new Intent(insert_new_purchases.this, calendar.class);
            intent.putExtra("button", "purchase");
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void insertNewAnimal(View view) {
        Bundle bundle = new Bundle();
        Intent intent = null;
        if (view.getId() == R.id.add_animal) {
            if(action.equals("insert")) {
                intent = new Intent(insert_new_purchases.this, insert_new_animal.class);
                bundle.putSerializable("action", "insert");
                bundle.putSerializable("WhereCameFrom", "new");
                InsertNewAnimal = "no owner";
            }else if(action.equals("modifie")){
                intent = new Intent(insert_new_purchases.this, insert_new_animal.class);
                bundle.putSerializable("action", "insert");
                bundle.putSerializable("WhereCameFrom", "change");
                bundle.putSerializable("idPurchase", idPurchase);
                InsertNewAnimal = "owner";
            }
        }

        if (intent != null) {
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void fillAnimalListOwner(){
        SQLiteDatabase db = conn.getReadableDatabase();

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
                        Utilidades.CAMPO_COMPRA + " = " + idPurchase, null
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

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(8));
            ganado.setTipo_ganado(cursor.getString(9));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(10));
            raza.setTipo_raza(cursor.getString(11));

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
                CompraDetalle compraDetalle = listViewAnimalsBought.get(recycler_view.getChildAdapterPosition(view));
                Ganado ganado = listViewTypeAnimal.get(recycler_view.getChildAdapterPosition(view));
                Raza raza = listViewRaceAnimal.get(recycler_view.getChildAdapterPosition(view));

                Intent intent = new Intent(getApplicationContext(), animal_details_purchase.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("compraDetalle", compraDetalle);
                bundle.putSerializable("ganado", ganado);
                bundle.putSerializable("raza", raza);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recycler_view.setAdapter(adapter_animals);
    }

    public void fillAnimalList() {
        SQLiteDatabase db = conn.getReadableDatabase();

        CompraDetalle compraDetalle = null;
        Ganado ganado = null;
        Raza raza = null;

        listViewAnimalsBought = new ArrayList<CompraDetalle>();
        listViewTypeAnimal = new ArrayList<Ganado>();
        listViewRaceAnimal = new ArrayList<Raza>();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + Utilidades.VIEW_ANIMAL_NO_OWNER, null
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

            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(8));
            ganado.setTipo_ganado(cursor.getString(9));

            raza = new Raza();
            raza.setId_raza(cursor.getInt(10));
            raza.setTipo_raza(cursor.getString(11));

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
                CompraDetalle compraDetalle = listViewAnimalsBought.get(recycler_view.getChildAdapterPosition(view));
                Ganado ganado = listViewTypeAnimal.get(recycler_view.getChildAdapterPosition(view));
                Raza raza = listViewRaceAnimal.get(recycler_view.getChildAdapterPosition(view));

                Intent intent = new Intent(getApplicationContext(), animal_details_purchase.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("compraDetalle", compraDetalle);
                bundle.putSerializable("ganado", ganado);
                bundle.putSerializable("raza", raza);
                bundle.putSerializable("tipo", "nuevo");

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        recycler_view.setAdapter(adapter_animals);
    }

    public void calculateQuantityAnimalsNotSaved() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_COMPRA_DETALLE + " WHERE " + Utilidades.CAMPO_COMPRA + " IS NULL", null);

        int count = cursor.getCount();

        number_animals_purchase.setText(String.valueOf(count));

        db.close();
        cursor.close();
    }

    public void calculateSumPayAnimalsNotSaved() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "SUM(" + Utilidades.CAMPO_TOTAL_PAGAR + ")" +
                        " FROM " +
                        Utilidades.TABLA_COMPRA_DETALLE +
                        " WHERE " +
                        Utilidades.CAMPO_COMPRA + " IS NULL"
                , null);

        if (cursor.moveToFirst()) {
            double total = cursor.getDouble(0);

            amount_to_pay.setText(String.valueOf(total));
        }

        db.close();
        cursor.close();
    }

    private void calculateQuantityAnimals() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_COMPRA_DETALLE + " WHERE " + Utilidades.CAMPO_COMPRA + " = " + idPurchase, null);

        int count = cursor.getCount();

        number_animals_purchase.setText(String.valueOf(count));

        db.close();
        cursor.close();
    }

    private void calculateSumPayAnimals() {
        SQLiteDatabase db = conn.getReadableDatabase();

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
        }

        db.close();
        cursor.close();
    }

    public void savePurchase(View view) {

            boolean noBlankSpaces = true;
            boolean complete = false;

        if (spinner_person_purchase.getSelectedItemId() == 0) {
            Toast.makeText(getApplicationContext(), "¡¡Selecione El Vendedor!!", Toast.LENGTH_LONG).show();
            noBlankSpaces = false;
        } else if (date_purchase.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "¡¡Selecione La Fecha!!", Toast.LENGTH_LONG).show();
            noBlankSpaces = false;
        } else if (number_animals_purchase.getText().toString().equals("0")) {
            Toast.makeText(getApplicationContext(), "¡¡Registre Almenos 1 Animal!!", Toast.LENGTH_LONG).show();
            noBlankSpaces = false;
        }

        if (noBlankSpaces) {
            if (action.equals("insert")) {
                boolean inserted = insertPurchase(
                        idPersonPurchase,
                        date_purchase.getText().toString(),
                        Integer.valueOf(number_animals_purchase.getText().toString()),
                        Double.valueOf(amount_to_pay.getText().toString())
                );

                if (inserted) {
                    Toast.makeText(getApplicationContext(), "Datos Insertados", Toast.LENGTH_LONG).show();
                    complete = true;
                } else {
                    Toast.makeText(getApplicationContext(), "¡¡Datos No Insertados!!", Toast.LENGTH_LONG).show();
                }
            }else if (action.equals("modifie")){
                int modified = modifiePurchase(
                        idPersonPurchase,
                        date_purchase.getText().toString(),
                        Integer.valueOf(number_animals_purchase.getText().toString()),
                        Double.valueOf(amount_to_pay.getText().toString())
                );

                if(modified == 1){
                    Toast.makeText(getApplicationContext(), "Se han actualizado los datos", Toast.LENGTH_LONG).show();
                    complete = true;
                }else{
                    Toast.makeText(getApplicationContext(), "Datos no actualizados", Toast.LENGTH_LONG).show();
                }
            }

            if(complete) finish();
        }
    }

    private int modifiePurchase(int idOwner, String date, int amountAnimals, double amount_pay) {

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        String[] id_purchase = {String.valueOf(idPurchase)};

        values.put(Utilidades.CAMPO_ID_COMPRA, idPurchase);
        values.put(Utilidades.CAMPO_PERSONA_COMPRO, idOwner);
        values.put(Utilidades.CAMPO_FECHA_COMPRAS, date);
        values.put(Utilidades.CAMPO_CANTIDAD_ANIMALES_COMPRAS, amountAnimals);
        values.put(Utilidades.CAMPO_CANTIDAD_PAGAR, amount_pay);
        values.put(Utilidades.CAMPO_RESPALDO_COMPRAS, 0);

        int updated = db.update(Utilidades.TABLA_COMPRAS, values, Utilidades.CAMPO_ID_COMPRA + " = ?", id_purchase);

        db.close();

        return updated;
    }

    public boolean insertPurchase(int idOwner, String date, int amountAnimals, double amount_pay) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_PERSONA_COMPRO, idOwner);
        values.put(Utilidades.CAMPO_FECHA_COMPRAS, date);
        values.put(Utilidades.CAMPO_CANTIDAD_ANIMALES_COMPRAS, amountAnimals);
        values.put(Utilidades.CAMPO_CANTIDAD_PAGAR, amount_pay);
        values.put(Utilidades.CAMPO_RESPALDO_COMPRAS, 0);

        Long idResult = db.insert(Utilidades.TABLA_COMPRAS, Utilidades.CAMPO_ID_COMPRA, values);

        db.close();

        if (idResult == -1) {
            return false;
        } else {
            if(setIDPurchase(idResult.intValue())){
                return true;
            }else{
                return false;
            }

        }
    }

    public boolean setIDPurchase(int idPurchase) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_COMPRA, idPurchase);

        int updated =db.update(Utilidades.TABLA_COMPRA_DETALLE, values, Utilidades.CAMPO_COMPRA + " IS NULL", null);

        db.close();
        if(updated < 1){
            Toast.makeText(getApplicationContext(), "Animales no insertados", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }
}
