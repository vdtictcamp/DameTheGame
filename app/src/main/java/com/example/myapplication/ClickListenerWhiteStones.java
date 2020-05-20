package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

public class ClickListenerWhiteStones extends View  {

    int[][]positionsIds = new int[8][8];

    public ClickListenerWhiteStones(Context context, int [][]positionsIds){
        super(context);
        this.positionsIds=positionsIds;
    }

    @SuppressLint("ResourceAsColor")
    public void showValidPositionsForWhiteStones(View v){
        int id = v.getId();
        int row =0;
        int col = 0;
        for(int i=0; i<positionsIds.length; i++){
            for(int j=0; j<positionsIds[i].length; j++){
                if(positionsIds[i][j]==id){
                    row = i;
                    col = j;
                    i=positionsIds.length-1;
                    break;
                }
            }
        }

        for(int i=row-1; i>row-2; i--){
            for(int j=col-1; j<col+2; j++){
                if(col<0 || col>7){
                    continue;
                }
                id = positionsIds[i][j];
                View validPos = findViewById(id);
                validPos.setBackgroundColor(R.color.green);
            }
        }
    }

    public boolean checkIfStoneIsBlockingPos(){
        return false;
    }

}
