package com.example.usuario.proyectocontentprovider.bdd;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by usuario on 12/01/2016.
 */
public class Contrato {
    private Contrato(){}

    public static abstract class TablaCancion implements BaseColumns {
        public static final String TABLA = "cancion";
        public static final String TITULO = "titulo";
        public static final String IDDISCO = "iddisco";
        public static final String DURACION = "duracion";
        public static final String IDARTISTA = "idartista";
        public static final String RUTASONG = "rutasong";

        public final static String AUTHORITY = "com.example.usuario.proyectocontentprovider";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
    public static abstract class TablaDisco implements BaseColumns{
        public static final String TABLA="disco";
        public static final String NOMBRE="nombre";
        public static final String INTERPRETE="interprete";
        public static final String IDINTERPRETE="idinterprete";
        public static final String RUTA="ruta";

        public final static String AUTHORITY = "com.example.usuario.proyectocontentprovider";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
    public static abstract class TablaInterprete implements BaseColumns{
        public static final String TABLA="interprete";
        public static final String NOMBRE="artista";

        public final static String AUTHORITY = "com.example.usuario.proyectocontentprovider";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    }
}
