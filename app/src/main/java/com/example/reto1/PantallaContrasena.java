package com.example.reto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
}