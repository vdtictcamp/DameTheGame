package com.example.myapplication.GameEngine;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    int[][]stones;
    int[][]positions;
    List<Integer> stonesToEat;

    public GameController(int[][]stones, int[][]positions, List<Integer>stonesToEat){
        this.stones=stones;
        this.positions=positions;
    }


    public void removeStones(int positionId, int stoneId) {
        int rowPos = 0;
        int colPos =0;
        int rowStone=0;
        int colStone=0;
        List<Integer> rows = new ArrayList<>();
        for (int s_id = 0; s_id < stonesToEat.size(); s_id++) {
            for (int i = 0; i < stones.length; i++) {
                for (int j = 0; j < stones[i].length; j++) {
                    if (positions[i][j] == positionId) {
                        rowPos=i;
                        colPos=j;
                    }
                    if(stones[i][j]==stoneId){
                        rowStone=i;
                        colStone=j;
                    }
                }
            }
        }
    }
}
