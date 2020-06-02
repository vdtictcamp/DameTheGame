package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Firebase.Firebase;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchGameActivity extends AppCompatActivity {

    private Button btnJoinGame;
    String gameName;
    EditText txtFieldGameName;
    DatabaseReference reference;
    FirebaseDatabase database;
    TextView txtGameNotFound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);
        btnJoinGame=findViewById(R.id.btnJoingame);
        txtFieldGameName = findViewById(R.id.txtFieldGameName);
        database = FirebaseDatabase.getInstance();
        txtGameNotFound=findViewById(R.id.gameNotFoundLbl);

        View.OnClickListener joinGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameName = txtFieldGameName.getText().toString().trim();
                reference = database.getReference().child("rooms/"+gameName);
                if(gameName.equals("")){
                    txtFieldGameName.setText("Bitte Gib ein Spielnamen ein");
                }
                if(reference==null){
                    txtGameNotFound.setText("kein Spiel gefunden, versuche es später nochmal");
                }else {
                    joinGame(gameName);
                }
            }
        };

        btnJoinGame.setOnClickListener(joinGameListener);

    }
    public void joinGame(String gameName){
        Intent intent =new Intent(this, GameFieldPlayerTwo.class);
        intent.putExtra("GameName", gameName);
        startActivity(intent);
    }
}
