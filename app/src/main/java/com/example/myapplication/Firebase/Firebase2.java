package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureGroupInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
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
    public Field updatedField;

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

                updatedField = dataSnapshot.getValue(Field.class);
                updateArray(updatedField.currentField);
                setFireField();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // fehler, nicht ausführen

            }
        });
    }




    public  static class Field{
        public int field00, field01, field02, field03, field04, field05, field06, field07;
        public int field10, field11, field12, field13, field14, field15, field16, field17;
        public int field20, field21, field22, field23, field24, field25, field26, field27;
        public int field30, field31, field32, field33, field34, field35, field36, field37;
        public int field40, field41, field42, field43, field44, field45, field46, field47;
        public int field50, field51, field52, field53, field54, field55, field56, field57;
        public int field60, field61, field62, field63, field64, field65, field66, field67;
        public int field70, field71, field72, field73, field74, field75, field76, field77;
        private List<List<Integer>>currentField = new ArrayList<>();

        public Field(int i00, int i01,int i02,int i03,int i04,int i05,int i06,int i07,int i10,int i11,int i12,int i13,int i14 ){
            field00 = i00; field01 =i01; field02 = i02; field03=i03; field04=i04; field05=i05; field06=i06; field07=i07;
            field10=i10; field11=i11; field12=i12; field13=i13; field14=i14; //field15=s15; field16=s16; field17=s17;
            //idStein01 = idb1; idStein02 = idb2;
        }

        public Field(List<List<Integer>>currentField){
            this.currentField=currentField;
        }

    }

    public void setFireField(){
        List<Integer>tempList = new ArrayList<>();
        messageRef = database.getReference("rooms/" + roomName );

        Map<String, Field> field = new HashMap<>();
        field.put("playField " + playerName, new Field(stones[0][0], stones[0][1], stones[0][2],stones[0][3],stones[0][4],stones[0][5], stones[0][6], stones[0][7], stones[1][0], stones[1][1], stones[1][2], stones[1][3], stones[1][4] ));

        for(int i=0;i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                tempList.add(stones[i][j]);
            }
            currentField.add(tempList);
        }
        messageRef.setValue(currentField);

        //message = "0";
        //messageRef.setValue(message);

    }


    public void readDataFromBase(){

    }

    public void updateArray(List<List<Integer>>updatedList){
        for(int i=0; i<updatedList.size(); i++){
            for(int j=0; j<updatedList.get(i).size(); j++){
                stones[i][j]=updatedList.get(i).get(j);
            }
        }
    }
}
