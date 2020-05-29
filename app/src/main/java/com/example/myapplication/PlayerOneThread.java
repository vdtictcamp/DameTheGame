package com.example.myapplication;

import com.example.myapplication.Activities.GameField;

public class PlayerOneThread extends Thread implements Runnable {

    GameField game = new GameField();
    PlayerTwoThread pTwoThread;
    boolean isInTurn;
    int[][] stones;

    public PlayerOneThread(int[][] stones) {
        this.stones = stones;
        pTwoThread = new PlayerTwoThread(stones);
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
