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
        inTurn = false;
        Looper.prepare();
        inTurn = gameController.readTurnOfPlayerTwo();
        if (!inTurn) {
            HashMap<String, Transaction> ids = gameController.addValueEventListenerAllValues();
            if (ids != null) {
                inTurn = true;
                System.out.println("TRUEEEEEE");
                System.out.println(ids);
                Transaction values = ids.get("updateInformations");
                int rowPos = values.getRowPos();
                int colPos = values.getColPos();
                int colStone = values.getColStone();
                int rowStone = values.getRowStone();
                game.moveHelperFunc(colStone, rowStone, rowPos, colPos);
            }
        }
    }
}

