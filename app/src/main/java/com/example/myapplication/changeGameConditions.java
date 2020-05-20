package com.example.myapplication;

import android.content.Context;
import android.view.View;

public class changeGameConditions extends View {

    private int[][] stones = new int[8][8];
    private int[][] positions = new int[8][8];
    public GameField gameField = new GameField();

    public changeGameConditions(Context context, int[][] stones, int[][] positions) {
        super(context);
        this.stones = stones;
        this.positions = positions;
    }

    public int[][] removeStone(int col, int row) {
        stones[row][col]=0;
        return stones;
    }

    public int[] redStoneToEat(View stone){
        int z=0, k=1;
        int[] ids=new int[4];
        int id=stone.getId();
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (stones[i][j] == id) {
                    if (stones[i + 1][j - 1] == 0 && stones[i + 1][j + 1] == 0) {
                        ids = null;
                    }
                    //Else we need to check if the Position beyond is free
                    else {
                        if (stones[i + 1][j - 1] != 0 && stones[i + 2][j - 2] == 0) {
                            ids[z] = i+1;
                            ids[k] = j-1;
                            k+=2;
                            z+=2;
                        }
                        if (stones[i+1][j + 1] != 0 && stones[i + 2][j + 2] == 0) {
                            ids[z] = i+1;
                            ids[k] = j+1;
                            k+=2;
                            z+=2;
                        }
                    }
                }

            }
        }
        return ids;
    }

    //First we need to check if a enemy stone is in front of another stone
    //Then we need to check if the Position beyond thie stone is free
    public int[] canEateRedStone(View stone) {
        int z=0, k=1;
        int[] ids=new int[4];
        int id=stone.getId();
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (stones[i][j] == id) {
                    if (stones[i + 1][j - 1] == 0 && stones[i + 1][j + 1] == 0) {
                        ids = null;
                    }
                    //Else we need to check if the Position beyond is free
                    else {
                        if (stones[i + 1][j - 1] != 0 && stones[i + 2][j - 2] == 0) {
                            ids[z] = i+2;
                            ids[k] = j-2;
                            k+=2;
                            z+=2;
                        }
                         if (stones[i+1][j + 1] != 0 && stones[i + 2][j + 2] == 0) {
                            ids[z] = i+2;
                            ids[k] = j+2;
                            k+=2;
                            z+=2;
                        }
                    }
                }

            }
        }
        return ids;
    }

}
