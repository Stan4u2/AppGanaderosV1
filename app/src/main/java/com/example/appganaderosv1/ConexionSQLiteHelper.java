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
        db.execSQL(Utilidades.INSERT_RAZA);
        db.execSQL(Utilidades.CREAR_TABLA_GANADO);
        db.execSQL(Utilidades.INSERT_GANADO);
        db.execSQL(Utilidades.CREAR_TABLA_COMPRAS);
        db.execSQL(Utilidades.CREAR_TABLA_COMPRA_DETALLE);
        db.execSQL(Utilidades.CREAR_TABLA_VENTAS);
        db.execSQL(Utilidades.CREAR_TABLA_VENTA_DETALLE);
        db.execSQL(Utilidades.CREAR_TABLA_BITACORA);
        db.execSQL(Utilidades.CREAR_VISTA_CITAS);
        db.execSQL(Utilidades.CREAR_VISTA_COMPRAS);
        db.execSQL(Utilidades.CREAR_VISTA_ANIMAL);
        db.execSQL(Utilidades.CREAR_VISTA_VENTAS);
        db.execSQL(Utilidades.CREAR_VISTA_ANIMAL_VENTA);
        db.execSQL(Utilidades.CREAR_TRIGGER_COMPRA_ANIMALES);
        db.execSQL(Utilidades.CREAR_TRIGGER_COMPRA_ANIMALES_RESTAR);
        db.execSQL(Utilidades.CREAR_TRIGGER_VENTA_ANIMALES);
        db.execSQL(Utilidades.CREAR_TRIGGER_VENTA_ANIMALES_RESTAR);
        db.execSQL(Utilidades.CREAR_TRIGGER_INSERT_APPOINTMENT_BIT);
        db.execSQL(Utilidades.CREAR_TRIGGER_INSERT_PURCHASE_BIT);
        db.execSQL(Utilidades.CREAR_TRIGGER_INSERT_SALE_BIT);
        db.execSQL(Utilidades.CREATE_INDEX_TABLE_APPOINTMENT);
        db.execSQL(Utilidades.CREATE_INDEX_TABLE_PURCHASES);
        db.execSQL(Utilidades.CREATE_INDEX_TABLE_SALES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_PERSONA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_CITAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_RAZA);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_GANADO);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_COMPRAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_COMPRA_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_VENTAS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_VENTA_DETALLE);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_BITACORA);
        db.execSQL("DROP VIEW IF EXISTS " + Utilidades.VIEW_CITA);
        db.execSQL("DROP VIEW IF EXISTS " + Utilidades.VIEW_COMPRAS);
        db.execSQL("DROP VIEW IF EXISTS " + Utilidades.VIEW_ANIMAL_NO_OWNER);
        db.execSQL("DROP VIEW IF EXISTS " + Utilidades.VIEW_VENTAS);
        db.execSQL("DROP VIEW IF EXISTS " + Utilidades.VIEW_ANIMAL_SALE_NO_OWNER);
        db.execSQL("DROP TRIGGER IF EXISTS " + Utilidades.TRIGGER_PURCHASE_ANIMALS);
        db.execSQL("DROP TRIGGER IF EXISTS " + Utilidades.TRIGGER_SUBTRACT_PURCHASE_ANIMALS);
        db.execSQL("DROP TRIGGER IF EXISTS " + Utilidades.TRIGGER_SALE_ANIMALS);
        db.execSQL("DROP TRIGGER IF EXISTS " + Utilidades.TRIGGER_SUBTRACT_SALE_ANIMALS);
        db.execSQL("DROP TRIGGER IF EXISTS " + Utilidades.TRIGGER_INSERT_APPOINTMENT_BIT);
        db.execSQL("DROP TRIGGER IF EXISTS " + Utilidades.TRIGGER_INSERT_PURCHASE_BIT);
        db.execSQL("DROP TRIGGER IF EXISTS " + Utilidades.TRIGGER_INSERT_SALE_BIT);
        db.execSQL("DROP INDEX IF EXISTS " + Utilidades.INDEX_TABLE_APPOINTMENT);
        db.execSQL("DROP INDEX IF EXISTS " + Utilidades.INDEX_TABLE_PURCHASES);
        db.execSQL("DROP INDEX IF EXISTS " + Utilidades.INDEX_TABLE_SALES);
        onCreate(db);*/
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }



}
