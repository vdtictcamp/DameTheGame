package com.example.myapplication.Threads;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Firebase.FirebaseGameController;
import com.example.myapplication.GameEngine.GameController;

import java.util.HashMap;

public class PlayerTwoThread extends Thread implements Runnable {

    GameController controller;
    PlayerOneThread pOneThread;
    public boolean gameOver= false;
    PlayerOneThread pTwoThread;
    boolean isInTurn=false;
    int[][] stones;
    int counter =15;
    FirebaseGameController gameController;
    String gameName;
    HashMap<String, Integer> stoneIdFromBase;
    int[][]positionIds;
    GameField game;
   private boolean inTurn = false;

    public PlayerTwoThread(int[][]stones, String gameName, Context context) {
        this.stones = stones;
        this.gameName=gameName;
        gameController = new FirebaseGameController(stones,this.gameName );
        this.game= (GameField) context;
    }

    public boolean isInTurn(){
        isInTurn = true;
        return isInTurn;
    }

    public boolean finishTurn(){
        isInTurn = false;
        return isInTurn;
    }

    @Override
    public void run() {
        boolean inTurn = false;
        //Sobald die update Methode true zurückgibt,
        // ist der Zug abgeschlossen und der andere Spieler ist am Zug

        while (!gameOver){
            inTurn = gameController.readTurnOfPlayerTwo();
            if(!inTurn) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long[] ids = gameController.readStoneIdPositionId();
            if (ids != null && ids[0]!=0) {
                System.out.println("TRUEEEEEE");
                int s_row = Integer.parseInt(String.valueOf(ids[0]));
                int s_col = Integer.parseInt(String.valueOf(ids[1]));
                int p_row = Integer.parseInt(String.valueOf(ids[2]));
                int p_col = Integer.parseInt(String.valueOf(ids[3]));
                inTurn=true;
                gameController.setDefaultUpdateValues();
                game.moveHelperFunc(s_col, s_row, p_row,p_col);
                System.out.println(".....................");
                System.out.println("StoneID" + ids[0]);
                System.out.println("....................");
                System.out.println("PositionID" + ids[1]);
                System.out.println(ids[2]);
                System.out.println(ids[3]);
                System.out.println("Stones aus Thread:"+ stones[s_row][s_col]);

                }
            }
        }
    }
}

