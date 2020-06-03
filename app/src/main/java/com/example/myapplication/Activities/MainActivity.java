package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    Button btnInitNewGame;

    private Button btnGetGps;
    private TextView txtAusgabe;

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

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

