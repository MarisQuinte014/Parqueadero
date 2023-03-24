package com.example.parqueadero;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class ClsOpenHelper extends SQLiteOpenHelper {
    public ClsOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table TblVehiculo" +
                "(placa text primary key, " +
                "tipo text not null, " +
                "marca text not null, " +
                "modelo text not null, " +
                "activo text default 'Si'," +
                "mensualidad integer not null)"
        );
        db.execSQL("create table TblTiquete " +
                "( codigo text primary key," +
                " fecha text not null," +
                " placa text not null," +
                " activo text default 'Si'," +
                " constraint FK_Tiquete foreign key(placa) references TblVehiculo(placa))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table TblVehiculo");
        db.execSQL("drop table TblTiquete");{
            onCreate(db);
        }
    }
}
