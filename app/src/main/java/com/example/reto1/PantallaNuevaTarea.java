package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PantallaNuevaTarea extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private EditText editTextFecha;
    private EditText editTextCoste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_nueva_tarea);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextCoste = findViewById(R.id.editTextCoste);
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
        if (id == R.id.abContrasena) {
            abOpciones.abContrasena(this);
        }
        if (id == R.id.abAcercaDe) {
            abOpciones.abAcercaDe(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelar(View view) {
        finish();
    }

    public void registrar(View view) {
       String nombre = editTextNombre.getText().toString();
       String descripcion = editTextDescripcion.getText().toString();
       String fecha = editTextFecha.getText().toString();
       String coste = editTextCoste.getText().toString();

       Spinner spinnerPrioridadDatos = findViewById(R.id.spinnerPrioridad);
       String prioridad = spinnerPrioridadDatos.getSelectedItem().toString();

       Date fechaComprobada = comprobarFecha(fecha);

       if(fechaComprobada != null){
            alta(nombre, descripcion, coste, fechaComprobada, prioridad);
       }else{
           Toast.makeText(this,R.string.toastFechaIncorrecta,Toast.LENGTH_LONG).show();
       }
    }

    public Date comprobarFecha(String fecha) {
        Date fechaComprobada=null;

        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            fechaComprobada = formato.parse(fecha);
        }catch (Exception e){
            fechaComprobada = null;
        }
        return fechaComprobada;
    }

    public void alta(String nombre, String descripcion, String coste, Date fecha, String prioridad) {
        BDD admin = new BDD(this,
                "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("descripcion", descripcion);
        registro.put("fecha", String.valueOf(fecha));
        registro.put("prioridad", prioridad);
        registro.put("coste",coste);
        bd.insert("tarea", null, registro);
        bd.close();
        Toast.makeText(this, "Se cargaron los datos de la tarea",
                Toast.LENGTH_SHORT).show();
    }
}