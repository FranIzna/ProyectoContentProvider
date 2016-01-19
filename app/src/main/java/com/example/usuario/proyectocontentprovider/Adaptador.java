package com.example.usuario.proyectocontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.proyectocontentprovider.clases.Cancion;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 2dam on 18/11/2015.
 */
public class Adaptador extends CursorAdapter{
    private List<Cancion> l;
    private TextView tv1,tv2,tv3;
    private ImageView iv;
    public Adaptador(Context context, Cursor cursor){
        super(context, cursor,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.item, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        tv1 =(TextView)view.findViewById(R.id.tv1);
        tv2 =(TextView)view.findViewById(R.id.tv2);
        tv3 =(TextView)view.findViewById(R.id.tv3);
        iv=(ImageView)view.findViewById(R.id.imageView);
        String sTv1="",sTv2="";

        String[] array = cursor.getColumnNames();

        for(String columnas:array){
            if(columnas.contains("titulo")){//CANCIONES

                iv.setVisibility(View.GONE);
                tv1.setText(cursor.getString(cursor.getColumnIndex("titulo")));
            }

            if(columnas.contains("nombre")){//DISCOS

                meteimg(cursor.getString(cursor.getColumnIndex("ruta")));
                tv2.setText(cursor.getString(cursor.getColumnIndex("nombre")));
            }

            if(columnas.contains("artista")){//ARTISTAS
                iv.setVisibility(View.GONE);
                tv3.setText(cursor.getString(cursor.getColumnIndex("artista")));
            }
            if(tv1.getText().equals("Large Text")){
                String s;
                try {

                    iv.setVisibility(View.GONE);
                    s=cursor.getString(cursor.getColumnIndex("nombre"));
                }catch (Exception e){
                    iv.setVisibility(View.GONE);
                    s=cursor.getString(cursor.getColumnIndex("artista"));
                }

                tv1.setText(s);
                tv2.setText("-");
                tv3.setText("-");
            }
        }
    }
    public void meteimg(String ruta){
        if(ruta!=null){
            File f = new File(ruta);
//            System.out.println("RUTA FOTO: "+f);

            iv.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
        }
    }
}
