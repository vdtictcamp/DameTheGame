package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        btnInitNewGame.setOnClickListener(toGameField);
    }

    public void changeToGamefield(){
        Intent intent = new Intent(this, GameField.class);
        startActivity(intent);
    }

}
