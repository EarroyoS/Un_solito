package com.example.unsolito;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Video extends AppCompatActivity {

    VideoView video;

    Button exit;
    Button btnVideo1;
    Button btnVideo2;
    Button btnVideo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
        video = findViewById(R.id.videoView);
        exit = findViewById(R.id.btnExit);
        btnVideo1 = findViewById(R.id.btnVideo1);
        btnVideo2 = findViewById(R.id.btnVideo2);
        btnVideo3 = findViewById(R.id.btnVideo3);
        MediaController mediaController = new MediaController(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Menu.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });

        btnVideo1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mediaController.setAnchorView(video);
                video.setMediaController(mediaController);
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.nature1);
                video.setVideoURI(uri);
                video.start();
            }
        });

        btnVideo2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mediaController.setAnchorView(video);
                video.setMediaController(mediaController);
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.nature2);
                video.setVideoURI(uri);
                video.start();
            }
        });

        btnVideo3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mediaController.setAnchorView(video);
                video.setMediaController(mediaController);
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.nature4);
                video.setVideoURI(uri);
                video.start();
            }
        });


    }
}