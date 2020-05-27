package com.example.myapplication.Queen;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RedQueen {


    List<Integer>redQueens = new ArrayList<>();
    View queen;
    int[][] positions;
    int[][]stones;
    int row=0;
    int col =0;

    public RedQueen(View queen, List<Integer>redQueens, int[][] positions, int[][]stones){
        this.redQueens=redQueens;
        this.queen=queen;
        this.stones=stones;
        this.positions=positions;
    }



    public View getQueen(){
        return this.queen;

    }
    public void setQueen(View queen){
        this.queen=queen;
        queen.getLayoutParams().height=120;
        queen.getLayoutParams().width=120;
    }

    public List<Integer> getPositionsToMove( ){
        List<Integer>posToMove = new ArrayList<>();
        int colDiff =1;
        int rowDiff=1;
        int[]index = getRowAndCol();
        col = index[1];
        row=index[0];
        for(int i=row; i>row-2; i--){
            if(i==row || i<0 || i>7){
                continue;
            }
            for(int j=0; j<stones[i].length; j++){
                if((i==row+rowDiff &&j==col-colDiff) || (i==row+rowDiff &&j==col+colDiff)){
                    if(stones[i][j]==0){
                        posToMove.add(positions[i][j]);
                    }
                }
            }

        }
        return posToMove;

    }
    public int[] getRowAndCol() {
        int index[]=new int[2];
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones[i].length; j++) {
                if(stones[i][j]==queen.getId()){
                        index[0]=i;
                        index[1]=j;
                }
            }
        }
        return index;
    }

    public boolean checkIfIsQueen(View v){
        boolean isQueen = false;
        for(int i=0; i<redQueens.size(); i++){
            if(v.getId()==redQueens.get(i)){
                isQueen=true;
            }

        }
        return isQueen;
    }



}
