package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaListaTareas extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Context esto = this;
    private String nombre;

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
    public void onCreateContextMenu(ContextMenu menu,
                                    View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.eligeOpcion);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mantener_pulsado, menu);
        TextView tv = (TextView) v;
        nombre = tv.getText().toString();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.modificar:
                Intent i = new Intent(esto, PantallaNuevaTarea.class);
                i.putExtra("modificar", true);
                i.putExtra("nombre", nombre);
                esto.startActivity(i);
                return true;
            case R.id.borrar:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.alertTitle);
                builder.setMessage(R.string.alertMessage);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        consultaBDDBorrado(nombre);
                        Toast.makeText(esto, R.string.borradoTarea, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        Toast.makeText(esto, R.string.canceladoBorrado, Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();



                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
        Cursor fila = bd.rawQuery("select nombre from tarea" ,null);
        if (fila.moveToFirst()) {
            TextView textViewTareaGeneral = new TextView(this);
            textViewTareaGeneral.setHeight(105);
            textViewTareaGeneral.setTextSize(32);
            textViewTareaGeneral.setText(fila.getString(0));
            insertarOnClick(textViewTareaGeneral);
            linearLayout.addView(textViewTareaGeneral);
            while (fila.moveToNext()){
                textViewTareaGeneral = new TextView(this);
                textViewTareaGeneral.setHeight(105);
                textViewTareaGeneral.setTextSize(32);
                textViewTareaGeneral.setText(fila.getString(0));
                insertarOnClick(textViewTareaGeneral);
                linearLayout.addView(textViewTareaGeneral);
            }
        } else
            Toast.makeText(this, R.string.toastNoExiste, Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void insertarOnClick(TextView tv){
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(esto,PantallaDetallesTarea.class);
                    i.putExtra("nombre", tv.getText().toString());
                    startActivity(i);
                }
            });
            registerForContextMenu(tv);
        }

    public void consultaBDDBorrado(String texto) {
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String sql = "delete from tarea where nombre = "  + "\'" + texto + "\'";
        bd.execSQL(sql);
        bd.close();

        finish();
        startActivity(getIntent());
    }

    public void mostrarHechas(View view){
        linearLayout.removeAllViews();
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre from tarea where realizada = true" ,null);
        if (fila.moveToFirst()) {
            TextView textViewTareaGeneral = new TextView(this);
            textViewTareaGeneral.setHeight(105);
            textViewTareaGeneral.setTextSize(32);
            textViewTareaGeneral.setText(fila.getString(0));
            insertarOnClick(textViewTareaGeneral);
            linearLayout.addView(textViewTareaGeneral);
            while (fila.moveToNext()){
                textViewTareaGeneral = new TextView(this);
                textViewTareaGeneral.setHeight(105);
                textViewTareaGeneral.setTextSize(32);
                textViewTareaGeneral.setText(fila.getString(0));
                insertarOnClick(textViewTareaGeneral);
                linearLayout.addView(textViewTareaGeneral);
            }
            Toast.makeText(this, R.string.toastFiltrarHechas, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, R.string.toastNoExiste, Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void mostrarPendientes(View view){
        linearLayout.removeAllViews();
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select nombre from tarea where realizada = false" ,null);
        if (fila.moveToFirst()) {
            TextView textViewTareaGeneral = new TextView(this);
            textViewTareaGeneral.setHeight(105);
            textViewTareaGeneral.setTextSize(32);
            textViewTareaGeneral.setText(fila.getString(0));
            insertarOnClick(textViewTareaGeneral);
            linearLayout.addView(textViewTareaGeneral);
            while (fila.moveToNext()){
                textViewTareaGeneral = new TextView(this);
                textViewTareaGeneral.setHeight(105);
                textViewTareaGeneral.setTextSize(32);
                textViewTareaGeneral.setText(fila.getString(0));
                insertarOnClick(textViewTareaGeneral);
                linearLayout.addView(textViewTareaGeneral);
            }
            Toast.makeText(this, R.string.toastFiltrarPendientes, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, R.string.toastNoExiste, Toast.LENGTH_SHORT).show();
        bd.close();
    }

    }
