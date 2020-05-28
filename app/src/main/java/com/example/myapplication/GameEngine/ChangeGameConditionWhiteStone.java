package com.example.myapplication.GameEngine;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChangeGameConditionWhiteStone{

    private int[][] stones = new int[8][8];
    private int[][] positions = new int[8][8];
    private int[][] whiteStonesIds;
    private List<Integer>stonesToEat = new ArrayList<>();

    public ChangeGameConditionWhiteStone( int[][] stones, int[][] positions, int[][] whiteStonesIds ) {
        this.stones = stones;
        this.positions = positions;
        this.whiteStonesIds = whiteStonesIds;
    }





    public boolean checkIfIsWhiteStone(int id){
        for(int i=0; i<whiteStonesIds.length; i++){
            for(int j=0; j<whiteStonesIds[i].length; j++){
                if(whiteStonesIds[i][j]==id){
                    return false;
                }
            }
        }
        return true;
    }

    //First we need to check if a enemy stone is in front of another stone
    //Then we need to check if the Position beyond thie stone is free
    public List<List<Integer>> canEateRedStone(View stone) {
        List<List<Integer>> positionsToJump = new ArrayList<>();
        int id = stone.getId();
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (stones[i][j] == id) {
                    if (j > 0 && j < 7 && i < 7 && i>0) {
                        if (stones[i - 1][j - 1] == 0 && stones[i - 1][j + 1] == 0) {
                            positionsToJump = null;
                        }
                    } else if (j >= 7 &&i>0 ) {
                        if (stones[i - 1][j - 1] == 0) {
                            positionsToJump = null;
                        }
                    } else if (j <= 0 &&i>0 ) {
                        if (stones[i - 1][j + 1] == 0) {
                            positionsToJump = null;
                        }
                    }
                    else if(i==0 && j>0){
                        if (stones[i + 1][j - 1] == 0 && stones[i + 1][j + 1] == 0) {
                            positionsToJump = null;
                        }
                    }
                    else if(i>=7 && j>0){
                        if (stones[i - 1][j - 1] == 0 && stones[i - 1][j + 1] == 0) {
                            positionsToJump = null;
                        }
                    }else
                    if(i==0 && j==0){
                        if (stones[i + 1][j + 1] == 0 ) {
                            positionsToJump = null;
                        }
                    }

                    if(positionsToJump!=null) {
                        List<Integer> pos = collectPosRightDiagonal(i, j);
                        positionsToJump.add(pos);
                        pos = collectPosLeftDiagonal(i, j);
                        positionsToJump.add(pos);
                        //Probably we dont need this methods anymore
                        //pos=collectPosZickZackLeft(i, j);
                        //positionsToJump.add(pos);
                        //pos = collectPosZickZackRight(i, j);
                        //positionsToJump.add(pos);

                    }
                }

            }
        }
        return positionsToJump;
    }



    //Gets all Positions to jump inDiagonal
    public List<Integer> collectPosRightDiagonal(int i, int j) {
        System.out.println("clicked von diagonal");
        int colDiff = 1;
        int rowDiff = 1;
        List<Integer> positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight=new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for (int k = i; k < positions.length; k++) {
            for (int z = 0; z < positions[i].length; z++) {
                if (z < 8) {
                    if (k == i + rowDiff && z == j + colDiff && (colDiff % 2 != 0)) {
                        if (stones[k][z] != 0 && checkIfIsWhiteStone(stones[k][z])) {
                            rowDiff++;
                            colDiff++;
                        }
                    }
                    if(j+colDiff<8){
                        if (k == i + rowDiff && z == j + colDiff && (colDiff % 2 == 0)) {
                            if (stones[k][z] == 0 ) {
                                positionsToJump.add(positions[k][z]);
                                stonesToEat.add(stones[k - 1][z - 1]);
                                colDiff++;
                                rowDiff++;
                                if(k!=7){
                                    if(checkNextJump(k, z)) {
                                        checkPositionsLeft = collectPosLeftDiagonal(k, z);
                                        checkPositionsRight = collectPosRightDiagonal(k, z);
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
            }
        }

        return positionsToJump;
    }

    public List<Integer>collectPosLeftDiagonal(int i, int j) {

        System.out.println("clicked von diagonal");
        int colDiff = 1;
        int rowDiff = 1;
        List<Integer>checkPositionsRight=new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        List<Integer> positionsToJump = new ArrayList<>();
        for (int k = i; k < positions.length; k++) {
            for (int z = 0; z < positions[i].length; z++) {
                if (z >=0) {
                    if ((k == i + rowDiff && z == j - colDiff) && (colDiff % 2 != 0)) {
                        if ((stones[k][z] != 0) && (checkIfIsWhiteStone(stones[k][z]))) {
                            rowDiff++;
                            colDiff++;
                        }
                    }
                    if (j - colDiff >= 0){
                        if ((k == i + rowDiff && z == j - colDiff) && (colDiff % 2 == 0)) {
                            if (stones[k][z] == 0) {
                                positionsToJump.add(positions[k][z]);
                                stonesToEat.add(stones[k - 1][z + 1]);
                                colDiff++;
                                rowDiff++;
                                if(k!=0 &&z!=0){
                                    if(checkNextJump(k, z)) {
                                        checkPositionsLeft = collectPosLeftDiagonal(k, z);
                                        checkPositionsRight = collectPosRightDiagonal(k, z);
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

            }
        }
        return positionsToJump;
    }
    public boolean checkNextJump(int row, int col){
        if(row ==0 || col==0 || col==7 || row==7){
            return false;
        }
        else if(stones[row-1][col-1]!=0 || stones[row-1][col+1]!=0){
            return true;
        }
        else{
            return false;
        }
    }


    public List<Integer> returnStonesToEat(){
        return stonesToEat;
    }
}
