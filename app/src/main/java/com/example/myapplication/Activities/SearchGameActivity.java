package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Firebase.Firebase2;
import com.example.myapplication.Firebase.Firebase3;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchGameActivity extends AppCompatActivity {

    private Button btnJoinGame;
    ListView listView;
    String gameName;
    EditText txtFieldGameName;
    DatabaseReference reference;
    FirebaseDatabase database;

    DatabaseReference roomsRef;
    DatabaseReference roomRef;

    List<String> roomsList;
    String roomName = "";
    String playerName = "";
    boolean isHosted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_game);
        btnJoinGame=findViewById(R.id.btnJoingame);
        database = FirebaseDatabase.getInstance();
        listView = findViewById(R.id.listView);
        isHosted=false;
        roomsList = new ArrayList<>();

        playerName = "test";


        btnJoinGame.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameName = txtFieldGameName.getText().toString().trim();
                System.out.println(gameName);
                reference = database.getReference("rooms").child(gameName).child("PlayerOneHasJoined");
                if(gameName.equals("")){
                    txtFieldGameName.setText("Bitte Gib ein Spielnamen ein");
                }
                else {
                    checkIfGameIsHosted();
                }
            }
        }));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bestehendem room beitreten und sich selbst als player2 hinzufügen
                roomName = roomsList.get(position);
                //roomRef = database.getReference("rooms/" + roomName + "/player2");
                //addRoomEventListener();
                //roomRef.setValue(playerName);
                joinGame(roomName);
            }
        });

        addRoomsEventListener();
    }

    public void joinGame(String gameName){
        Intent intent =new Intent(this, GameField.class);
        intent.putExtra("gameName", gameName);
        intent.putExtra("Player", "PlayerTwo");
        startActivity(intent);
    }

    private void checkIfGameIsHosted(){
        reference = database.getReference("rooms").child(gameName).child("PlayerOneHasJoined");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                isHosted = (boolean) dataSnapshot.getValue();
                if (isHosted){
                    joinGame(gameName);
                }
                else{
                    txtFieldGameName.setText("Dises Spiel ist nicht gehostet");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void addRoomsEventListener(){
        roomsRef = database.getReference("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //rooms liste anzeigen
                roomsList.clear();
                Iterable<DataSnapshot> rooms = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : rooms){
                    roomsList.add(snapshot.getKey());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchGameActivity.this, android.R.layout.simple_list_item_1, roomsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // fehler, nicht ausführen

            }
        });
    }

    private void addRoomEventListener(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //join room
                Intent intent = new Intent(getApplicationContext(), Firebase3.class);
                intent.putExtra("roomName", roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //error
                Toast.makeText(SearchGameActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
