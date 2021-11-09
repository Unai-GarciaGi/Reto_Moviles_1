package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaListaTareas extends AppCompatActivity {

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_tareas);
        linearLayout = findViewById(R.id.llLista);
        consultaBDD();
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

    public void consultaBDD() {
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre from tarea" ,null);
        if (fila.moveToFirst()) {
            TextView textViewTareaGeneral = new TextView(this);
            textViewTareaGeneral.setHeight(100);
            textViewTareaGeneral.setTextSize(32);
            textViewTareaGeneral.setText(fila.getString(0));
            linearLayout.addView(textViewTareaGeneral);
            while (fila.moveToNext()){
                TextView textViewTareaGeneral2 = new TextView(this);
                textViewTareaGeneral2.setHeight(100);
                textViewTareaGeneral2.setTextSize(32);
                textViewTareaGeneral2.setText(fila.getString(0));
                linearLayout.addView(textViewTareaGeneral2);
            }
        } else
            Toast.makeText(this, "No existen datos en la BDD", Toast.LENGTH_SHORT).show();
        bd.close();
    }

}