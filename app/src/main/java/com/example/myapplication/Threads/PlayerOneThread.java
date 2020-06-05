package com.example.myapplication.Threads;

import android.content.Context;
import android.os.Looper;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Firebase.FirebaseGameController;

public class PlayerOneThread extends Thread implements Runnable {

    GameField game;
    PlayerTwoThread pTwoThread;
    private boolean isInTurn=true;
    int[][] stones;
    int counter =15;
    FirebaseGameController gameController;
    String gameName;
    boolean playerTwohasJoined = false;
    FirebaseGameController dataBaseController;
    private boolean gameOver=false;
    private boolean inTurn = true;

    public PlayerOneThread(int[][]stones, String gameName, Context context) {
        this.stones = stones;
        this.gameName=gameName;
        gameController = new FirebaseGameController(stones,this.gameName );
        dataBaseController=new FirebaseGameController(stones, gameName);
        this.game= (GameField) context;
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
        while (!playerTwohasJoined){
            playerTwohasJoined=dataBaseController.checkIfPlayerTwoHasJoined();
            System.out.println("Warte bis Spieler zwei beitritt");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Spieler zwei ist beigetreten");
        game.connectionSuccessfull();
        while (!gameOver) {
            inTurn = gameController.readTurnOfPlayerOne();
            if (!inTurn) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Zug Spieler eins beendet");
                long[] ids = gameController.readStoneIdPositionId();
                System.out.println("suche update infos");
                if (ids != null ) {
                    int s_row = Integer.parseInt(String.valueOf(ids[0]));
                    int s_col = Integer.parseInt(String.valueOf(ids[1]));
                    int p_row = Integer.parseInt(String.valueOf(ids[2]));
                    int p_col = Integer.parseInt(String.valueOf(ids[3]));
                    inTurn=true;
                    game.moveHelperFunc(s_col, s_row, p_row,p_col);
                    gameController.setDefaultUpdateValues();
                    System.out.println("Jetzt bin ich im Thread des Spieler eins");
                    System.out.println("TRUEEEEEE aus player one thread");
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
