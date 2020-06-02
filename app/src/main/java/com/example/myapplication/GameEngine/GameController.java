package com.example.myapplication.GameEngine;

import android.view.View;

import com.example.myapplication.Activities.GameField;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private GameField gameField;
    int[][] stones;
    int[][] positionIds;
    List<Integer> stonesToEat;

    public GameController(int[][] stones, int[][] positionIds) {
        this.stones = stones;
        this.positionIds = positionIds;
        gameField = new GameField();
    }


    //We will conduct this method fpr every stone which can be eaten
    public void removeStones(List<Integer> positions, int stoneId, int positionId) {
        int rowPos = 0;
        int colPos = 0;
        int rowStone = 0;
        int colStone = 0;
        int diffRow = 0;
        int colDiff = 0;
        int index = positions.indexOf(positionId);
        for (int p_id = index; p_id < positions.size(); p_id++) {
            for (int i = 0; i < stones.length; i++) {
                for (int j = 0; j < stones[i].length; j++) {
                    if (positionIds[i][j] == positions.get(p_id)) {
                        rowPos = i;
                        colPos = j;
                    }
                    if (stones[i][j] == stoneId) {
                        rowStone = i;
                        colStone = j;
                    }

                }
            }
            diffRow = (rowPos + rowStone) / 2;
            colDiff = (colPos + colStone) / 2;
            int stoneToEatId = stones[diffRow][colDiff];
            gameField.eatRedStone(diffRow, colDiff);
        }
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


}
