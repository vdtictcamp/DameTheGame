package com.example.myapplication.Queen;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.myapplication.GameEngine.ChangeGameConditionRedStone;
import com.example.myapplication.GameEngine.ChangeGameConditionWhiteStone;
import com.example.myapplication.GameEngine.GameController;

import java.util.ArrayList;
import java.util.List;

public class QueenChecker {

    private int[][]positions;
    private List<List<Integer>>allPositionsToJump = new ArrayList<>();
    private GameController gameController;
    private Context context;
    private List<Integer>stonesToEat = new ArrayList<>();

    public QueenChecker(Context context, int[][]positions){
        this.positions=positions;
        this.context=context;
        gameController = new GameController(context, positions);
    }

    //This Method sets the Queen
    public void setRedQueen(View queen){
        queen.setBackgroundColor(Color.MAGENTA);
    }
    public void setWhiteQueen(View queen) {
        queen.setBackgroundColor(Color.LTGRAY);
    }

    public boolean checkIfIsWhiteQueen(List<Integer>whiteQueens, View v){
        boolean isQueen = false;
        for(int i=0; i<whiteQueens.size(); i++){
            if(v.getId()==whiteQueens.get(i)){
                isQueen=true;
            }

        }
        return isQueen;
    }

    public boolean checkIfIsRedQueen(List<Integer>redQueens, View v){
        boolean isQueen = false;
        for(int i=0; i<redQueens.size(); i++){
            if(v.getId()==redQueens.get(i)){
                isQueen=true;
            }

        }
        return isQueen;
    }



