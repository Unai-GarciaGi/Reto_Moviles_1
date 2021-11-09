package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    }

    public void consultaBDD(View v) {
        BDD admin = new BDD(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from tarea" ,null);
        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("nombre");
        if (fila.moveToFirst()) {
            if (fila.getString(0).equals(dato)){

                textViewDescripcionTarea.setText(fila.getString(1));
                textViewFechaTarea.setText(fila.getString(2));
                textViewPrioridadTarea.setText(fila.getString(3));
                textViewCosteTarea.setText(fila.getString(4));
            }
        } else
            Toast.makeText(this, "No existen datos en la BDD", Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void volver(View view) {
        finish();
    }
}