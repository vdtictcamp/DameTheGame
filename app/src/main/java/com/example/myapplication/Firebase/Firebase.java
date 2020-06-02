package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase extends AppCompatActivity {

    EditText editText;
    Button button;

    String playerName = "";

    FirebaseDatabase database;
    DatabaseReference playerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        playerName = "";

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.btnLogin);

        database = FirebaseDatabase.getInstance();

        //prüfen ob spieler existiert und referenz setzen
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");
        if(!playerName.equals("")){
            playerRef = database.getReference("players/"+ playerName);
            addEvenListener();
            playerRef.setValue("");

        }
        button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spieler einloggen
                playerName = editText.getText().toString();
                editText.setText("");
                if(!playerName.equals("")){
                    button.setText("Loggin in");
                    button.setEnabled(false);
                    playerRef = database.getReference("players/"+ playerName);
                    addEvenListener();
                    playerRef.setValue("");
                }
            }
        }));
    }

    private void addEvenListener(){
        //von datenbank lesen
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //wen erfolg weiterleitung zu firebase2 aktivity, playername wird gespeichert
                if(!playerName.equals("")){
                    SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("playerName", playerName);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), Firebase2.class));
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //fehler
                button.setText("Log in");
                button.setEnabled(true);
                Toast.makeText(Firebase.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerName = "";
    }
}
