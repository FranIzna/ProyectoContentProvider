package com.example.usuario.proyectocontentprovider.clases;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.usuario.proyectocontentprovider.bdd.Contrato;


/**
 * Created by usuario on 12/01/2016.
 */
public class Cancion {
    private long id ,idArtista, idDisco, duracion;
    private String titulo, compositor,rutaSong;

    public Cancion(){}

    public Cancion(long id, long idDisco, String titulo) {
        this.id = id;
        this.idDisco = idDisco;
        this.titulo = titulo;
    }
    public Cancion(long idDisco, String titulo) {
        this.idDisco = idDisco;
        this.titulo = titulo;
    }
    public Cancion(String titulo) {
        this.titulo = titulo;
    }

    public Cancion(long id, long idArtista, long idDisco, long duracion, String titulo, String compositor,String rutaSong) {
        this.id = id;
        this.idArtista = idArtista;
        this.idDisco = idDisco;
        this.duracion = duracion;
        this.titulo = titulo;
        this.compositor = compositor;
        this.rutaSong=rutaSong;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", idAtista=" + idArtista +
                ", idDisco=" + idDisco +
                ", duracion=" + duracion +
                ", titulo='" + titulo + '\'' +
                ", compositor='" + compositor + '\'' +
                '}';
    }

    public long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(long idArtista) {
        this.idArtista = idArtista;
    }

    public String getRutaSong() {
        return rutaSong;
    }

    public void setRutaSong(String rutaSong) {
        this.rutaSong = rutaSong;
    }

    public long getIdAtista() {
        return idArtista;
    }

    public void setIdAtista(long idAtista) {
        this.idArtista = idAtista;
    }

    public long getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracion) {
        this.duracion = duracion;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdDisco() {
        return idDisco;
    }

    public void setIdDisco(long idDisco) {
        this.idDisco = idDisco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        //cv.put(ContratoCliente.TablaCliente._ID,this.id);
        cv.put(Contrato.TablaCancion.TITULO,this.titulo);
        cv.put(Contrato.TablaCancion.IDDISCO,this.idDisco);
        cv.put(Contrato.TablaCancion.IDARTISTA,this.idArtista);
        cv.put(Contrato.TablaCancion.DURACION,this.duracion);
        cv.put(Contrato.TablaCancion.RUTASONG,this.rutaSong);
        return cv;
    }

    public void set(Cursor c){ //A partir del cursor recuperar nombre, apellido y telefono
        this.id = c.getLong(c.getColumnIndex(Contrato.TablaCancion._ID));
        this.titulo = c.getString(c.getColumnIndex(Contrato.TablaCancion.TITULO));
        this.idDisco= c.getLong(c.getColumnIndex(Contrato.TablaCancion.IDDISCO));

    }
}