    public List<Integer> getPositionsToMove(int[][] stones,  int row, int col){
        List<Integer>posToMove = new ArrayList<>();
        int colDiff =1;
        int rowDiff=1;
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

    public List<Integer> getPositionsToJumpBackwardRight(int[][]stones, int row, int col, int[][] whiteStones, int[][] redStones, boolean whiteQueen){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row+1; i<positions.length; i++){
            if(i>7 || i<0|| i==row){
                continue;
            }
            for(int j=col+1; j<positions[i].length; j++){
                if((i==row+rowDiff && j==col+colDiff &&colDiff%2!=0)) {
                    if(whiteQueen) {
                        if (stones[i][j] != 0 && gameController.checkIfIsWhiteStone(stones[i][j], whiteStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                    else{
                        if (stones[i][j] != 0 && gameController.checkIfIsRedStone(stones[i][j], redStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                }
                if((i==row+rowDiff && j==col+colDiff)){
                    if(stones[i][j]==0 && rowDiff%2==0){
                        positionsToJump.add(positions[i][j]);
                        stonesToEat.add(stones[i-1][j-1]);
                        if(i!=0 || i!=7) {
                            if (checkNextJump(stones, i, j)) {
                                checkPositionsLeft = getPositionsToJumpBackwardLeft(stones, i, j, whiteStones, redStones, whiteQueen);
                                checkPositionsRight = getPositionsToJumpBackwardRight(stones, i, j, whiteStones, redStones, whiteQueen);
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
        }

        allPositionsToJump.add(positionsToJump);
        return positionsToJump;
    }

    public List<Integer> getPositionsToJumpBackwardLeft(int[][]stones, int row, int col, int[][] whiteStones, int[][] redStones, boolean whiteQueen){
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
                if(j<0 || j>7){
                    continue;
                }
                if((i==row+rowDiff && j==col-colDiff &&colDiff%2!=0)) {
                    if(whiteQueen) {
                        if (stones[i][j] != 0 && gameController.checkIfIsWhiteStone(stones[i][j], whiteStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                    else{
                        if (stones[i][j] != 0 && gameController.checkIfIsRedStone(stones[i][j], redStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                }
                if((i==row+rowDiff && j==col-colDiff)){
                    if(stones[i][j]==0 && rowDiff%2==0){
                        positionsToJump.add(positions[i][j]);
                        stonesToEat.add(stones[i-1][j+1]);
                       if(i!=0 || i!=7) {
                           if (checkNextJump(stones, i, j)) {
                               checkPositionsLeft = getPositionsToJumpBackwardLeft(stones, i, j, whiteStones, redStones, whiteQueen);
                               checkPositionsRight = getPositionsToJumpBackwardRight(stones, i, j, whiteStones, redStones, whiteQueen);
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
        }

        allPositionsToJump.add(positionsToJump);
        return positionsToJump;
    }

    public List<Integer>getPositionsToJumpForwardLeft(int[][]stones, int row, int col, int[][] whiteStones, int[][] redStones, boolean whiteQueen){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row; i>row-3; i--){
            if(i>7 || i==row || i<0){
                continue;
            }
            for(int j=col-2; j<positions[i].length; j++){
                if(j<0){
                    continue;
                }
                if((i==row-rowDiff && j==col-colDiff &&colDiff%2!=0)) {
                    if(whiteQueen) {
                        if (stones[i][j] != 0 && gameController.checkIfIsWhiteStone(stones[i][j], whiteStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                    else{
                        if (stones[i][j] != 0 && gameController.checkIfIsRedStone(stones[i][j], redStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                }
                if((i==row-rowDiff && j==col-colDiff)){
                    if(stones[i][j]==0 &&(rowDiff % 2 == 0)){
                        positionsToJump.add(positions[i][j]);
                        stonesToEat.add(stones[i+1][j+1]);
                        if(i!=0 || i!=7) {
                            if (checkNextJump(stones, i, j)) {
                                checkPositionsLeft = getPositionsToJumpForwardLeft(stones, i, j, whiteStones, redStones, whiteQueen);
                                checkPositionsRight = getPositionsToJumpForwardRight(stones, i, j, whiteStones, redStones, whiteQueen);
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
        }
        allPositionsToJump.add(positionsToJump);
        return positionsToJump;
    }

    public List<Integer> getPositionsToJumpForwardRight(int[][]stones, int row, int col, int[][] whiteStones, int[][] redStones, boolean whiteQueen){
        int rowDiff=1;
        int colDiff=1;
        List<Integer>positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight = new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for(int i=row+1; i>row-3; i--){
            if(i>7 || i==row || i<0){
                continue;
            }
            for(int j=col+1; j<positions[i].length; j++){
                if(j<0 || j>7){
                    continue;
                }
                if((i==row-rowDiff && j==col+colDiff &&colDiff%2!=0)) {
                    if(whiteQueen) {
                        if (stones[i][j] != 0 && gameController.checkIfIsWhiteStone(stones[i][j], whiteStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                    else{
                        if (stones[i][j] != 0 && gameController.checkIfIsRedStone(stones[i][j], redStones)) {
                            colDiff++;
                            rowDiff++;
                        }
                    }
                }
                if((i==row-rowDiff && j==col+colDiff)){
                    if(stones[i][j]==0 &&rowDiff%2==0){
                        positionsToJump.add(positions[i][j]);
                        stonesToEat.add(stones[i + 1][j - 1]);
                        if(i!=0 || i!=7){
                        if(checkNextJump(stones, i,j)) {
                            checkPositionsLeft = getPositionsToJumpForwardRight(stones, i, j, whiteStones, redStones, whiteQueen);
                            checkPositionsRight = getPositionsToJumpForwardLeft(stones, i, j, whiteStones, redStones, whiteQueen);
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
        }
        allPositionsToJump.add(positionsToJump);
        return positionsToJump;
    }

    //This Method checks if there are more stones which can be eaten
    public boolean checkNextJump(int[][]stones, int row, int col){
        if( col==0 ){
            if(stones[row+1][col+1]!=0 || stones[row-1][col+1]!=0 ){
                return true;
            }else {
                return false;
            }
        }
        else if(col==7){
            if(stones[row+1][col-1]!=0 || stones[row-1][col-1]!=0){
                return true;
            }else{
                return false;
            }
        }
        else if(row ==0 || row==7){
            return false;
        }
        else if(stones[row+1][col-1]!=0 || stones[row+1][col+1]!=0
                || stones[row-1][col+1]!=0 || stones[row-1][col-1]!=0){
            return true;
        }
        else{
            return false;
        }
    }

    public int[] getRowAndCol(int[][]stones, View queen) {
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

    public List<List<Integer>> returnPostions(){
        System.out.println(allPositionsToJump.size());
        return allPositionsToJump;
    }

    public List<Integer> returnStonesToEat(){
        return stonesToEat;
    }

}
