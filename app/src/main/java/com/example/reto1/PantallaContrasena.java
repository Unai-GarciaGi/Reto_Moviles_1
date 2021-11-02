package com.example.reto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences.Editor;

public class PantallaContrasena extends AppCompatActivity {

    private EditText editVieja;
    private EditText editNueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_contrasena);

        editVieja = findViewById(R.id.editPasswordAntigua);
        editNueva = findViewById(R.id.editPasswordNueva);
    }

    public void cambiarContrasena(View view){
        SharedPreferences preferences = getSharedPreferences("contrasena", Context.MODE_PRIVATE);
        String contrasenaPreferences = preferences.getString("contrasena", "asdf");
        if(contrasenaPreferences.equals(editVieja.getText().toString())){
            Editor editor = preferences.edit();
            editor.putString("contrasena", editNueva.getText().toString());
            editor.commit();
            finish();
            Toast.makeText(this,R.string.contrasenaCambiada,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,R.string.contrasenaNoCambiada,Toast.LENGTH_LONG).show();
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
        if (id==R.id.abNuevaTarea) {
            abOpciones.abNuevaTarea(this);
        }
        if (id==R.id.abAcercaDe) {
            abOpciones.abAcercaDe(this);
        }
        return super.onOptionsItemSelected(item);
    }
}