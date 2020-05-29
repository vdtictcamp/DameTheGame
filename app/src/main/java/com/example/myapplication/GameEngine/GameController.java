package com.example.myapplication.GameEngine;

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


    public void checkMovement(){
        int row=0;
        int id=0;
        List<Integer>rows = new ArrayList<>();
        for(int s_id =0; s_id<stonesToEat.size(); s_id++) {
            for (int i = 0; i < stones.length; i++) {
                for (int j = 0; j < stones[i].length; j++) {
                    if (stones[i][j] == stonesToEat.get(s_id)){

                    }
                }
            }
        }
    }
}
