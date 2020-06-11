package com.example.myapplication.Threads;

import android.content.Context;
import android.os.Looper;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Firebase.FirebaseGameController;

import java.util.HashMap;
import java.util.List;

public class PlayerOneThread extends Thread implements Runnable {

    private GameField game;
    private boolean isInTurn=true;
    private int[][] stones;
    private String gameName;
    private boolean playerTwohasJoined = false;
    private FirebaseGameController dataBaseController;
    private boolean gameOver=false;
    private boolean inTurn = true;

    public PlayerOneThread(int[][]stones, String gameName, Context context) {
        this.stones = stones;
        this.gameName=gameName;
        dataBaseController=new FirebaseGameController(gameName);
        this.game= (GameField) context;
    }


    @Override
    public void run() {
        Looper.prepare();
        while (!playerTwohasJoined) {
            playerTwohasJoined = dataBaseController.checkIfPlayerTwoHasJoined();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        game.connectionSuccessfull();
        while (!gameOver) {
            isInTurn = dataBaseController.readTurnOfPlayerOne();
            if (!isInTurn) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HashMap<String, Integer> ids = dataBaseController.addValueEventListenerAllValues();
                System.out.println(ids);
                if (ids != null) {
                    long rowPos = Long.parseLong(String.valueOf(ids.get("rowPos")));
                    long colPos = Long.parseLong(String.valueOf(ids.get("colPos")));
                    long colStone = Long.parseLong(String.valueOf(ids.get("colStone")));
                    long rowStone = Long.parseLong(String.valueOf(ids.get("rowStone")));
                    if (rowPos != 0 && colPos != 0 && colPos != 0 && colStone != 0) {
                        ids=null;
                        boolean ready = dataBaseController.setDefaultUpdateValues();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(ready) {
                            isInTurn = true;
                            dataBaseController.finishPlayerTwoTurn();
                            game.moveHelperFunc((int) colStone, (int) rowStone, (int) rowPos, (int) colPos);
                        }
                    }

                }
            }

        }
    }
    }

