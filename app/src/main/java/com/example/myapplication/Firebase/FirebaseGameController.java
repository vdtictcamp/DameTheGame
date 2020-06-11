package com.example.myapplication.Firebase;

import androidx.annotation.NonNull;

import com.example.myapplication.GameEngine.GameController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class FirebaseGameController {

    int[][] stones;
    FirebaseDatabase database;
    List<List<Integer>> currentField;
    private List<Integer> field;
    long updateIds = 0;
    private List<String> roomsList;
    DatabaseReference reference, reference1, reference2;
    GameController gameController;
    String gameName;
    long[] ids = new long[4];
    private boolean isHosted = false;
    private boolean turn = false;
    private HashMap<String, Integer> updateValues;
    private HashMap<String, Integer> values;


    public FirebaseGameController( String gameName) {
        this.gameName = gameName;
        database = FirebaseDatabase.getInstance();
    }

    public void initStartSituationBeta(int rowStone, int colStone, int rowPos, int colPos, String player) {

        if (player.equals("PlayerOne")) {
            reference = database.getReference("rooms").child(gameName).child("PlayerOneHasJoined");
            reference.setValue(true);
            reference = database.getReference("rooms").child(gameName).child("PlayerTwoHasJoined");
            reference.setValue(false);
        }

        if (player.equals("PlayerTwo")) {
            reference = database.getReference("rooms").child(gameName).child("PlayerTwoHasJoined");
            reference.setValue(true);
        }
        reference = database.getReference("rooms").child(gameName).child("PlayerOneTurn");
        reference.setValue(true);
        reference = database.getReference("rooms").child(gameName).child("PlayerTwoTurn");
        reference.setValue(false);

        HashMap<String, Integer> values = new HashMap<>();
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations");
        values.put("rowPos", 0);
        values.put("colPos", 0);
        values.put("rowStone", 0);
        values.put("colStone", 0);
        reference.setValue(values);
    }

    public void updateValuesBeta(int rowStone, int colStone, int rowPos, int colPos) {
        HashMap<String, Integer> values = new HashMap<>();
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations");
        values.put("rowPos", rowPos);
        values.put("colPos", colPos);
        values.put("rowStone", rowStone);
        values.put("colStone", colStone);
        reference.setValue(values);
    }


    public HashMap<String, Integer> addValueEventListenerAllValues() {
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                values = (HashMap<String, Integer>) dataSnapshot.getValue();
                System.out.println("UpdateSTone" + values);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return values;
    }


    public boolean setDefaultUpdateValues() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("rowPos", 0);
        values.put("colPos", 0);
        values.put("rowStone", 0);
        values.put("colStone", 0);
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations");
        reference.setValue(values);
        return true;
    }

    private boolean readIfPlayerTwoHasJoined() {
        reference = database.getReference("rooms").child(gameName).child("PlayerTwoHasJoined");
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

    public boolean readTurnOfPlayerOne() {
        reference = database.getReference("rooms").child(gameName).child("PlayerOneTurn");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                turn = (boolean) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return turn;
    }

    public boolean readTurnOfPlayerTwo() {
        reference = database.getReference("rooms").child(gameName).child("PlayerTwoTurn");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                turn = (boolean) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return turn;
    }

    public void finishPlayerOneTurn() {
        reference = database.getReference("rooms").child(gameName).child("PlayerOneTurn");
        reference.setValue(false);
        reference = database.getReference("rooms").child(gameName).child("PlayerTwoTurn");
        reference.setValue(true);
    }

    public void finishPlayerTwoTurn() {
        reference = database.getReference("rooms").child(gameName).child("PlayerTwoTurn");
        reference.setValue(false);
        reference = database.getReference("rooms").child(gameName).child("PlayerOneTurn");
        reference.setValue(true);
    }

    public boolean checkIfPlayerTwoHasJoined() {
        boolean hasJoined = false;
        hasJoined = readIfPlayerTwoHasJoined();
        return hasJoined;
    }

    //This Method will write the stones which will be removed to the base
    public void writeStonesToRemove(int row, int col) {
            HashMap<String, Integer> values = new HashMap<>();
            reference = database.getReference("rooms").child(this.gameName).child("StoneToRemove");
            values.put("rowStone", row);
            values.put("colStone", col);
            reference.setValue(values);
        }


        public void writeStonesToRemoveToNull(){
            HashMap<String, Integer> values = new HashMap<>();
            reference = database.getReference("rooms").child(this.gameName).child("StoneToRemove");
            values.put("rowStone", 0);
            values.put("colStone", 0);
            reference.setValue(values);
        }

        public HashMap<String, Integer> readStoneToRemove(){
            reference = database.getReference("rooms").child(this.gameName).child("StoneToRemove");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    values = (HashMap<String, Integer>) dataSnapshot.getValue();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return values;
        }
}
