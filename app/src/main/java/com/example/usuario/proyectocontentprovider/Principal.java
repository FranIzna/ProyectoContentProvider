package com.example.usuario.proyectocontentprovider;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.usuario.proyectocontentprovider.bdd.Proveedor;
import com.example.usuario.proyectocontentprovider.bdd.Contrato;
import com.example.usuario.proyectocontentprovider.clases.*;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        inicio();
    }
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sincronizar) {
            String s=getString(R.string.err);
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle(this.getString(R.string.importante));
            dialogo.setMessage(s);
            dialogo.setCancelable(false);
            dialogo.setNegativeButton(this.getString(R.string.cancelar), null);
            dialogo.setPositiveButton(this.getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    sincroniza();
                }
            });
            dialogo.show();


            return true;
        }
        if (id == R.id.canciones) {
            todo();

            return true;
        }
        if (id == R.id.disco) {
            album();
            return true;
        }
        if (id == R.id.interprete) {
            artist();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*****************/
    private ListView lv;
    private Adaptador ad;
    private List<Cancion> listCanciones;
    private List<Interprete> listInterpretes;
    private List<Disco> listDiscos;
    private SharedPreferences preferencia;
    private Extrae e;
    private static final int CANCION=1,DISCO=2,INTERPRETE=3;
    private boolean reproduce=true;


    public void inicio() {
        preferencia=getSharedPreferences("MiPref", this.MODE_PRIVATE);//crea grupo pref compartida
        e=new Extrae(this);

        if (preferencia.getBoolean("sincro",false)){//si tenemos la sincronizacion automatica activada
            Cursor c=sacaBdd();
            System.out.println("YOKC: "+c);
            listCanciones=e.sacaCancionesList(c,0);
            generaAdaptador(c);
        } else {
            primeraEjecucion();
            generaAdaptador(sacaBdd());

            SharedPreferences.Editor ed=preferencia.edit();
            ed=preferencia.edit();
                System.out.println("Sincro on");
                ed.putBoolean("sincro", true);
                ed.commit();
        }

    /*  imprime(listCanciones);
        imprime(listInterpretes);
        imprime(listDiscos);*/

//        getLoaderManager().initLoader(1, null, this);
    }
    public void primeraEjecucion(){
        listCanciones=e.insertaCancionesList(e.sacaCancionesCursor(), 0);

        listInterpretes=e.insertaArtistasList(e.sacaArtistasCursor());

        listDiscos=e.insertaDiscosList(e.sacaAlbumsCursor());
    }
    public Cursor sacaBdd(){
        return getContentResolver().query(Contrato.TablaCancion.CONTENT_URI, null, null, null, null);//seguir aqui;
    }
    public void sincroniza(){
        System.out.println("SINCRONIZA");
        getContentResolver().delete(Contrato.TablaCancion.CONTENT_URI,null,null);
        getContentResolver().delete(Contrato.TablaDisco.CONTENT_URI,null,null);
        getContentResolver().delete(Contrato.TablaInterprete.CONTENT_URI, null, null);

        primeraEjecucion();
        generaAdaptador(sacaBdd());
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        switch (id){
            case CANCION:
                uri =Contrato.TablaCancion.CONTENT_URI;
                return new CursorLoader(this, uri, null, null, null, Contrato.TablaCancion.TABLA + " collate localized asc");
            case DISCO:
                uri = Contrato.TablaCancion.CONTENT_URI;
                return new CursorLoader(this, uri, null, null, null, Contrato.TablaDisco.TABLA + " collate localized asc");
            case INTERPRETE:
                uri = Contrato.TablaCancion.CONTENT_URI;
                return new CursorLoader(this, uri, null, null, null, Contrato.TablaInterprete.TABLA + " collate localized asc");
            default:
                uri = Contrato.TablaCancion.CONTENT_URI;
                return new CursorLoader(this, uri, null, null, null, Contrato.TablaDisco.TABLA + " collate localized asc");
        }

    }
    @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) { ad.swapCursor(data);  }
    @Override public void onLoaderReset(Loader<Cursor> loader) {ad.swapCursor(null); }


    public void generaAdaptador(final Cursor c){
        ad = new Adaptador(this, c);

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                if(reproduce){
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    String ruta=listCanciones.get(posicion).getRutaSong();
                    Uri data = Uri.parse("file://"+ruta);
                    intent.setDataAndType(data, "audio/mp3");
                    startActivity(intent);
                }
            }
        });
        registerForContextMenu(lv);
    }
    public void todo() {
        reproduce=true;
        Cursor c = getContentResolver().query(Contrato.TablaCancion.CONTENT_URI, null, null, null, null);
        generaAdaptador(c);
    }
    public void album(){
        reproduce=false;
        Cursor c = getContentResolver().query(Contrato.TablaDisco.CONTENT_URI, null, null, null, null);
        generaAdaptador(c);
    }
    public void artist(){
        reproduce=false;
        Cursor c = getContentResolver().query(Contrato.TablaInterprete.CONTENT_URI, null, null, null, null);
        generaAdaptador(c);
    }


}
