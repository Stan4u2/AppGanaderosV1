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

    static String action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_animal);

        conn = new ConexionSQLiteHelper(this, "bd_ganado", null, 1);

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

            switch(action){
                case "insert":
                    break;

                case "modifie":
                    loadData();
                    break;
            }

        }
    }

    private void loadData() {

    }

    public void onResume(){
        super.onResume();

        if(process_race == true){
            consultListRaceAnimals();

            for(int i = 0; i < raceAnimal.size(); i++){
                if(raceAnimal.get(i).getId_raza() == id_new_race){
                    spinner_race_animal.setSelection(i+1);
                }
            }
            process_race = false;
        }

        if(process_type == true){
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
        db.close();

        obtaninList_Types();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeAnimalList);

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
        if(
            spinner_types_animal.getSelectedItemId() == 0 ||
            spinner_race_animal.getSelectedItemId() == 0 ||
            weight_animal.getText().toString().isEmpty() ||
            price_animal.getText().toString().isEmpty() ||
            tare_animal.getText().toString().isEmpty() ||
            number_earring_animal.getText().toString().isEmpty()
        ){
            Toast.makeText(getApplicationContext(), "¡¡Campos Vacios!!", Toast.LENGTH_LONG).show();
        }else{
            if(action.equals("insert")){
                boolean inserted = insertNewAnimal(
                        selectedType,
                        selectedRace,
                        Double.parseDouble(weight_animal.getText().toString()),
                        Double.parseDouble(price_animal.getText().toString()),
                        Integer.valueOf(tare_animal.getText().toString()),
                        Integer.valueOf(number_earring_animal.getText().toString())
                );
            }
        }
    }

    private boolean insertNewAnimal(int typeAnimal, int raceAnimal, double weightAnimal, double priceAnimal, int tareAnimal, int earringNumber) {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_GANADO, typeAnimal);
        values.put(Utilidades.CAMPO_RAZA, raceAnimal);
        values.put(Utilidades.CAMPO_PESO, weightAnimal);
        values.put(Utilidades.CAMPO_PRECIO, priceAnimal);
        values.put(Utilidades.CAMPO_TARA, tareAnimal);
        values.put(Utilidades.CAMPO_NUMERO_ARETE, earringNumber);

        Long idResult = db.insert(Utilidades.TABLA_COMPRA_DETALLE, Utilidades.CAMPO_ID_CITAS, values);

        db.close();

        if(idResult == -1){
            return false;
        }else{
            insert_new_appointment.id_new_person = idResult.intValue();
            return true;
        }

        /*
        public static final String TABLA_COMPRA_DETALLE = "compra_detalle";
        public static final String CAMPO_ID_COMPRA_DETALLE = "id_compra_detalle";
        public static final String CAMPO_GANADO = "ganado";
        public static final String CAMPO_RAZA = "raza";
        public static final String CAMPO_PESO = "peso";
        public static final String CAMPO_PRECIO = "precio";
        public static final String CAMPO_TARA = "tara";
        public static final String CAMPO_TOTAL_PAGAR = "total";
        public static final String CAMPO_NUMERO_ARETE = "numero_arete";
        public static final String CAMPO_COMPRA = "compra";

        public static final String CREAR_TABLA_COMPRA_DETALLE =
                "CREATE TABLE " + TABLA_COMPRA_DETALLE + "("
                        + CAMPO_ID_COMPRA_DETALLE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + CAMPO_GANADO + " INTEGER REFERENCES " + TABLA_GANADO + "(" + CAMPO_ID_GANADO + "), "
                        + CAMPO_RAZA + " INTEGER REFERENCES " + TABLA_RAZA + "(" + CAMPO_ID_RAZA + "), "
                        + CAMPO_PESO + " REAL, "
                        + CAMPO_PRECIO + " REAL, "
                        + CAMPO_TARA + " REAL, "
                        + CAMPO_TOTAL_PAGAR + " REAL, "
                        + CAMPO_NUMERO_ARETE + " INTEGER, "
                        + CAMPO_COMPRA + " INTEGER REFERENCES " + TABLA_COMPRAS + "(" + CAMPO_ID_COMPRA + "))";
         */
    }
}
