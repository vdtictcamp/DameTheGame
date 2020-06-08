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

    public void finishTurn() {
        isInTurn = false;
    }


    @Override
    public void run() {
        Looper.prepare();
        while (!playerTwohasJoined) {
            playerTwohasJoined = dataBaseController.checkIfPlayerTwoHasJoined();
            System.out.println("Warte bis Spieler zwei beitritt");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Spieler zwei ist beigetreten");
        game.connectionSuccessfull();
        while (!gameOver) {
            isInTurn = gameController.readTurnOfPlayerOne();
            if (!isInTurn) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HashMap<String, Integer> ids = gameController.addValueEventListenerAllValues();
                System.out.println(ids);
                if (ids != null) {
                    System.out.println("TRUEEEEEE");
                    System.out.println(ids);
                    long rowPos = Long.parseLong(String.valueOf(ids.get("rowPos")));
                    long colPos = Long.parseLong(String.valueOf(ids.get("colPos")));
                    long colStone = Long.parseLong(String.valueOf(ids.get("colStone")));
                    long rowStone = Long.parseLong(String.valueOf(ids.get("rowStone")));
                    if (rowPos != 0 && colPos != 0 && colPos != 0 && colStone != 0) {
                        ids=null;
                        boolean ready = gameController.setDefaultUpdateValues();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(ready) {
                            isInTurn = true;
                            gameController.finishPlayerTwoTurn();
                            game.moveHelperFunc((int) colStone, (int) rowStone, (int) rowPos, (int) colPos);
                        }
                    }

                }
            }

        }
    }
    }

