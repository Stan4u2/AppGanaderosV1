package com.example.appganaderosv1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appganaderosv1.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidades.INSERTAR_USUARIOS);
        db.execSQL(Utilidades.CREAR_TABLA_PERSONA);
        db.execSQL(Utilidades.CREAR_TABLA_CITAS);
        db.execSQL(Utilidades.CREAR_TABLA_RAZA);
        db.execSQL(Utilidades.CREAR_TABLA_GANADO);
        db.execSQL(Utilidades.CREAR_TABLA_COMPRAS);
        db.execSQL(Utilidades.CREAR_TABLA_COMPRA_DETALLE);
        db.execSQL(Utilidades.CREAR_TABLA_VENTAS);
        db.execSQL(Utilidades.CREAR_TABLA_VENTA_DETALLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_PERSONA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_CITAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_RAZA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_GANADO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_COMPRAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_COMPRA_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_VENTAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.CREAR_TABLA_VENTA_DETALLE);

        onCreate(db);
    }

}
