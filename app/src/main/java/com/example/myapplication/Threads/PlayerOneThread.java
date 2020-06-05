package com.example.myapplication.Threads;

import android.content.Context;
import android.os.Looper;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Activities.Transaction;
import com.example.myapplication.Firebase.FirebaseGameController;

import java.util.HashMap;
import java.util.List;

public class PlayerOneThread extends Thread implements Runnable {

    GameField game;
    PlayerTwoThread pTwoThread;
    private boolean isInTurn=true;
    int[][] stones;
    int counter =15;
    FirebaseGameController gameController;
    String gameName;
    boolean playerTwohasJoined = false;
    FirebaseGameController dataBaseController;
    private boolean gameOver=false;
    private boolean inTurn = true;

    public PlayerOneThread(int[][]stones, String gameName, Context context) {
        this.stones = stones;
        this.gameName=gameName;
        gameController = new FirebaseGameController(stones,this.gameName );
        dataBaseController=new FirebaseGameController(stones, gameName);
        this.game= (GameField) context;
    }

    public boolean isInTurn() {
        isInTurn = true;
        return isInTurn;
    }

    public boolean finishTurn() {
        isInTurn = false;
        return isInTurn;
    }


    @Override
    public void run() {

        dataBaseController.initStartSituationBeta(new Transaction(0,0,0,0), "PlayerOne");
        dataBaseController.setPlayers("PlayerOne");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Looper.prepare();
        while (!playerTwohasJoined){
            playerTwohasJoined=dataBaseController.checkIfPlayerTwoHasJoined();
            System.out.println("Warte bis Spieler zwei beitritt");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Spieler zwei ist beigetreten");
        game.connectionSuccessfull();
            inTurn = gameController.readTurnOfPlayerOne();
            if (!inTurn) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Zug Spieler eins beendet");
                HashMap<String, Transaction> ids = gameController.addValueEventListenerAllValues();
                System.out.println(ids);
                Transaction values = ids.get("updateInformations");
                int rowPos = values.getRowPos();
                int colPos = values.getColPos();
                int colStone = values.getColStone();
                int rowStone = values.getRowStone();
                if(rowPos!=0 && colPos!=0 && colPos!=0 &&colStone!=0){
                    game.moveHelperFunc(colStone, rowStone, rowPos, colPos);
                }
            }

        }
    }

