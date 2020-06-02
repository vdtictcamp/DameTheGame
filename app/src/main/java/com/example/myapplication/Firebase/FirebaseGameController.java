package com.example.myapplication.Firebase;

import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.myapplication.GameEngine.GameController;
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

public class FirebaseGameController {

    int[][]stones;
    FirebaseDatabase database;
    List<List<Integer>>currentField;
    HashMap<String, Integer> field;
    HashMap<String, Integer>updateIds;
    private List<String> roomsList;
    Firebase2 firebase2;
    DatabaseReference reference, reference1, reference2;
    GameController gameController;
    String gameName;



    public FirebaseGameController(int[][]stones, String gameName){
        this.stones=stones;
        this.gameName = gameName;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("rooms/"+this.gameName);


    }


    //4
    public int[][] updateArray(List<List<Integer>>updatedList){
        for(int i=0; i<updatedList.size(); i++){
            for(int j=0; j<updatedList.get(i).size(); j++){
                stones[i][j]=updatedList.get(i).get(j);
            }
        }
        return stones;
    }

    //3
    public void updateField(int[][]stones){
       currentField = new ArrayList<>();
        reference = database.getReference("rooms/" + gameName );

        List<Integer> tempList = new ArrayList<>();
        for(int i=0;i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                tempList.add(stones[i][j]);
            }
            currentField.add(tempList);
        }
        reference.setValue(currentField);
       addRoomsEventListener();
    }


    public void initStartSituation(){
        currentField = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();
        reference = database.getReference("rooms/" + gameName+"/"+"field" );
        for(int i=0;i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                tempList.add(stones[i][j]);
            }
            currentField.add(tempList);
        }
        reference.setValue(currentField);
    }


    private void addValueEventListener(){
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateIds = (HashMap<String, Integer>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addRoomsEventListener(){
        reference = database.getReference("rooms").child(gameName).child("field");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                field = (HashMap<String, Integer>) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // fehler, nicht ausführen

            }
        });
    }

    //1
    //This Method will send the Turn code and the stone which has moved to his last position
    public boolean sendUpdateInformaions(int stoneId, int positionId){
        reference = database.getReference("rooms").child(gameName).child("field").child("0").child("updateInformations").child("StoneID");
        reference.setValue(stoneId);
        reference = database.getReference("rooms").child(gameName).child("field").child("0").child("updateInformations").child("PositionID");
        reference.setValue(positionId);
        return true;
    }

    //2
    public boolean readStoneIdPositionId(){
        if(field!=null) {
            return true;
        }else{
            return false;
        }

    }

    public HashMap<String, Integer> returnIds(){
        return field;
    }
    //This Method will write the stones which will be removed to the base
    public void writeStonesToRemove(){

    }


}
