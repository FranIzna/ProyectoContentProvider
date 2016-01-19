package com.example.usuario.proyectocontentprovider;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Editar extends AppCompatActivity {
    private EditText nombre,disco,interprete;
    private TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptar();
            }
        });
        nombre=(EditText)findViewById(R.id.nom);
        disco=(EditText)findViewById(R.id.dis);
        interprete=(EditText)findViewById(R.id.inter);
        error=(TextView)findViewById(R.id.error);
    }
    public void aceptar(){
        String nom="",dis="",inter="",err="";
        nombre.getText();
        disco.getText();
        interprete.getText();
        if(nom.isEmpty()){
            err="1";
        }
        if(dis.isEmpty()){
            err+="2 ";
        }
        if(inter.isEmpty()){
            err+="3 ";
        }
        error.setText(err);
    }

}
