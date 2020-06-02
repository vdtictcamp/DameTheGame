package com.example.myapplication.Threads;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Firebase.FirebaseGameController;

public class PlayerOneThread extends Thread implements Runnable {

    GameField game = new GameField();
    PlayerTwoThread pTwoThread;
    boolean isInTurn;
    int[][] stones;
    int counter =15;
    FirebaseGameController gameController;
    String gameName;

    public PlayerOneThread(int[][]stones, String gameName) {
        this.stones = stones;
        this.gameName=gameName;
        pTwoThread = new PlayerTwoThread(stones, gameName);
        gameController = new FirebaseGameController(stones,this.gameName );
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

        while (true) {
            if (isInTurn) {
                game.update();
                finishTurn();
                pTwoThread.isInTurn();
            }


        }
    }
}
