package com.example.myapplication.Threads;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Activities.Transaction;
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
        gameController = new FirebaseGameController(stones, this.gameName);
        this.game = (GameField) context;
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

        Looper.prepare();
        while (!gameOver) {
            inTurn = gameController.readTurnOfPlayerTwo();
            if (!isInTurn) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HashMap<String, Integer> ids = gameController.addValueEventListenerAllValues();
                if (ids != null) {
                    System.out.println(ids.keySet());
                    System.out.println("TRUEEEEEE");
                    System.out.println(ids);
                    gameController.setDefaultUpdateValues();
                    if (ids != null) {
                        inTurn = true;
                        System.out.println("TRUEEEEEE");
                        System.out.println(ids);
                        gameController.setDefaultUpdateValues();
                        long rowPos = Long.parseLong(String.valueOf(ids.get("rowPos")));
                        long colPos = Long.parseLong(String.valueOf(ids.get("colPos")));
                        long colStone = Long.parseLong(String.valueOf(ids.get("colStone")));
                        long rowStone = Long.parseLong(String.valueOf(ids.get("rowStone")));
                        if (rowPos != 0 && colPos != 0 && colPos != 0 && colStone != 0) {
                            isInTurn = true;
                            boolean ready = gameController.setDefaultUpdateValues();
                            if(ready) {
                                game.moveHelperFunc((int) colStone, (int) rowStone, (int) rowPos, (int) colPos);
                            }
                        }
                    }

                }
            }
        }
    }
}

