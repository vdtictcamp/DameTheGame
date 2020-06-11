package com.example.myapplication.Threads;

import android.content.Context;
import android.os.Looper;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Firebase.FirebaseGameController;
import com.example.myapplication.GameEngine.GameController;

import java.util.HashMap;

public class PlayerTwoThread extends Thread implements Runnable {

    GameController controller;
    PlayerOneThread pOneThread;
    public boolean gameOver = false;
    PlayerOneThread pTwoThread;
    boolean isInTurn = false;
    int[][] stones;
    int counter = 15;
    FirebaseGameController gameController;
    String gameName;
    HashMap<String, Integer> stoneIdFromBase;
    int[][] positionIds;
    GameField game;
    private boolean inTurn = false;

    public PlayerTwoThread(int[][] stones, String gameName, Context context) {
        this.stones = stones;
        this.gameName = gameName;
        gameController = new FirebaseGameController( this.gameName);
        this.game = (GameField) context;
    }

    @Override
    public void run() {

        Looper.prepare();
        while (!gameOver) {
            isInTurn = gameController.readTurnOfPlayerTwo();
            if (!isInTurn) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HashMap<String, Integer> ids = gameController.addValueEventListenerAllValues();
                if (ids != null) {
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
                                gameController.finishPlayerOneTurn();
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                isInTurn=true;
                                game.moveHelperFunc((int) colStone, (int) rowStone, (int) rowPos, (int) colPos);
                            }
                        }
                    }

                }
            }
        }
    }


