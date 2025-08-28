package com.example.unsolito;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Pestanias extends AppCompatActivity {

    Button btnExit;

    Button btn2;
    Button btn3;
    ImageView imgView;
    TextView textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pestanias);

        btnExit = findViewById(R.id.btnExit);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        imgView = findViewById(R.id.imgView1);
        textTime = findViewById(R.id.textTime);

        LocalTime ahora = LocalTime.now();
        String horaActual = ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        textTime.setText(horaActual);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (imgView.getVisibility() == View.VISIBLE) {
                    imgView.setVisibility(View.INVISIBLE);
                } else {
                    imgView.setVisibility(View.VISIBLE);
                }
            }
        });

       btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                LocalTime ahora = LocalTime.now();
                String horaActual = ahora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                textTime.setText(horaActual);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Menu.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });
    }
}