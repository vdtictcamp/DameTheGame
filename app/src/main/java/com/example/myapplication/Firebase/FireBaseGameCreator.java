package com.example.myapplication.Firebase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.Player.Player;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;


public class FireBaseGameCreator {

    FirebaseDatabase database;
    DatabaseReference reference;

    Player host;
    String gameName;

    int[][]stones;
    List<List<Integer>>currentField = new ArrayList<>();

    public FireBaseGameCreator(int[][]stones, Player host, String gameName){
        this.host=host;
        this.gameName=gameName;
        this.stones=stones;
    }

    public void creatRoom(){
        database=FirebaseDatabase.getInstance();
        reference = database.getReference().child("rooms");
        addEvenListener();
    }

    private void addEvenListener(){
        //von datenbank lesen
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //wen erfolg weiterleitung zu firebase2 aktivity, playername wird gespeichert
                if(!gameName.equals("")){
                  reference.setValue(gameName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Fehlermeldung generieren

            }
        });
    }

    public void initStartSituation(){
        List<Integer> tempList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("rooms/" + gameName );
        for(int i=0;i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                tempList.add(stones[i][j]);
            }
            currentField.add(tempList);
        }
        reference.setValue(currentField);
    }

}
