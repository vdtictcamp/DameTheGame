package com.example.myapplication.Service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationService extends AppCompatActivity {

    Button btnInitNewGame;

    private Button btnGetGps;
    private TextView txtAusgabe;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getgps);
        btnInitNewGame = findViewById(R.id.btnInitnewGame);



        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetGps = findViewById(R.id.btnGetGps);
        txtAusgabe = findViewById(R.id.txtPostition);

        btnGetGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LocationService.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    getCurrentLocation();
                }
            }
        });

    }

    private void getCurrentLocation(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);

        //hier gibt es verschiedene genauigkeitsstufen, welche sich unterschiedlich auf die Batterie auswirken
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(LocationService.this).requestLocationUpdates(locationRequest, new LocationCallback(){

            @Override
            public void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(LocationService.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude =
                            locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude =
                            locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    String provider = locationResult.getLocations().get(latestLocationIndex).getProvider();
                    txtAusgabe.setText(String.format(
                            "Breitengrad: %s\nLängengrad: %S\nProvider: %S", latitude, longitude, provider));
                }
            }
        }, Looper.getMainLooper());
    }

}
