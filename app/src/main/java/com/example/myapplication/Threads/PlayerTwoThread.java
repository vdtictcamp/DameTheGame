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
        while (!gameOver){
            if(!isInTurn){
                boolean receivedUpdate =gameController.readStoneIdPositionId();
                if(receivedUpdate){
                   System.out.println("TRUEEEEEE");
                    HashMap<String, Integer> ids = gameController.returnIds();
                    int posID = ids.get("PositionID");
                    int stoneId = ids.get("StoneID");
                    //Nachdem die Steine bewegt wurden, muss der aktuelle Spielstatus ausgelesen werden
                    System.out.println("Aus Thread"+posID + stoneId);
                    System.out.println("Aus player zwei Thread:"+ids);
                    stoneIdFromBase = gameController.returnIds();
                    isInTurn= isInTurn();
                    game.setTurn();

                    //Now we need to move the Stone
                    //Then we finish the Turn of player one
                    //And set the Turn for player two
                }
            }
    }

}


}
