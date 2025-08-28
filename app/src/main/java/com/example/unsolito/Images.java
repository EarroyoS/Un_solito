package com.example.unsolito;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Images extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PERMISSION_REQUEST_CAMERA = 100;

    Button btnCamara, btnSave, btnExit;
    ImageView imageView; // Esta variable necesita ser inicializada
    String photoPath;
    Bitmap currentImageBitmap; // Guardar el bitmap actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_images);

        // INICIALIZAR TODOS LOS ELEMENTOS DE LA UI
        btnCamara = findViewById(R.id.btnCamara);
        btnSave = findViewById(R.id.btnSave);
        btnExit = findViewById(R.id.btnExit);
        imageView = findViewById(R.id.imageView); // ¡Esta línea faltaba!

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botón cámara
        btnCamara.setOnClickListener(v -> {
            // Para Android 10+ también necesitamos permisos de almacenamiento para guardar
            String[] permissions;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissions = new String[]{Manifest.permission.CAMERA};
            } else {
                permissions = new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CAMERA);
            } else {
                abrirCamara();
            }
        });

        // Botón salir
        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Menu.class);
            startActivity(intent);
            finish();
        });

        // Agregar funcionalidad al botón guardar
        btnSave.setOnClickListener(v -> {
            if (currentImageBitmap != null) {
                guardarImagen();
            } else {
                Toast.makeText(this, "No hay imagen para guardar. Toma una foto primero.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo abrir la cámara: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            try {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null && imageView != null) {
                        currentImageBitmap = imageBitmap; // Guardar el bitmap
                        imageView.setImageBitmap(imageBitmap);
                        Toast.makeText(this, "Foto capturada exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al procesar la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void guardarImagen() {
        try {
            String fileName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Para Android 10+ usar MediaStore API
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MiApp");

                ContentResolver resolver = getContentResolver();
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                if (imageUri != null) {
                    OutputStream outputStream = resolver.openOutputStream(imageUri);
                    currentImageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.close();

                    Toast.makeText(this, "Imagen guardada en la galería: " + fileName, Toast.LENGTH_LONG).show();
                }
            } else {
                // Para Android 9 y anteriores
                File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                File appDir = new File(picturesDir, "MiApp");

                if (!appDir.exists()) {
                    appDir.mkdirs();
                }

                File imageFile = new File(appDir, fileName);
                FileOutputStream fos = new FileOutputStream(imageFile);
                currentImageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();

                // Notificar a la galería que hay una nueva imagen
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(imageFile);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);

                Toast.makeText(this, "Imagen guardada en: Pictures/MiApp/" + fileName, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar la imagen: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Crear el directorio si no existe
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        photoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}