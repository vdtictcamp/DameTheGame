package com.example.myapplication.Firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Firebase2 extends AppCompatActivity {

    ListView listView;
    Button buttonCreateRoom;

    public int[][] stones;
    private List<String> roomsList;

    String playerName = "";
    String roomName = "";

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomsRef;
    DatabaseReference messageRef;
    String message = "";


    public List<List<Integer>>currentField = new ArrayList<>();

    public Firebase2(int[][]stones){
        this.stones=stones;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase2);

        database = FirebaseDatabase.getInstance();

        //player name erhalten und diesem dem room zuweisen
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");
        roomName = playerName;

        System.out.println(playerName + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        listView = findViewById(R.id.listView);
        buttonCreateRoom = findViewById(R.id.btnRoomCreate);

        //alle verfügbare räume
        roomsList = new ArrayList<>();

        stones = new int[][]{{R.id.w1, 0, R.id.w2, 0, R.id.w3, 0, R.id.w4, 0},
                { 0, R.id.w5, 0, R.id.w6, 0, R.id.w7, 0, R.id.w8},
                {R.id.w9, 0, R.id.w10, 0, R.id.w11, 0, R.id.w12, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0,R.id.b9, 0,R.id.b10, 0,R.id.b11, 0,R.id.b12},
                {R.id.b5,0, R.id.b6,0, R.id.b7,0, R.id.b8,0},
                {0,R.id.b1, 0,R.id.b2, 0,R.id.b3, 0,R.id.b4}};


        buttonCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //room erstellen und selbst als player1 hinzufügen
                buttonCreateRoom.setText("CREATING ROOM");
                buttonCreateRoom.setEnabled(false);
                roomName = playerName;
                roomRef = database.getReference("rooms/" + roomName + "/player1");
                addRoomEventListener();
                roomRef.setValue(playerName);
                setFireField();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //bestehendem room beitreten und sich selbst als player2 hinzufügen
                roomName = roomsList.get(position);
                roomRef = database.getReference("rooms/" + roomName + "/player2");
                addRoomEventListener();
                roomRef.setValue(playerName);
            }
        });

        //neue Räume anzeigen
        addRoomsEventListener();
    }

    private void addRoomEventListener(){
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //join room
                buttonCreateRoom.setText("CREATE ROOM");
                buttonCreateRoom.setEnabled(true);
                Intent intent = new Intent(getApplicationContext(), Firebase3.class);
                intent.putExtra("roomName", roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //error
                buttonCreateRoom.setText("CREATE ROOM");
                buttonCreateRoom.setEnabled(true);
                Toast.makeText(Firebase2.this, "Error", Toast.LENGTH_SHORT).show();

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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Firebase2.this, android.R.layout.simple_list_item_1, roomsList);
                    listView.setAdapter(adapter);
                }

                currentField = (List<List<Integer>>) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // fehler, nicht ausführen

            }
        });
    }

    public void setFireField(){
        List<Integer>tempList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        messageRef = database.getReference("rooms/" + roomName );
        for(int i=0;i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                tempList.add(stones[i][j]);
            }
            currentField.add(tempList);
        }

        messageRef.setValue(currentField);

    }


    public List<List<Integer>> readDataFromBase(){
        return currentField;
    }

}
