package com.example.myapplication.Firebase;

import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.myapplication.Player.Player;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseClass {

    int[][]stones;
    FirebaseDatabase database;
    DatabaseReference roomsRef;
    DatabaseReference messageRef;
    List<List<Integer>>currentField;
    private List<String> roomsList;
    Firebase2 firebase2;



    public FirebaseClass(int[][]stones){
        this.stones=stones;
        database = FirebaseDatabase.getInstance();
        firebase2 = new Firebase2(stones);
    }

    public void addNewUserToBase(String name, String password){
        Player newPlayer = new Player(name, password);
    }

    public void writeDataInBase(){
        firebase2.setFireField();

    }

    public void readDataFromBase(){
            currentField=firebase2.readDataFromBase();
            updateArray(currentField);
    }

    public void updateArray(List<List<Integer>>updatedList){
        for(int i=0; i<updatedList.size(); i++){
            for(int j=0; j<updatedList.get(i).size(); j++){
                stones[i][j]=updatedList.get(i).get(j);
            }
        }
    }





}
