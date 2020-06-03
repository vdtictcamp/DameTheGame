package com.example.myapplication.Threads;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Activities.GameFieldPlayerTwo;
import com.example.myapplication.Firebase.FirebaseGameController;
import com.example.myapplication.GameEngine.GameController;
import com.example.myapplication.Threads.PlayerOneThread;

import java.util.HashMap;
import java.util.List;

public class PlayerTwoThread extends Thread implements Runnable {

    GameFieldPlayerTwo game = new GameFieldPlayerTwo();
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



    public PlayerTwoThread(int[][]stones, String gameName) {
        this.stones = stones;
        this.gameName=gameName;
        gameController = new FirebaseGameController(stones,this.gameName );
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
            if(!isInTurn){
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long[] ids =gameController.readStoneIdPositionId();
                if(ids!=null){
                   System.out.println("TRUEEEEEE");
                   int s_row = Integer.parseInt(String.valueOf(ids[0]));
                   int s_col = Integer.parseInt(String.valueOf(ids[1]));
                   System.out.println(".....................");
                   System.out.println("StoneID"+ids[0]);
                   System.out.println("....................");
                    System.out.println("PositionID"+ids[1]);
                    System.out.println(ids[2]);
                    System.out.println(ids[3]);
                   int p_row = Integer.parseInt(String.valueOf(ids[2]));
                   int p_col = Integer.parseInt(String.valueOf(ids[3]));
                    isInTurn= isInTurn();
                   game.helpViewMover(s_row, s_col, p_row, p_col);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   game.setTurn();
                    //Nachdem die Steine bewegt wurden, muss der aktuelle Spielstatus ausgelesen werden

                    //Now we need to move the Stone
                    //Then we finish the Turn of player one
                    //And set the Turn for player two
                }

            }
    }

}

