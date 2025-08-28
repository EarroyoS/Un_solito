package com.example.unsolito;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Web extends AppCompatActivity {

    WebView webView;
    Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web);

        btnExit = findViewById(R.id.btnExit);

        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        // Ensure links open within the WebView
        webView.setWebViewClient(new WebViewClient());

        // Load a URL
        webView.loadUrl("https://developer.android.com/develop?hl=es-419");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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