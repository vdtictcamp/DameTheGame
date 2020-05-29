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


    public void checkMovement(int positionId){
        int row=0;
        int id=0;
        List<Integer>rows = new ArrayList<>();
            for (int i = 0; i < stones.length; i++) {
                for (int j = 0; j < stones[i].length; j++) {
                    if (positions[i][j] == positionId){

                    }
                }
            }
        }
}
