package com.example.usuario.proyectocontentprovider.clases;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.usuario.proyectocontentprovider.bdd.Contrato;

/**
 * Created by usuario on 12/01/2016.
 */
public class Disco {
    private long id,idInterprete;
    private String nombre,interprete,ruta;

    public Disco(){}
    public Disco(long id, String nombre, String interprete, long idInterprete,String ruta) {
        this.id = id;
        this.nombre = nombre;
        this.interprete = interprete;
        this.idInterprete=idInterprete;
        this.ruta=ruta;
    }

    @Override
    public String toString() {
        return "Disco{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", interprete='" + interprete + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInterprete() {
        return interprete;
    }

    public void setInterprete(String interprete) {
        this.interprete = interprete;
    }
//    long id, String nombre, String interprete, long idInterprete
    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        //cv.put(ContratoCliente.TablaCliente._ID,this.id);
        cv.put(Contrato.TablaDisco.NOMBRE,this.nombre);
        cv.put(Contrato.TablaDisco.INTERPRETE,this.interprete);
        cv.put(Contrato.TablaDisco.IDINTERPRETE,this.idInterprete);
        cv.put(Contrato.TablaDisco.RUTA,this.ruta);
        return cv;
    }

    public void set(Cursor c){
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaDisco._ID));
        this.nombre = c.getString(c.getColumnIndex(Contrato.TablaDisco.NOMBRE));
        this.interprete= c.getString(c.getColumnIndex(Contrato.TablaDisco.INTERPRETE));

    }
}
