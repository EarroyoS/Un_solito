package com.example.unsolito;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class Gps extends AppCompatActivity implements LocationListener {
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;
    private LocationManager locationManager;
    private IMapController mapController;
    private Marker userMarker;
    private boolean locationFound = false;

    Button btnExit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().setUserAgentValue("UnSolito/1.0");

        setContentView(R.layout.activity_gps);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(15.0);

        // ubicación inicial México City
        GeoPoint startPoint = new GeoPoint(19.4326, -99.1332);
        mapController.setCenter(startPoint);

        btnExit = findViewById(R.id.btnExit);

        // 👉 Aquí agregamos el intent al hacer clic
        btnExit.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Menu.class); // Cambia si quieres ir a otra pantalla
            startActivity(intent);
            finish(); // opcional: cerrar esta Activity
        });

        // pedir permisos
        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
    }

    private void initializeLocationServices() {
        if (!hasLocationPermissions() || locationFound) {
            return;
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager != null) {
            try {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0, 0, this);

                    Location lastKnown = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastKnown != null) {
                        moveToUserLocation(lastKnown);
                    }
                } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0, 0, this);

                    Location lastKnown = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (lastKnown != null) {
                        moveToUserLocation(lastKnown);
                    }
                }
            } catch (SecurityException e) {
                Toast.makeText(this, "Permisos de ubicación requeridos", Toast.LENGTH_LONG).show();
            }
        }

        if (!locationFound) {
            Toast.makeText(this, "Buscando tu ubicación...", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToUserLocation(Location location) {
        if (location != null && mapController != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            GeoPoint userLocation = new GeoPoint(latitude, longitude);

            mapController.animateTo(userLocation);
            mapController.setZoom(18.0);

            if (userMarker != null) {
                map.getOverlays().remove(userMarker);
            }

            userMarker = new Marker(map);
            userMarker.setPosition(userLocation);
            userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            userMarker.setTitle("Tu ubicación actual");
            userMarker.setSnippet("Lat: " + String.format("%.6f", latitude) +
                    "\nLon: " + String.format("%.6f", longitude) +
                    "\nPrecisión: " + String.format("%.0fm", location.getAccuracy()));

            map.getOverlays().add(userMarker);
            map.invalidate();

            locationFound = true;
            if (locationManager != null && hasLocationPermissions()) {
                try {
                    locationManager.removeUpdates(this);
                } catch (SecurityException ignored) {}
            }

            Toast.makeText(this, "¡Ubicación encontrada!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasLocationPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (map != null) {
            map.onResume();
        }
        if (hasLocationPermissions() && !locationFound) {
            initializeLocationServices();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (map != null) {
            map.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null && hasLocationPermissions()) {
            try {
                locationManager.removeUpdates(this);
            } catch (SecurityException ignored) {}
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        moveToUserLocation(location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean hasLocationPermission = false;
        for (int i = 0; i < permissions.length; i++) {
            if ((permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION) ||
                    permissions[i].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) &&
                    grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                hasLocationPermission = true;
                break;
            }
        }

        if (hasLocationPermission) {
            initializeLocationServices();
        } else {
            ArrayList<String> permissionsToRequest = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permissions[i]);
                }
            }

            if (permissionsToRequest.size() > 0) {
                ActivityCompat.requestPermissions(
                        this,
                        permissionsToRequest.toArray(new String[0]),
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } else {
            initializeLocationServices();
        }
    }
}
