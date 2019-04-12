package com.example.appganaderosv1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appganaderosv1.entidades.CompraDetalle;
import com.example.appganaderosv1.entidades.Ganado;
import com.example.appganaderosv1.entidades.Raza;
import com.example.appganaderosv1.utilidades.Utilidades;

import java.util.ArrayList;

public class insert_new_animal extends AppCompatActivity {

    ConexionSQLiteHelper conn;

    Spinner spinner_types_animal, spinner_race_animal;
    ImageButton add_type_animal, add_race_animal, cancelAnimal, saveAnimal;
    EditText weight_animal, price_animal, tare_animal, number_earring_animal;

    //Thhese ArrayLists are for the spinners
    ArrayList<CompraDetalle> animalData;
    ArrayList<String> typeAnimalList;
    ArrayList<Ganado> typeAnimal;
    ArrayList<String> raceAnimalList;
    ArrayList<Raza> raceAnimal;

    public static int id_new_race;
    public static boolean process_race;
    int selectedRace;
    public static int id_new_type;
    public static boolean process_type;
    int selectedType;

    static int id_animal_modifie;

    static String action;
    static String WhereCameFrom;
    static int idPurchase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_animal);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 2);

        //Spinner
        spinner_types_animal = findViewById(R.id.spinner_types_animal);
        spinner_race_animal = findViewById(R.id.spinner_race_animal);

        //ImageButton
        add_type_animal = findViewById(R.id.add_type_animal);
        add_race_animal = findViewById(R.id.add_race_animal);
        cancelAnimal = findViewById(R.id.cancelAnimal);
        saveAnimal = findViewById(R.id.saveAnimal);

        //EditText
        weight_animal = findViewById(R.id.weight_animal);
        price_animal = findViewById(R.id.price_animal);
        tare_animal = findViewById(R.id.tare_animal);
        number_earring_animal = findViewById(R.id.number_earring_animal);

        consultListTypesAnimals();
        consultListRaceAnimals();

        Bundle actionToDo = getIntent().getExtras();

        if(actionToDo != null){

            action = actionToDo.getSerializable("action").toString();
            WhereCameFrom = actionToDo.getSerializable("WhereCameFrom").toString();


            switch(action){
                case "insert":
                    if(WhereCameFrom.equals("change")) {
                        idPurchase = Integer.valueOf(actionToDo.getSerializable("idPurchase").toString());
                    }
                    break;

                case "modifie":
                    loadData();
                    break;
            }

        }
    }

    private void loadData() {
        CompraDetalle compraDetalle = null;
        Ganado ganado = null;
        Raza raza = null;

        Bundle objectSent = getIntent().getExtras();

        if(objectSent != null){
            compraDetalle = (CompraDetalle) objectSent.getSerializable("compraDetalle");
            ganado = (Ganado) objectSent.getSerializable("ganado");
            raza = (Raza) objectSent.getSerializable("raza");

            id_animal_modifie = compraDetalle.getId_compra_detalle();

            for (int i = 0; i < typeAnimal.size(); i++) {
                if(typeAnimal.get(i).getId_ganado().equals(ganado.getId_ganado())){
                    spinner_types_animal.setSelection(i+1);
                }
            }

            for (int i = 0; i < raceAnimal.size(); i++) {
                if(raceAnimal.get(i).getId_raza().equals(raza.getId_raza())){
                    spinner_race_animal.setSelection(i+1);
                }
            }

            weight_animal.setText(compraDetalle.getPeso().toString());
            price_animal.setText(compraDetalle.getPrecio().toString());
            tare_animal.setText(compraDetalle.getTara().toString());
            number_earring_animal.setText(compraDetalle.getNumero_arete().toString());

        }
    }

    public void onResume(){
        super.onResume();

        if(process_race){
            consultListRaceAnimals();

            for(int i = 0; i < raceAnimal.size(); i++){
                if(raceAnimal.get(i).getId_raza() == id_new_race){
                    spinner_race_animal.setSelection(i+1);
                }
            }
            process_race = false;
        }

        if(process_type){
            consultListTypesAnimals();

            for(int i = 0; i < typeAnimal.size(); i++){
                if(typeAnimal.get(i).getId_ganado() == id_new_type){
                    spinner_types_animal.setSelection(i+1);
                }
            }
            process_type = false;
        }
    }

    private void consultListRaceAnimals() {
        SQLiteDatabase db = conn.getWritableDatabase();

        Raza raza = null;

        raceAnimal = new ArrayList<Raza>();

        //SELECT * FROM RAZA;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_RAZA + " ORDER BY " + Utilidades.CAMPO_TIPO_RAZA, null);
        while (cursor.moveToNext()){
            raza = new Raza();
            raza.setId_raza(cursor.getInt(0));
            raza.setTipo_raza(cursor.getString(1));

            raceAnimal.add(raza);
        }

        cursor.close();
        db.close();

        obtainList_Race();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, raceAnimalList);

        spinner_race_animal.setAdapter(adapter);

        spinner_race_animal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    selectedRace = raceAnimal.get(position-1).getId_raza();
                }else{
                    selectedRace = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void obtainList_Race() {
        raceAnimalList = new ArrayList<String>();
        raceAnimalList.add("Seleccione");

        for (int i = 0; i < raceAnimal.size(); i++){
            raceAnimalList.add(raceAnimal.get(i).getTipo_raza());
        }
    }

    private void consultListTypesAnimals() {
        SQLiteDatabase db = conn.getWritableDatabase();

        Ganado ganado = null;

        typeAnimal = new ArrayList<Ganado>();

        //SELECT * FROM GANADO;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_GANADO + " ORDER BY " + Utilidades.CAMPO_TIPO_GANADO, null);
        while (cursor.moveToNext()) {
            ganado = new Ganado();
            ganado.setId_ganado(cursor.getInt(0));
            ganado.setTipo_ganado(cursor.getString(1));

            typeAnimal.add(ganado);
        }

        cursor.close();
        db.close();

        obtaninList_Types();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeAnimalList);

        spinner_types_animal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    selectedType = typeAnimal.get(position-1).getId_ganado();
                }else{
                    selectedType = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_types_animal.setAdapter(adapter);
    }

    private void obtaninList_Types() {
        typeAnimalList = new ArrayList<String>();
        typeAnimalList.add("Seleccione");

        for (int i = 0; i < typeAnimal.size(); i++) {
            typeAnimalList.add(typeAnimal.get(i).getTipo_ganado());
        }
    }

    public void insertNewRace(View view){
        Intent intent = null;
        if (view.getId() == R.id.add_race_animal) {
            intent = new Intent(insert_new_animal.this, insert_new_race.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void insertNewType(View view){
        Intent intent = null;
        if (view.getId() == R.id.add_type_animal) {
            intent = new Intent(insert_new_animal.this, insert_new_type.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    public void saveAnimal(View view){
        boolean complete = false;
        if(
            spinner_types_animal.getSelectedItemId() == 0 ||
            spinner_race_animal.getSelectedItemId() == 0 ||
            weight_animal.getText().toString().isEmpty() ||
            price_animal.getText().toString().isEmpty()
        ){
            Toast.makeText(getApplicationContext(), "¡¡Campos Vacios!!", Toast.LENGTH_LONG).show();
        }else{
            int tare = 0, earring = 0;
            if(!tare_animal.getText().toString().isEmpty()){
                tare = Integer.valueOf(tare_animal.getText().toString());
            }
            if(!number_earring_animal.getText().toString().isEmpty()){
                earring = Integer.valueOf(number_earring_animal.getText().toString());
            }
            if(action.equals("insert")){
                boolean inserted = false;
                if(WhereCameFrom.equals("new")) {
                    inserted = insertNewAnimal(
                            selectedType,
                            selectedRace,
                            Double.parseDouble(weight_animal.getText().toString()),
                            Double.parseDouble(price_animal.getText().toString()),
                            tare,
                            earring
                    );
                }else if (WhereCameFrom.equals("change")){
                    System.out.println("A insertar nuevo pero con dueño \nId de la compra " + idPurchase);
                    inserted = insertNewAnimalCurrentPurchase(
                            idPurchase,
                            selectedType,
                            selectedRace,
                            Double.parseDouble(weight_animal.getText().toString()),
                            Double.parseDouble(price_animal.getText().toString()),
                            tare,
                            earring
                    );
                }

                if(inserted){
                    Toast.makeText(getApplicationContext(), "Datos Insertados", Toast.LENGTH_LONG).show();
                    complete = true;
                }else {
                    Toast.makeText(getApplicationContext(), "¡¡Datos No Insertados!!", Toast.LENGTH_LONG).show();
                }
            }else if (action.equals("modifie")){
                int modified = modifieAnimal(
                        selectedType,
                        selectedRace,
                        Double.parseDouble(weight_animal.getText().toString()),
                        Double.parseDouble(price_animal.getText().toString()),
                        tare,
                        earring
                );

                if(modified == 1){
                    Toast.makeText(getApplicationContext(), "Se han actualizado los datos", Toast.LENGTH_LONG).show();
                    complete = true;
                }else{
                    Toast.makeText(getApplicationContext(), "Datos no actualizados", Toast.LENGTH_LONG).show();
                }
            }

            if(complete){
                insert_new_purchases.newAnimalInserted = true;
                finish();
            }
        }
    }

    private boolean insertNewAnimalCurrentPurchase( int idPurchase, int typeAnimal, int raceAnimal, double weightAnimal, double priceAnimal, int tareAnimal, int earringNumber) {
        SQLiteDatabase db = conn.getWritableDatabase();


        double total = (weightAnimal * priceAnimal);

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_COMPRA, idPurchase);
        values.put(Utilidades.CAMPO_GANADO, typeAnimal);
        values.put(Utilidades.CAMPO_RAZA, raceAnimal);
        values.put(Utilidades.CAMPO_PESO, weightAnimal);
        values.put(Utilidades.CAMPO_PRECIO, priceAnimal);
        values.put(Utilidades.CAMPO_TARA, tareAnimal);
        values.put(Utilidades.CAMPO_TOTAL_PAGAR, (total - ((total*tareAnimal)/100)));
        values.put(Utilidades.CAMPO_NUMERO_ARETE, earringNumber);

        long idResult = db.insert(Utilidades.TABLA_COMPRA_DETALLE, Utilidades.CAMPO_ID_CITAS, values);

        db.close();

        if(idResult == -1){
            return false;
        }else{
            return true;
        }
    }

    private boolean insertNewAnimal(int typeAnimal, int raceAnimal, double weightAnimal, double priceAnimal, int tareAnimal, int earringNumber) {
        SQLiteDatabase db = conn.getWritableDatabase();


        double total = (weightAnimal * priceAnimal);

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_GANADO, typeAnimal);
        values.put(Utilidades.CAMPO_RAZA, raceAnimal);
        values.put(Utilidades.CAMPO_PESO, weightAnimal);
        values.put(Utilidades.CAMPO_PRECIO, priceAnimal);
        values.put(Utilidades.CAMPO_TARA, tareAnimal);
        values.put(Utilidades.CAMPO_TOTAL_PAGAR, (total - ((total*tareAnimal)/100)));
        values.put(Utilidades.CAMPO_NUMERO_ARETE, earringNumber);

        long idResult = db.insert(Utilidades.TABLA_COMPRA_DETALLE, Utilidades.CAMPO_ID_CITAS, values);

        db.close();

        if(idResult == -1){
            return false;
        }else{
            return true;
        }
    }

    private int modifieAnimal(int typeAnimal, int raceAnimal, double weightAnimal, double priceAnimal, int tareAnimal, int earringNumber){
        SQLiteDatabase db = conn.getWritableDatabase();

        double total = (weightAnimal * priceAnimal);

        String[] id_animal = {String.valueOf(id_animal_modifie)};

        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_GANADO, typeAnimal);
        values.put(Utilidades.CAMPO_RAZA, raceAnimal);
        values.put(Utilidades.CAMPO_PESO, weightAnimal);
        values.put(Utilidades.CAMPO_PRECIO, priceAnimal);
        values.put(Utilidades.CAMPO_TARA, tareAnimal);
        values.put(Utilidades.CAMPO_TOTAL_PAGAR, (total - ((total*tareAnimal)/100)));
        values.put(Utilidades.CAMPO_NUMERO_ARETE, earringNumber);

        int updated = db.update(Utilidades.TABLA_COMPRA_DETALLE, values, Utilidades.CAMPO_ID_COMPRA_DETALLE + " = ?", id_animal);

        db.close();

        return updated;
    }

    public void cancelAnimal(View view){
        finish();
    }

}
