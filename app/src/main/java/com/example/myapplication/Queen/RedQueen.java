package com.example.myapplication.Queen;

import android.view.View;

import com.example.myapplication.GameEngine.ChangeGameConditionRedStone;

import java.util.ArrayList;
import java.util.List;

public class RedQueen {

    ChangeGameConditionRedStone chGameCond;
    List<Integer>redQueens = new ArrayList<>();
    int[][]redStones;
    View queen;
    int[][] positions;
    int[][]stones;
    int row=0;
    int col =0;


    public RedQueen(View queen, List<Integer>redQueens, int[][] positions, int[][]stones, int[][]redStones){
        this.redQueens=redQueens;
        this.queen=queen;
        this.stones=stones;
        this.positions=positions;
        this.redStones=redStones;
        chGameCond= new ChangeGameConditionRedStone(this.stones, this.positions, redStones);
    }



    public View getQueen(){
        return this.queen;

    }
    public void setQueen(View queen){
        this.queen=queen;
        queen.getLayoutParams().height=120;
        queen.getLayoutParams().width=120;
    }

    public List<Integer> getPositionsToMove(int row, int col){
        List<Integer>posToMove = new ArrayList<>();
        int colDiff =1;
        int rowDiff=1;
        System.out.println("Spalte:"+col +"Reihe:"+ row);
        for(int i=row-1; i<row+2; i++){
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

    public List<Integer> getPositionsToJumpForwardRight(int row, int col){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row-1; i<row+3; i++){
            if(i>7 || i<0|| i==row){
                continue;
            }
            for(int j=col-2; j<positions[i].length; j++){
                if((i==row+rowDiff && j==col+colDiff &&colDiff%2!=0)) {
                    if (stones[i][j] != 0 && chGameCond.checkIfIsRedStone(stones[i][j])) {
                        colDiff++;
                        rowDiff++;
                    }
                }
                if((i==row+rowDiff && j==col+colDiff)){
                    if(stones[i][j]==0 && rowDiff%2==0){
                        System.out.println("Feld leer");
                        positionsToJump.add(positions[i][j]);
                        if(chGameCond.checkNextJump(i,j)){
                            checkPositionsLeft=getPositionsToJumpForwardLeft(i,j);
                            checkPositionsRight=getPositionsToJumpForwardRight(i,j);
                            for (int p = 0; p < checkPositionsRight.size(); p++) {
                                positionsToJump.add(checkPositionsRight.get(p));
                            }
                            for (int p = 0; p < checkPositionsLeft.size(); p++) {
                                positionsToJump.add(checkPositionsLeft.get(p));
                            }
                        }
                    }
                }
            }
        }

        return positionsToJump;
    }

    public List<Integer> getPositionsToJumpForwardLeft(int row, int col){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row-1; i<row+3; i++){
            if(i>7 || i<0|| i==row){
                continue;
            }
            for(int j=col-2; j<positions[i].length; j++){
                if(j<0){
                    continue;
                }
                if((i==row+rowDiff && j==col-colDiff &&colDiff%2!=0)) {
                    if (stones[i][j] != 0 && chGameCond.checkIfIsRedStone(stones[i][j])) {
                        colDiff++;
                        rowDiff++;
                    }
                }
                if((i==row+rowDiff && j==col-colDiff) && rowDiff%2==0){
                    if(stones[i][j]==0 && rowDiff%2==0){
                        System.out.println("Feld leer");
                        positionsToJump.add(positions[i][j]);
                        if(chGameCond.checkNextJump(i,j)){
                            checkPositionsLeft=getPositionsToJumpForwardLeft(i,j);
                            checkPositionsRight=getPositionsToJumpForwardRight(i,j);
                            for (int p = 0; p < checkPositionsRight.size(); p++) {
                                positionsToJump.add(checkPositionsRight.get(p));
                            }
                            for (int p = 0; p < checkPositionsLeft.size(); p++) {
                                positionsToJump.add(checkPositionsLeft.get(p));
                            }
                        }
                    }
                }
            }
        }

        return positionsToJump;
    }




    public int[] getRowAndCol(View queen) {
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
