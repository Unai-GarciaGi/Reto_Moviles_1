package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
    private Spinner spinnerPrioridadTarea;
    private Context esto = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_nueva_tarea);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextCoste = findViewById(R.id.editTextCoste);
        spinnerPrioridadTarea = findViewById(R.id.spinnerPrioridad);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getBoolean("modificar")){
            String nombre = bundle.getString("nombre");
            BDD admin = new BDD(this, "administracion", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select * from tarea" ,null);
            if (fila.moveToFirst()) {
                do {
                    if (fila.getString(0).equals(nombre)) {
                        editTextNombre.setText(fila.getString(0));
                        editTextDescripcion.setText(fila.getString(1));
                        String[] fecha = fila.getString(2).split(" ");
                        editTextFecha.setText(fecha[0] + " " + fecha[2] + " " + fecha[1] + " " + fecha[5]);
                        String prioridad = (fila.getString(4));
                        if (prioridad.equalsIgnoreCase("urgente")) {
                            spinnerPrioridadTarea.setSelection(0);
                        } else if (prioridad.equalsIgnoreCase("alta")) {
                            spinnerPrioridadTarea.setSelection(1);
                        } else if (prioridad.equalsIgnoreCase("media")) {
                            spinnerPrioridadTarea.setSelection(2);
                        } else if (prioridad.equalsIgnoreCase("baja")) {
                            spinnerPrioridadTarea.setSelection(3);
                        }
                        editTextCoste.setText(fila.getString(3));
                    }
                } while (fila.moveToNext());
            }
        }
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
        if(id == R.id.abHome){
            abOpciones.abHome(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelar(View view) {
        finish();
    }

    public void registrar(View view) {
       String nombre = editTextNombre.getText().toString();
       editTextNombre.setText(nombre);
       String descripcion = editTextDescripcion.getText().toString();
       String fecha = editTextFecha.getText().toString();
       String coste = editTextCoste.getText().toString();

       Spinner spinnerPrioridadDatos = findViewById(R.id.spinnerPrioridad);
       int posSpin = spinnerPrioridadDatos.getSelectedItemPosition();
       String prioridad = "";
       switch(posSpin){
           case 0:
               prioridad = "Urgente";
               break;
           case 1:
               prioridad = "Alta";
               break;
           case 2:
               prioridad = "Media";
               break;
           case 3:
               prioridad = "Baja";
               break;
       }

       Date fechaComprobada = comprobarFecha(fecha);

       if(fechaComprobada != null){

    if (!nombre.equals("")&& !descripcion.equals("") && !coste.equals("")) {
        alta(nombre, descripcion, coste, fechaComprobada, prioridad);
        Intent i = new Intent(this, PantallaPrincipal.class );
        startActivity(i);
    } else{
        Toast.makeText(this,R.string.toastCamposVacios,Toast.LENGTH_LONG).show();
    }
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
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Bundle bundle = getIntent().getExtras();
        String nombres[] = {bundle.getString("nombre")};

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("descripcion", descripcion);
        registro.put("fecha", String.valueOf(fecha));
        registro.put("prioridad", prioridad);
        registro.put("coste",coste);

        if(bundle.getBoolean("modificar")){
            bd.update("tarea", registro, "nombre = ?", nombres);
        }else{
            registro.put("realizada", false);
            bd.insert("tarea", null, registro);
        }
        bd.close();
        Toast.makeText(this, R.string.toastDatosGuardadosTarea, Toast.LENGTH_SHORT).show();
    }

}