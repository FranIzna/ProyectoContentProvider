package com.example.usuario.proyectocontentprovider.bdd;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by usuario on 12/01/2016.
 */
public class Proveedor extends ContentProvider {
    public static final UriMatcher convierteUri2Int;
    public static final int CANCION = 1, CANCION_ID = 2;
    public static final int DISCO = 3, DISCO_ID = 4;
    public static final int INTERPRETE = 5, INTERPRETE_ID = 6;
    private static SQLiteDatabase bd;
    static {
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        /********- CANCION -*/
        convierteUri2Int.addURI(Contrato.TablaCancion.AUTHORITY,
                Contrato.TablaCancion.TABLA, CANCION);
        convierteUri2Int.addURI(Contrato.TablaCancion.AUTHORITY,
                "asd", CANCION_ID);
        /********- DISCO -*/
        convierteUri2Int.addURI(Contrato.TablaDisco.AUTHORITY,
                Contrato.TablaDisco.TABLA, DISCO);
//        convierteUri2Int.addURI(Contrato.TablaDisco.AUTHORITY,
//                Contrato.TablaDisco.TABLA + "/#", DISCO_ID);
        /********- INTERPRETE -*/
        convierteUri2Int.addURI(Contrato.TablaInterprete.AUTHORITY,
                Contrato.TablaInterprete.TABLA, INTERPRETE);
//        convierteUri2Int.addURI(Contrato.TablaInterprete.AUTHORITY,
//                Contrato.TablaInterprete.TABLA + "/#", INTERPRETE_ID);
        /*********/
    }
    private Ayudante abd;
    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        bd=abd.getReadableDatabase();
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {//Devuelve el tipo mime que corresponde a la uri con que se ha llamado
        switch (convierteUri2Int.match(uri)) {
            /********- CANCION -*/
            case CANCION:
                return Contrato.TablaCancion.MJLTIPLE_MIME;
            case CANCION_ID:
                return Contrato.TablaCancion.SINGLE_MIME;
            /********- DISCO -*/
            case DISCO:
                return Contrato.TablaDisco.MJLTIPLE_MIME;
            case DISCO_ID:
                return Contrato.TablaDisco.SINGLE_MIME;
            /********- INTERPRETE -*/
            case INTERPRETE:
                return Contrato.TablaInterprete.MJLTIPLE_MIME;
            case INTERPRETE_ID:
                return Contrato.TablaInterprete.SINGLE_MIME;
            /*********/
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
//        String tipo=getType(uri);
//        System.out.println("TIPO: "+tipo);
        long rowId=0;
        //Si el ContentValues es nulo, crea un ContentValues
        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException(" null ");
        }
        int conver=convierteUri2Int.match(uri);
        if (conver != CANCION && conver != CANCION_ID &&
                conver != DISCO && conver != DISCO_ID &&
                conver != INTERPRETE && conver != INTERPRETE_ID) {
            throw new IllegalArgumentException("URI desconocida : " + uri);//SI no es correcta la Uri
        }
        // Insercion de nueva fila
        SQLiteDatabase db = abd.getWritableDatabase();//Conectamos a la base de datos en modo escritura
        /******/
        if(uri.toString().contains("cancion")){
//            System.out.println("TIPO: "+values.getClass());
//            System.out.println("--__ "+values.toString());
            rowId = db.insert(Contrato.TablaCancion.TABLA, null, values);
            if (rowId > 0) {
                //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaCancion.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }else if(uri.toString().contains("disco")){
            rowId = db.insert(Contrato.TablaDisco.TABLA, null, values);
            if (rowId > 0) {
                //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaDisco.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }else if(uri.toString().contains("interprete")){
            rowId = db.insert(Contrato.TablaInterprete.TABLA, null, values);
            if (rowId > 0) {
                //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                Uri uri_actividad = ContentUris.withAppendedId(Contrato.TablaInterprete.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }
        throw new SQLException("Error al insertar fila en : " + uri);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();// Vuelve a abrir la base de datos para conectar con ella en modo escritura
        int match = convierteUri2Int.match(uri);//Obtengo la uri
        int affected;
        long idActividad=0;
        switch (match) {
            case CANCION: //Muchas canciones
                affected = db.delete(Contrato.TablaCancion.TABLA, selection, selectionArgs);
                break;
            case CANCION_ID: //Una cancion
                idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaCancion.TABLA, Contrato.TablaCancion._ID + "= ?", new String[]{idActividad + ""});
                break;
            /***/
            case DISCO:
                affected = db.delete(Contrato.TablaDisco.TABLA, selection, selectionArgs);
                break;
            case DISCO_ID: //Una cancion
                idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaDisco.TABLA, Contrato.TablaDisco._ID + "= ?", new String[]{idActividad + ""});
                break;
            /***/
            case INTERPRETE:
                affected = db.delete(Contrato.TablaInterprete.TABLA, selection, selectionArgs);
                break;
            case INTERPRETE_ID: //Una cancion
                idActividad = ContentUris.parseId(uri);
                affected = db.delete(Contrato.TablaInterprete.TABLA, Contrato.TablaInterprete._ID + "= ?", new String[]{idActividad + ""});
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        // Notificar cambio asociado a la urigetContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;//Devuelve el numero de filas borradas
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int affected;
        switch (convierteUri2Int.match(uri)) {
            case CANCION:
                affected = db.update(Contrato.TablaCancion.TABLA, values, selection, selectionArgs);
                break;
            case CANCION_ID:
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(Contrato.TablaCancion.TABLA, values,
                        Contrato.TablaCancion._ID + "= ?", new String[]{idActividad});
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = abd.getReadableDatabase();

        int match = convierteUri2Int.match(uri);
        Cursor c;

        switch (match) {
            case CANCION:
//                c = db.query(Contrato.TablaCancion.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                String s="Select * from cancion LEFT JOIN disco ON (cancion.iddisco = disco._id) LEFT JOIN interprete ON (disco.idinterprete=interprete._ID)";
                c=db.rawQuery(s,null);
                break;
            case CANCION_ID:
                // Consultando un solo registro basado en el Id del Uri
                //System.out.println("------------------------");
                long idActividad = ContentUris.parseId(uri);
                c = db.query(Contrato.TablaCancion.TABLA, projection, Contrato.TablaCancion._ID + "= ? ", new String[]{idActividad + ""},
                        null, null, sortOrder);
                break;
            case DISCO:
                c = db.query(Contrato.TablaDisco.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case INTERPRETE:
                c = db.query(Contrato.TablaInterprete.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(), Contrato.TablaCancion.CONTENT_URI);
        return c;
    }

}