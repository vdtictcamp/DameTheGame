package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

public class ClickListenerRedStones extends View  {

    int[][]positionsIds = new int[8][8];
    int [][]redStonePositions = new int[8][8];
    int[][]stones;

    public ClickListenerRedStones(Context context, int[][]positionsIds, int redStonePositions [][]){
        super(context);
        this.positionsIds=positionsIds;
        this.redStonePositions = redStonePositions;
    }


    @SuppressLint("ResourceAsColor")
    public void showValidPositionsForRedStones(View v){
        int id = v.getId();
        int row =0;
        int col = 0;
        for(int i=0; i<redStonePositions.length; i++){
                for(int j=0; j<redStonePositions[i].length; j++){
                    if(redStonePositions[i][j]==id){
                        row = i;
                        col = j;
                        i=redStonePositions.length-1;
                        break;
                    }
                }
            }

        for(int i=row+1; i<row+2; i++){
            for(int j=col-1; j<col+2; j++){
                if(j<0 || j>7 || i<0 || i>7){
                    continue;
                }
                id = positionsIds[i][j];
                View validPos = findViewById(id);
                validPos.setBackgroundColor(R.color.green);
            }
        }

    }

    //Prüft ob ein anderer Stein eine Position belegt
    //Falls das wahr ergibt, dann soll die Position nicht angezeigt werden
    public boolean checkIfStoneIsBlockingPos(){
        return false;
    }
}
