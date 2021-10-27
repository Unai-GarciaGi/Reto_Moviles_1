package com.example.reto1;

import android.content.Context;
import android.content.Intent;

public class ActionBarOpciones {

    public ActionBarOpciones(){}

    public void abNuevaTarea(Context contexto){
        Intent i = new Intent(contexto, PantallaNuevaTarea.class);
        contexto.startActivity(i);
    }
    /*
    public void abContrasena(Context contexto){
        Intent i = new Intent(contexto, PantallaContrasena.class);
        contexto.startActivity(i);
    }
    */
    public void abAcercaDe(Context contexto){
        Intent i = new Intent(contexto, PantallaAcercaDe.class);
        contexto.startActivity(i);
    }

}
