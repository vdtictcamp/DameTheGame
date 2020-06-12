package com.example.myapplication.GameEngine;

public class CheckIfGameIsFinish {


    public boolean checkIfGameIsFinish(int[][]whiteStones, int[][]redStones){
        int counter_1=0;
        int counter_2=0;
        boolean isFinish = false;
        for(int i=0; i<whiteStones.length; i++){
            for(int j=0; j<whiteStones[i].length; j++){
                if(whiteStones[i][j]==0){
                    counter_1++;
                }
            }
        }
        for(int i=0; i<redStones.length; i++){
            for(int j=0; j<redStones[i].length; j++){
                if(redStones[i][j]==0){
                    counter_2++;
                }
            }
        }
        if(counter_1>=9 || counter_2>=9){
            isFinish=true;
        }
        return isFinish;
    }
}
