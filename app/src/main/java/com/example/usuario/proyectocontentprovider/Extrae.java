package com.example.usuario.proyectocontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.usuario.proyectocontentprovider.bdd.Contrato;
import com.example.usuario.proyectocontentprovider.clases.Cancion;
import com.example.usuario.proyectocontentprovider.clases.Disco;
import com.example.usuario.proyectocontentprovider.clases.Interprete;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 16/01/2016.
 */
public class Extrae {
    private static Context c;
    public Extrae(Context c){
        this.c=c;
    }
    public Cursor sacaCancionesCursor(){
        return c.getContentResolver().query(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
    }
    public Cursor sacaAlbumsCursor(){
        return c.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);
    }
    public Cursor sacaArtistasCursor(){
        return c.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
    }
    public List sacaDiscosList(Cursor cur){
        List<Disco> l = new ArrayList();
        Uri uri= Contrato.TablaDisco.CONTENT_URI;
        int id=0,idInterprete=0;
        String album="",artist="",ruta="";

//        Cursor cur = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cur.moveToNext()) {
            id=cur.getInt(cur.getColumnIndex("_id"));
            album = cur.getString(cur.getColumnIndex("album"));
            artist = cur.getString(cur.getColumnIndex("artist"));
            idInterprete=cur.getInt(cur.getColumnIndex("artist_id"));

            Disco i=new Disco(id,album,artist,idInterprete,ruta);
            c.getContentResolver().insert(uri,i.getContentValues());
            l.add(i);
        }
        return l;
    }
    public List sacaArtistasList(Cursor cur){
        List<Interprete> l = new ArrayList();
        Uri uri= Contrato.TablaInterprete.CONTENT_URI;

        int idArtista=0;
        String artist="";

//        Cursor cur = getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cur.moveToNext()) {
            idArtista=cur.getInt(cur.getColumnIndex("_id"));
            artist = cur.getString(cur.getColumnIndex("artist"));

            Interprete i=new Interprete(idArtista,artist);

            l.add(i);
        }
        return l;
    }
    public List sacaCancionesList(Cursor cur, int idBusca) {
        List<Cancion> l = new ArrayList();
        Uri uri= Contrato.TablaCancion.CONTENT_URI;

        int id=0,idArtista=0,idAlbum=0, duracion=0;
        String title="", ruta="";
        Cancion song;
        /*Cursor cur = getContentResolver().query(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null)*/;
        int i = 0;
        while (cur.moveToNext()) {
                id=cur.getInt(cur.getColumnIndex("_id"));
                title = cur.getString(cur.getColumnIndex("titulo"));
                duracion = cur.getInt(cur.getColumnIndex("duracion"));
                idArtista = cur.getInt(cur.getColumnIndex("idartista"));
                idAlbum = cur.getInt(cur.getColumnIndex("iddisco"));
                ruta=cur.getString(cur.getColumnIndex("rutasong"));
//            long id, long idAtista, long idDisco, long duracion, String titulo, String compositor
//                if(!exist(nombre)){
//            if(idBusca!=0){
//                System.out.println("sin album");
//                c=new Cancion(id,idArtista,idAlbum,duracion,title,composer);
//            }else{
//               if(idBusca==(idAlbum)){
//
//               }else
            song=new Cancion(id,idArtista,idAlbum,duracion,title,null,ruta);

//            }


            l.add(song);
//                }
//            }
            i++;
        }

        cur.close();
        return l;
    }
    public List insertaDiscosList(Cursor cur){
        List<Disco> l = new ArrayList();
        Uri uri= Contrato.TablaDisco.CONTENT_URI;
        int id=0,idInterprete=0;
        String album="",artist="",ruta="";

//        Cursor cur = getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cur.moveToNext()) {
            id=cur.getInt(cur.getColumnIndex("_id"));
            album = cur.getString(cur.getColumnIndex("album"));
            artist = cur.getString(cur.getColumnIndex("artist"));
            idInterprete = cur.getInt(cur.getColumnIndex("artist_id"));
            ruta=cur.getString(cur.getColumnIndex("album_art"));

            Disco i=new Disco(id,album,artist,idInterprete,ruta);
//            long id, String nombre, String interprete, long idInterprete) {
            c.getContentResolver().insert(uri,i.getContentValues());
            l.add(i);
        }
        return l;
    }
    public List insertaArtistasList(Cursor cur){
        List<Interprete> l = new ArrayList();
        Uri uri= Contrato.TablaInterprete.CONTENT_URI;

        int idArtista=0;
        String artist="";

//        Cursor cur = getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cur.moveToNext()) {
            idArtista=cur.getInt(cur.getColumnIndex("_id"));
            artist = cur.getString(cur.getColumnIndex("artist"));

            Interprete i=new Interprete(idArtista,artist);
            c.getContentResolver().insert(uri,i.getContentValues());

            l.add(i);
        }
        return l;
    }
    public List insertaCancionesList(Cursor cur, int idBusca) {
        List<Cancion> l = new ArrayList();
        Uri uri= Contrato.TablaCancion.CONTENT_URI;

        int id=0,idArtista=0,idAlbum=0, duracion=0;
        String title="", composer="",rutaSong="";
        Cancion song;
        /*Cursor cur = getContentResolver().query(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null)*/;
        int i = 0;
        while (cur.moveToNext()) {
                id=cur.getInt(cur.getColumnIndex("_id"));
                title = cur.getString(cur.getColumnIndex("title"));
                duracion = cur.getInt(cur.getColumnIndex("duration"));
                idArtista = cur.getInt(cur.getColumnIndex("artist_id"));
                composer = cur.getString(cur.getColumnIndex("composer"));
                idAlbum = cur.getInt(cur.getColumnIndex("album_id"));
                rutaSong=cur.getString(cur.getColumnIndex("_data"));
//            long id, long idAtista, long idDisco, long duracion, String titulo, String compositor
//                if(!exist(nombre)){
//            if(idBusca!=0){
//                System.out.println("sin album");
//                c=new Cancion(id,idArtista,idAlbum,duracion,title,composer);
//            }else{
//               if(idBusca==(idAlbum)){
//
//               }else

            song=new Cancion(id,idArtista,idAlbum,duracion,title,composer,rutaSong);
            c.getContentResolver().insert(uri, song.getContentValues());
//            }
            l.add(song);
//                }
//            }

        }

        cur.close();
        return l;
    }
}
