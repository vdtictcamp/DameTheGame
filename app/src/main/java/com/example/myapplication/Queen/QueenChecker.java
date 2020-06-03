package com.example.myapplication.Queen;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class QueenChecker {

    private int[][]positions;

    public QueenChecker(int[][]positions){
        this.positions=positions;
    }

    public List<Integer> getPositionsToMove(int[][] stones,  int row, int col){
        List<Integer>posToMove = new ArrayList<>();
        int colDiff =1;
        int rowDiff=1;
        System.out.println("Spalte:"+col +"Reihe:"+ row);
        for(int i=row+1; i>row-2; i--){
            if(i==row || i<0 || i>7){
                continue;
            }
            for(int j=0; j<stones[i].length; j++){
                if((i==row-rowDiff &&j==col-colDiff) || (i==row+rowDiff &&j==col+colDiff)
                        || (i==row+rowDiff &&j==col-colDiff) ||(i==row-rowDiff &&j==col+colDiff)){
                    if(stones[i][j]==0){
                        posToMove.add(positions[i][j]);
                    }
                }
            }

        }
        return posToMove;

    }


}
