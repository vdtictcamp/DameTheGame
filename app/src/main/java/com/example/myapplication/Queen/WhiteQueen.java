package com.example.myapplication.Queen;

import android.graphics.LightingColorFilter;
import android.view.View;

import com.example.myapplication.GameEngine.ChangeGameConditionRedStone;
import com.example.myapplication.GameEngine.ChangeGameConditionWhiteStone;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WhiteQueen {

    private View queen;
    private List<Integer> whiteQueens;
    private int[][] positions;
    private int[][] stones;
    private int[][] whiteStones;
    private int row = 0;
    private int col = 0;
    private List<List<Integer>>allPositionsToJump = new ArrayList<>();
    ChangeGameConditionWhiteStone chGameCondWhite;

    //We need this object because the queen of the white figures can move in the same way as the red stones can do
    ChangeGameConditionRedStone changeGameCondRed;

    public WhiteQueen( List<Integer> whiteQueens, int[][] positions, int[][] stones, int[][] whiteStones) {
        this.whiteQueens = whiteQueens;
        this.stones = stones;
        this.positions = positions;
        this.whiteStones = whiteStones;
        chGameCondWhite = new ChangeGameConditionWhiteStone(stones, positions, whiteStones);
        changeGameCondRed = new ChangeGameConditionRedStone( stones, positions, whiteStones);
    }


    public void setQueen(View queen) {
        this.queen = queen;
        queen.setBackgroundColor(R.drawable.whitequeen);
    }


    public List<Integer> getPositionsToJumpBackwardRight(int row, int col){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row+1; i<row+3; i++){
            if(i>7 || i<0|| i==row){
                continue;
            }
            for(int j=col-2; j<positions[i].length; j++){
                if((i==row+rowDiff && j==col+colDiff &&colDiff%2!=0)) {
                    if (stones[i][j] != 0 && chGameCondWhite.checkIfIsWhiteStone(stones[i][j])) {
                        colDiff++;
                        rowDiff++;
                    }
                }
                if((i==row+rowDiff && j==col+colDiff)){
                    if(stones[i][j]==0 && rowDiff%2==0){
                        positionsToJump.add(positions[i][j]);
                        if(chGameCondWhite.checkNextJump(i,j)){
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

        allPositionsToJump.add(positionsToJump);
        return positionsToJump;
    }

    public List<Integer> getPositionsToJumpBackwardLeft(int row, int col){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row+1; i<row+3; i++){
            if(i>7 || i<0|| i==row){
                continue;
            }
            for(int j=col-2; j<positions[i].length; j++){
                if((i==row+rowDiff && j==col-colDiff &&colDiff%2!=0)) {
                    if (stones[i][j] != 0 && chGameCondWhite.checkIfIsWhiteStone(stones[i][j])) {
                        colDiff++;
                        rowDiff++;
                    }
                }
                if((i==row+rowDiff && j==col-colDiff)){
                    if(stones[i][j]==0 && rowDiff%2==0){
                        positionsToJump.add(positions[i][j]);
                        if(chGameCondWhite.checkNextJump(i,j)){
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

        allPositionsToJump.add(positionsToJump);
        return positionsToJump;
    }

    public List<Integer>getPositionsToJumpForwardLeft(int row, int col){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row+1; i>row-3; i--){
            if(i>7 || i==row || i<0){
                continue;
            }
            for(int j=col-2; j<positions[i].length; j++){
                if(j<0){
                    continue;
                }
                if((i==row-rowDiff && j==col-colDiff &&colDiff%2!=0)) {
                    if (stones[i][j] != 0 && chGameCondWhite.checkIfIsWhiteStone(stones[i][j])) {
                        colDiff++;
                        rowDiff++;
                    }
                }
                if((i==row-rowDiff && j==col-colDiff)){
                    if(stones[i][j]==0 &&(rowDiff % 2 == 0)){
                        System.out.println("Feld leer");
                        positionsToJump.add(positions[i][j]);
                        if(chGameCondWhite.checkNextJump(i,j)){
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
        allPositionsToJump.add(positionsToJump);
        return positionsToJump;
    }

    public List<Integer> getPositionsToJumpForwardRight(int row, int col){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row+1; i>row-3; i--){
            if(i>7 || i==row || i<=0){
                continue;
            }
            for(int j=col-2; j<positions[i].length; j++){
                if(j<0 || j>7){
                    continue;
                }
                if((i==row-rowDiff && j==col+colDiff &&colDiff%2!=0)) {
                    if (stones[i][j] != 0 && chGameCondWhite.checkIfIsWhiteStone(stones[i][j])) {
                        colDiff++;
                        rowDiff++;
                    }
                }
                    if((i==row-rowDiff && j==col+colDiff)){
                        if(stones[i][j]==0 &&rowDiff%2==0){
                            System.out.println("Feld leer");
                            positionsToJump.add(positions[i][j]);
                            if(chGameCondWhite.checkNextJump(i,j)){
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
        allPositionsToJump.add(positionsToJump);
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
        for(int i=0; i<whiteQueens.size(); i++){
            if(v.getId()==whiteQueens.get(i)){
                isQueen=true;
            }

        }
        return isQueen;
    }

    public List<List<Integer>> returnPostions(){
        return allPositionsToJump;
    }

}
