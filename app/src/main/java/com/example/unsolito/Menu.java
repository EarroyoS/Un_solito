package com.example.unsolito;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.button.MaterialButton;

public class Menu extends AppCompatActivity {

    Button btnCalc;
    Button btnImg;
    Button btnWindows;
    Button btnWeb;
    Button btnVideo;
    Button btnGps;
    Button btnAbout;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        btnCalc = findViewById(R.id.btnCalc);
        btnImg = findViewById(R.id.btnImg);
        btnWindows = findViewById(R.id.btnWindows);
        btnWeb = findViewById(R.id.btnWeb);
        btnVideo = findViewById(R.id.btnVideo);
        btnGps = findViewById(R.id.btnGps);
        btnAbout = findViewById(R.id.btnAbout);
        btnExit = findViewById(R.id.btnExit);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCalc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Calculator.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });

        btnImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Images.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });

        btnWindows.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Pestanias.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });

        btnWeb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Web.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });

        btnVideo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Video.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });

        btnGps.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Gps.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(Menu.this)
                        .setTitle("Acerca de la Aplicación")
                        .setMessage(
                                "Desarrollado por:\nEduardo Arroyo Sanchez\n" +
                                        "Materia:\nProgramación Móvil\n" +
                                        "Docente:\nRocío Elizabeth Pulido Alba\n" +
                                        "Versión:\n1.0.2025B"
                        )
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        });


        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), MainActivity.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });
    }



}