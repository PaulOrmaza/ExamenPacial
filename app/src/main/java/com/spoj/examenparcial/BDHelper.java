package com.spoj.examenparcial;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREACIÓN DE LAS TABLAS
        db.execSQL("CREATE TABLE tblVehiculos" +
                "(" +
                "veh_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "veh_cedula INTEGER NOT NULL," +
                "veh_nombre TEXT NOT NULL," +
                "veh_placa TEXT NOT NULL," +
                "veh_fb TEXT NOT NULL," +
                "veh_marca TEXT NOT NULL," +
                "veh_color TEXT NOT NULL," +
                "veh_tipo TEXT NOT NULL," +
                "veh_valor REAL NOT NULL," +
                "veh_multas INTEGER NOT NULL" +
                ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CAMBIE LA VERSIÓN DE LA TABLA DE LA BDD
        db.execSQL("DROP TABLE tblUsuarios");
        onCreate(db);
    }
}
