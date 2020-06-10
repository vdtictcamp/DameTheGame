package com.example.myapplication.Firebase;

import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.myapplication.Activities.Transaction;
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


    public FirebaseGameController(int[][] stones, String gameName) {
        this.stones = stones;
        this.gameName = gameName;
        database = FirebaseDatabase.getInstance();
    }


    //4
    public int[][] updateArray(List<List<Integer>> updatedList) {
        for (int i = 0; i < updatedList.size(); i++) {
            for (int j = 0; j < updatedList.get(i).size(); j++) {
                stones[i][j] = updatedList.get(i).get(j);
            }
        }
        return stones;
    }

    //3
    public void updateField(int[][] stones) {
        currentField = new ArrayList<>();
        reference = database.getReference("rooms").child(this.gameName).child("field");

        List<Integer> tempList = new ArrayList<>();
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones[i].length; j++) {
                tempList.add(stones[i][j]);
            }
            currentField.add(tempList);
        }
        reference.setValue(currentField);
    }


    public void initStartSituation(String player) {
        currentField = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();

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
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("stone").child("row");
        reference.setValue(0);
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("stone").child("col");
        reference.setValue(0);
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("position").child("row");
        reference.setValue(0);
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("position").child("col");
        reference.setValue(0);

        /**
         * Würde ein "Field" auf Fierbase erstellen

         reference = database.getReference("rooms").child(gameName).child("field");
         for(int i=0;i<stones.length; i++){
         for(int j=0; j<stones[i].length; j++){
         tempList.add(stones[i][j]);
         }
         currentField.add(tempList);
         }
         reference.setValue(currentField);

         */
    }


    private long addValueEventListenerStoneRow() {
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("stone").child("row");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateIds = (long) dataSnapshot.getValue();
                ids[0] = updateIds;
                System.out.println("UpdateSTone" + updateIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return updateIds;
    }

    private long addValueEventListenerStoneCol() {
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("stone").child("col");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateIds = (long) dataSnapshot.getValue();
                ids[1] = updateIds;
                System.out.println("UpdateSTone" + updateIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return updateIds;
    }


    private long addValueEventListenerPositionCol() {
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("position").child("col");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateIds = (long) dataSnapshot.getValue();
                ids[3] = updateIds;
                System.out.println("UpdateSTone" + updateIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return updateIds;
    }

    private long addValueEventListenerPositionRow() {
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("position").child("row");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateIds = (long) dataSnapshot.getValue();
                ids[2] = updateIds;
                System.out.println("UpdateSTone" + updateIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return updateIds;
    }


    /**
     * reference = database.getReference("test").child("Testevent");
     * DatabaseReference usersRef = reference.child("Testevent");
     * <p>
     * Map<String, Transaction> transactions = new HashMap<>();
     * transactions.put("alanisawesome", new Transaction("1", "2", "3", "4"));
     * <p>
     * usersRef.setValueAsync(transaction);
     **/


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

    private long addValueEventListenerCol() {
        reference = database.getReference("rooms").child(this.gameName).child("updateInformations").child("PositionId");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateIds = (long) dataSnapshot.getValue();
                System.out.println("UpdatePos:" + updateIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return updateIds;

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


    public void setPlayerOneTurn() {
        reference = database.getReference("rooms").child(gameName).child("PlayerOneTurn");
        reference.setValue(true);
    }

    public void setPlayerTwoTurn() {
        reference = database.getReference("rooms").child(gameName).child("PlayerTwoTurn");
        reference.setValue(true);
    }


    public boolean checkIfPlayerTwoHasJoined() {
        boolean hasJoined = false;
        hasJoined = readIfPlayerTwoHasJoined();
        return isHosted;
    }

    public void checkIfIsInTurn() {

    }

    //This Method will write the stones which will be removed to the base
    public void writeStonesToRemove() {

    }
}
