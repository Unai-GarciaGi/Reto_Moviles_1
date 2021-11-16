package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaDetallesTarea extends AppCompatActivity {

    private TextView textViewNombreTarea;
    private TextView textViewDescripcionTarea;
    private TextView textViewFechaTarea;
    private TextView textViewPrioridadTarea;
    private TextView textViewCosteTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_tarea);

        textViewNombreTarea = findViewById(R.id.textViewNombreTarea);
        textViewDescripcionTarea = findViewById(R.id.textViewDescripcionTarea);
        textViewFechaTarea = findViewById(R.id.textViewFechaTarea);
        textViewPrioridadTarea = findViewById(R.id.textViewPrioridadTarea);
        textViewCosteTarea = findViewById(R.id.textViewCosteTarea);

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
        if(id == R.id.abHome){
            abOpciones.abHome(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void consultaBDD() {
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from tarea" ,null);
        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("nombre");
        if (fila.moveToFirst()) {
            do{
                if (fila.getString(0).equals(dato)){
                    textViewNombreTarea.setText(fila.getString(0));
                    textViewDescripcionTarea.setText(fila.getString(1));
                    String[] fecha = fila.getString(2).split(" ");
                    textViewFechaTarea.setText(fecha[0] + " " + fecha[2] +  " " + fecha[1] + " " + fecha[5]);
                    textViewPrioridadTarea.setText(fila.getString(3));
                    textViewCosteTarea.setText(fila.getString(4));
                }
            }while(fila.moveToNext());
        } else
            Toast.makeText(this, "No existen datos en la BDD", Toast.LENGTH_SHORT).show();
        bd.close();
    }



    public void volver(View view) {
        finish();
    }
}