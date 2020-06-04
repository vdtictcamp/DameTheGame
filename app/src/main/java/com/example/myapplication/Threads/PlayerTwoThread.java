package com.example.myapplication.Threads;

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



    public PlayerTwoThread(int[][]stones, String gameName) {
        this.stones = stones;
        this.gameName=gameName;
        gameController = new FirebaseGameController(stones,this.gameName );
        game=new GameField();
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
        //Sobald die update Methode true zurückgibt,
        // ist der Zug abgeschlossen und der andere Spieler ist am Zug
        while (!gameOver){
        if(!isInTurn) {
            long[] ids = gameController.readStoneIdPositionId();
            if (ids != null) {
                System.out.println("TRUEEEEEE");
                int s_row = Integer.parseInt(String.valueOf(ids[0]));
                int s_col = Integer.parseInt(String.valueOf(ids[1]));
                System.out.println(".....................");
                System.out.println("StoneID" + ids[0]);
                System.out.println("....................");
                System.out.println("PositionID" + ids[1]);
                System.out.println(ids[2]);
                System.out.println(ids[3]);
                System.out.println("Stones aus Thread:"+ stones[s_row][s_col]);
                int p_row = Integer.parseInt(String.valueOf(ids[2]));
                int p_col = Integer.parseInt(String.valueOf(ids[3]));
                isInTurn = isInTurn();
                game.moveHelperFunc(s_col, s_row, p_row,p_col);
                //game.helpViewMover(stones[s_col][s_row], positionIds[p_row][p_col]);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isInTurn=true;
                //Nachdem die Steine bewegt wurden, muss der aktuelle Spielstatus ausgelesen werden

                //Now we need to move the Stone
                //Then we finish the Turn of player one
                //And set the Turn for player two
            }
        }

            }
    }

}

