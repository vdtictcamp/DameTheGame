package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

import java.io.BufferedReader;

public class GameSettings extends AppCompatActivity {


    String gameName;
    EditText txtFieldGameName;
    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);
        txtFieldGameName = findViewById(R.id.txtGameName);
        btnStartGame = findViewById(R.id.btnStartGame);

        View.OnClickListener startGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        };

        btnStartGame.setOnClickListener(startGameListener);
    }

    public void startGame(){
        gameName = txtFieldGameName.getText().toString().trim();
        Intent intent = new Intent(getApplicationContext(), GameField.class);
        intent.putExtra("gameName", gameName);
        startActivity(intent);
    }

}
