package com.example.myapplication.Threads;

import android.os.CountDownTimer;
import android.os.Looper;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Activities.localGame;

import java.util.Locale;

public class TimeThread {

    private TextView countdown;
    private long STARTTIME = 600000;
    private long leftTime = STARTTIME;
    private CountDownTimer timerCount;

    //Time Thread
    public TimeThread(localGame game, TextView countdown) {
        this.countdown = countdown;
        this.countdown.setText(String.valueOf(STARTTIME));
        startCountdown();
    }

    //Starts the Timer
    public void startCountdown(){
        timerCount = new CountDownTimer(leftTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTime = millisUntilFinished;
                updateTimeText();
                if(leftTime==1){
                    timerCount.cancel();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    //Updates the countdown text
    public void updateTimeText(){
        int minutes = (int)(leftTime/1000)/60;
        int seconds = (int)(leftTime/1000)%60;
        String timeLeftText = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        countdown.setText(timeLeftText);
    }
}

