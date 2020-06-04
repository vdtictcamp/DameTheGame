package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchGameActivity extends AppCompatActivity {

    private Button btnJoinGame;
    String gameName;
    EditText txtFieldGameName;
    DatabaseReference reference;
    FirebaseDatabase database;
    TextView txtGameNotFound;
    boolean isHosted = false;


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
                reference = database.getReference("rooms").child(gameName).child("PlayerOneHasJoined");
                if(gameName.equals("")){
                    txtFieldGameName.setText("Bitte Gib ein Spielnamen ein");
                }
                else {
                        isHosted= checkIfGameIsHosted();
                        if(isHosted) {
                            joinGame(gameName);
                        }else{
                            txtFieldGameName.setText("Dieses Spiel ist nicht gehostet");
                        }
                }
            }
        };

        btnJoinGame.setOnClickListener(joinGameListener);

    }
    public void joinGame(String gameName){
        Intent intent =new Intent(this, GameField.class);
        intent.putExtra("gameName", gameName);
        intent.putExtra("Player", "PlayerTwo");
        startActivity(intent);
    }


    private boolean checkIfGameIsHosted(){
         isHosted=false;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isHosted = (boolean) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return isHosted;
    }
}
