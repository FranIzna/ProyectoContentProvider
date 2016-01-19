package com.example.usuario.proyectocontentprovider.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by usuario on 12/01/2016.
 */
public class Ayudante extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="musica.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql;
//      duracion;
        sql="create table "+ Contrato.TablaCancion.TABLA+ " ("+
                Contrato.TablaCancion._ID+ " integer primary key, "+
                Contrato.TablaCancion.TITULO+" text, "+
                Contrato.TablaCancion.IDDISCO+" integer, "+
                Contrato.TablaCancion.DURACION+" integer, "+
                Contrato.TablaCancion.IDARTISTA+" integer, "+
                Contrato.TablaCancion.RUTASONG+" text)";

        db.execSQL(sql);
//        long id, String nombre, String interprete
        sql="create table "+ Contrato.TablaDisco.TABLA+ " ("+
                Contrato.TablaDisco._ID+ " integer primary key, "+
                Contrato.TablaDisco.NOMBRE+" text, "+
                Contrato.TablaDisco.INTERPRETE+" text, "+
                Contrato.TablaDisco.IDINTERPRETE+" text, "+
                Contrato.TablaDisco.RUTA+" text)";

        db.execSQL(sql);
//        long id, String nombre
        sql="create table "+ Contrato.TablaInterprete.TABLA+ " ("+
                Contrato.TablaInterprete._ID+ " integer primary key, "+
                Contrato.TablaInterprete.NOMBRE+" text)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

        String sql="drop table if exists "
                + Contrato.TablaCancion.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }
}
