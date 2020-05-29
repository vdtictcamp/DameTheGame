package com.example.myapplication;

import com.example.myapplication.Activities.GameField;

public class PlayerTwoThread extends Thread implements Runnable {

    PlayerOneThread pOneThread;

    boolean isInTurn;
    int[][]stones;
    GameField game = new GameField();

    public PlayerTwoThread(int[][]stones){
        this.stones = stones;
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
        while (true){
            if(isInTurn){
                game.update();
            }



    }
}
}
