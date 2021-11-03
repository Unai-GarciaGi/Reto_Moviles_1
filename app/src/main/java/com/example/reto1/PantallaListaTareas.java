package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PantallaListaTareas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_tareas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ActionBarOpciones abOpciones = new ActionBarOpciones();
        int id = item.getItemId();
        if (id==R.id.abNuevaTarea) {
            abOpciones.abNuevaTarea(this);
        }
        if (id==R.id.abContrasena) {
            abOpciones.abContrasena(this);
        }
        if (id==R.id.abAcercaDe) {
            abOpciones.abAcercaDe(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void consultaBDD(View v) {
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from tarea" ,null);
        if (fila.moveToFirst()) {
           // et2.setText(fila.getString(0));
           // et3.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existen datos en la BDD", Toast.LENGTH_SHORT).show();
        bd.close();
    }

}