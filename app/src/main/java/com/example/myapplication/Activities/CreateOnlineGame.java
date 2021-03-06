package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class CreateOnlineGame extends AppCompatActivity {

    private String gameName;
    private EditText txtFieldGameName;
    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_online_game);
        btnStartGame=findViewById(R.id.btnCreateOnlineGame);
        txtFieldGameName=findViewById(R.id.nameOnlineGame);
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
        if(!gameName.equals("")) {
            Intent intent = new Intent(getApplicationContext(), GameField.class);
            intent.putExtra("gameName", gameName);
            intent.putExtra("Player", "PlayerOne");
            startActivity(intent);
        }else{
            Toast.makeText(CreateOnlineGame.this, "Bitte lege einen Namen für das Spiel fest", Toast.LENGTH_LONG).show();
        }
    }
}