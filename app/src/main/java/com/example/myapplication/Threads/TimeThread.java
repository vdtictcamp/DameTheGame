package com.example.myapplication.Threads;

import android.os.Looper;
import android.widget.EditText;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Activities.localGame;

public class TimeThread extends Thread implements Runnable {

    private localGame gameField;
    private int timer = 600;
    boolean finish = false;
    EditText countdown;

    public TimeThread(localGame game, EditText countdown){
        this.gameField = game;
        this.countdown = countdown;
    }

    @Override
    public void run() {
        Looper.prepare();
        while (timer>0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer--;
            //countdown.setText(String.valueOf(timer));
            if(timer<=0){
                stopGame();
            }
        }
        finish = true;
    }

private void stopGame(){
        gameField.stopGame();
}

}
