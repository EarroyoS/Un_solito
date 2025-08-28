package com.example.unsolito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    Button btnAccesar;
    Button btnCerrar;

    TextInputEditText etNombre;
    TextInputEditText etContrasena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAccesar = findViewById(R.id.btnAccesar);
        btnCerrar = findViewById(R.id.btnCerrar);
        etNombre = findViewById(R.id.etNombre);
        etContrasena = findViewById(R.id.etContrasena);

        btnAccesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString().trim();
                String contrasena = etContrasena.getText().toString().trim();

                if (nombre.equals("eduardo") && contrasena.equals("123456")) {
                    Intent intent = new Intent(view.getContext(), Menu.class); // Cambia si quieres ir a otra pantalla
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });


        btnCerrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(MainActivity.this, "Saliendo del App", Toast.LENGTH_LONG).show();
                finishAffinity(); // Cierra todas las actividades
                System.exit(0);
            }
        });

    }
}