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
    int[][] stones;
    int[][] positionIds;
    List<Integer> stonesToEat;


    public GameController(Context context, int[][] positionIds) {
        this.positionIds = positionIds;
        if(context.getClass().equals(GameField.class)){
            this.activity = (GameField) context;
        }else {
            this.activity=(localGame)context;
        }
    }


    //We will conduct this method fpr every stone which can be eaten
    public void removeStones(int[][]stones, List<Integer> positions, int stoneId, int positionId) {
        int rowChoosenPos = 0;
        int colChoosenPos = 0;
        int rowStone = 0;
        int colStone = 0;
        int diffRow = 0;
        int colDiff = 0;
        int rowJumpPos=0;
        int colJumpPos=0;
        int []index = getChoosenPositionToJump(stones, positionId);
        colChoosenPos=index[1];
        rowChoosenPos=index[0];
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


        }
    }

    private int[] getChoosenPositionToJump(int[][]stones, int positionId){
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

    public List<Integer> fillPositionsToJumpInList(List<List<Integer>>posAfterEat){
        List<Integer>allPositionsToJump = new ArrayList<>();
        System.out.println("Size:"+posAfterEat.size());
        for(int i=0; i<posAfterEat.size(); i++){
            for(int j=0; j<posAfterEat.get(i).size(); j++){
                allPositionsToJump.add(posAfterEat.get(i).get(j));
            }
        }
        return allPositionsToJump;
    }

    public int[] getRowAndCol(View stone) {
        int index[]=new int[2];
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones[i].length; j++) {
                if(stones[i][j]==stone.getId()){
                    index[0]=i;
                    index[1]=j;
                }
            }
        }
        return index;
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
