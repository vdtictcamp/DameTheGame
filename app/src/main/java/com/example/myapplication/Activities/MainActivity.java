package com.example.myapplication.Activities;

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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    Button btnInitNewGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInitNewGame = findViewById(R.id.btnInitnewGame);


        View.OnClickListener toGameField = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeToGamefield();
            }
        };
        btnInitNewGame.setOnClickListener(toGameField);}

    public void changeToGamefield(){
        Intent intent = new Intent(this, GameField.class);
        startActivity(intent);
    }

}

