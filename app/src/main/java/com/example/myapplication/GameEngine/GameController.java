package com.example.myapplication.GameEngine;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Activities.localGame;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private Activity activity;
    int[][] positionIds;


    public GameController(Context context, int[][] positionIds) {
        this.positionIds = positionIds;
        if(context.getClass().equals(GameField.class)){
            this.activity = (GameField) context;
        }else {
            this.activity=(localGame)context;
        }
    }

    public boolean checkIfIsRedStone(int id, int[][]redStonesIds) {
        for (int i = 0; i < redStonesIds.length; i++) {
            for (int j = 0; j < redStonesIds[i].length; j++) {
                if (redStonesIds[i][j] == id) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkIfIsWhiteStone(int id, int[][]whiteStonesIds){
        for(int i=0; i<whiteStonesIds.length; i++){
            for(int j=0; j<whiteStonesIds[i].length; j++){
                if(whiteStonesIds[i][j]==id){
                    return false;
                }
            }
        }
        return true;
    }

    //We will conduct this method fpr every stone which can be eaten
    public void removeStones(int[][]stones, List<Integer> positions, int stoneId, int positionId) {
        int colChoosenPos = 0,rowChoosenPos=0, rowStone = 0, colStone = 0, rowJumpPos=0, colJumpPos=0;
        int diffRow = 0, colDiff = 0;
        int []index = getChoosenPositionToJump(stones, positionId);
        colChoosenPos=index[1];
        for (int p_id = 0; p_id < positions.size(); p_id++) {
            for (int i = 0; i < stones.length; i++) {
                for (int j = 0; j < stones[i].length; j++) {
                    if(positionIds[i][j]==positions.get(p_id)){
                        rowJumpPos=i;
                        colJumpPos=j;
                    }
                    if (stones[i][j] == stoneId || stoneId==positionIds[i][j]) {
                        rowStone = i;
                        colStone = j;
                    }

                }
            }
            if(colChoosenPos>colStone){
                if(colJumpPos>=colStone) {
                    diffRow = (rowJumpPos + rowStone) / 2;
                    colDiff = (colJumpPos + colStone) / 2;
                    if (activity instanceof localGame) {
                        ((localGame) activity).removeStone(diffRow, colDiff);
                    } else {
                        ((GameField) activity).removeStone(diffRow, colDiff);
                    }
                    stoneId = positionIds[rowJumpPos][colJumpPos];
                }
            }if(colChoosenPos<colStone){
                if(colJumpPos<=colStone){
                    diffRow = (rowJumpPos + rowStone) / 2;
                    colDiff = (colJumpPos + colStone) / 2;
                    if(activity instanceof localGame) {
                        ((localGame) activity).removeStone(diffRow, colDiff);
                    }else{
                        ((GameField) activity).removeStone(diffRow, colDiff);
                    }
                    stoneId = positionIds[rowJumpPos][colJumpPos];

                }
            }
            if(colChoosenPos<=colStone){
                if(colJumpPos<=colStone){
                    diffRow = (rowJumpPos + rowStone) / 2;
                    colDiff = (colJumpPos + colStone) / 2;
                    if(activity instanceof localGame) {
                        ((localGame) activity).removeStone(diffRow, colDiff);
                    }else{
                        ((GameField) activity).removeStone(diffRow, colDiff);
                    }
                    stoneId = positionIds[rowJumpPos][colJumpPos];

                }
            }
            if(colChoosenPos>=colStone){
                if(colJumpPos>=colStone) {
                    diffRow = (rowJumpPos + rowStone) / 2;
                    colDiff = (colJumpPos + colStone) / 2;
                    if (activity instanceof localGame) {
                        ((localGame) activity).removeStone(diffRow, colDiff);
                    } else {
                        ((GameField) activity).removeStone(diffRow, colDiff);
                    }
                    stoneId = positionIds[rowJumpPos][colJumpPos];
                }
            }
            if(positions.get(p_id)==positionId){
                break;
            }
        }

    }

    public int[] getChoosenPositionToJump(int[][]stones, int positionId){
        int rowChoosenPos=0;
        int colChoosenPos=0;
        int[]index = new int[2];
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones[i].length; j++) {
                if (positionIds[i][j] == positionId) {
                    rowChoosenPos = i;
                    colChoosenPos = j;
                    break;
                }
            }
        }
        index[0]=rowChoosenPos;
        index[1]=colChoosenPos;
        return index;
    }
    //This Method fills all positions in one List
    public List<Integer> fillPositionsToJumpInList(List<List<Integer>>posAfterEat, boolean isQueen){
        List<Integer>allPositionsToJump = new ArrayList<>();

            System.out.println(isQueen);
            for (int i = 0; i < posAfterEat.size(); i++) {
                for (int j = 0; j < posAfterEat.get(i).size(); j++) {
                    allPositionsToJump.add(posAfterEat.get(i).get(j));
                }
            }
            System.out.println(isQueen);
            for (int i = posAfterEat.size()-1; i>=0; i--) {
                for (int j = posAfterEat.get(i).size()-1; j>=0; j--) {
                    allPositionsToJump.add(posAfterEat.get(i).get(j));
                }
            }

        return allPositionsToJump;
    }

//bewegter Stein Id und zielpositionID
    public int[][] switchPosOfStoneInArray(int[][]stones,int[][]positionIds, int stoneID, int posId){
        int oldCol=0;
        int oldRow=0;
        for(int i=0; i<positionIds.length; i++){
            for(int j=0; j<positionIds[i].length; j++){
                if(stones[i][j]==stoneID){
                    oldCol=j;
                    oldRow=i;
                }
                //Now we got the stone and we need to change the index
                if(positionIds[i][j]==posId){
                    stones[i][j]=stoneID;
                }
            }
        }
        stones[oldRow][oldCol]=0;
        return stones;
    }

    public boolean checkIfStoneIsBlockingPos(int[][]stones, int col, int row){
        int id = stones[row][col];
        if(stones[row][col]==0){
            return false;
        }else{
            return true;
        }
    }

}
