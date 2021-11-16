package com.example.reto1;

import android.content.Context;
import android.content.Intent;

public class ActionBarOpciones {

    public ActionBarOpciones(){}

    public void abNuevaTarea(Context contexto){
        Intent i = new Intent(contexto, PantallaNuevaTarea.class);
        i.putExtra("modificar", false);
        contexto.startActivity(i);
    }

    public void abContrasena(Context contexto){
        Intent i = new Intent(contexto, PantallaContrasena.class);
        contexto.startActivity(i);
    }

    public void abAcercaDe(Context contexto){
        Intent i = new Intent(contexto, PantallaAcercaDe.class);
        contexto.startActivity(i);
    }

    public void abHome(Context contexto){
        Intent i = new Intent(contexto, PantallaPrincipal.class);
        contexto.startActivity(i);
    }

}
