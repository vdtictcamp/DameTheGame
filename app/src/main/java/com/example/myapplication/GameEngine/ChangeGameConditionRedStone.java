package com.example.myapplication.GameEngine;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChangeGameConditionRedStone {


    private int[][] stones = new int[8][8];
    private int[][] positions = new int[8][8];
    private int[][] redStonesIds;
    List<Integer>stonesToEat = new ArrayList<>();

    public ChangeGameConditionRedStone(int[][] stones, int[][] positions, int[][] redStonesIds) {
        this.stones = stones;
        this.positions = positions;
        this.redStonesIds = redStonesIds;
    }



    public boolean checkIfIsRedStone(int id) {
        for (int i = 0; i < redStonesIds.length; i++) {
            for (int j = 0; j < redStonesIds[i].length; j++) {
                if (redStonesIds[i][j] == id) {
                    return false;
                }
            }
        }
        return true;
    }



    public List<List<Integer>> canEateWhiteStoneBeta(View stone) {
        List<List<Integer>> positionsToJump = new ArrayList<>();
        int id = stone.getId();
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (stones[i][j] == id) {
                    if (j > 0 && j < 7 && i < 7 && i>0) {
                        if (stones[i - 1][j - 1] == 0 && stones[i - 1][j + 1] == 0) {
                            break;
                        }
                    } else if (j >= 7 &&i>0 ) {
                        if (stones[i - 1][j - 1] == 0) {
                            break;
                        }
                    } else if (j <= 0 &&i>0 ) {
                        if (stones[i - 1][j + 1] == 0) {
                            break;
                        }
                    }
                    else if(i==0 && j>0){
                        if (stones[i + 1][j - 1] == 0 && stones[i + 1][j + 1] == 0) {
                            break;
                        }
                    }
                    else if(i>=7 && j>0){
                        if (stones[i - 1][j - 1] == 0 && stones[i - 1][j + 1] == 0) {
                            break;
                        }
                    }else
                        if(i==0 && j==0){
                        if (stones[i + 1][j + 1] == 0 ) {
                            break;
                        }
                    }

                    if(positionsToJump!=null) {
                        List<Integer> pos = collectPosRightDiagonal(i, j);
                        positionsToJump.add(pos);
                        pos = collectPosLeftDiagonal(i, j);
                        positionsToJump.add(pos);


                    }
                }

            }
        }
        return positionsToJump;
    }



    //Gets all Positions to jump inDiagonal
    public List<Integer> collectPosRightDiagonal(int i, int j) {

       System.out.println(i+" "+" "+j +"aus collect");
        int colDiff = 1;
        int rowDiff = 1;
        List<Integer> positionsToJump = new ArrayList<>();
        List<Integer>checkPositionsRight=new ArrayList<>();
        List<Integer>checkPositionsLeft = new ArrayList<>();
        for (int k = i; k >=0; k--) {
            for (int z = 0; z < positions[i].length; z++) {
                if (z < 8) {
                    if ((k == i - rowDiff && z == j + colDiff) && (colDiff % 2 != 0)) {
                        if ((stones[k][z] != 0) && (checkIfIsRedStone(stones[k][z]))) {
                            rowDiff++;
                            colDiff++;
                        }
                    }
                    if(j+colDiff<8){
                    if ((k == i - rowDiff && z == j + colDiff) && (colDiff % 2 == 0)) {
                        if (stones[k][z] == 0 ) {
                            positionsToJump.add(positions[k][z]);
                            stonesToEat.add(stones[k + 1][z - 1]);
                            colDiff++;
                            rowDiff++;
                            if(k!=0 && z!=0){
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

public List<Integer> collectPosLeftDiagonal(int i, int j){
    int colDiff = 1;
    int rowDiff = 1;
    List<Integer>checkPositionsRight=new ArrayList<>();
    List<Integer>checkPositionsLeft = new ArrayList<>();
    List<Integer> positionsToJump = new ArrayList<>();
    for (int k = i; k >=0; k--) {
        for (int z = 0; z < positions[i].length; z++) {
            if (z >=0) {
                if ((k == i - rowDiff && z == j - colDiff) && (colDiff % 2 != 0)) {
                    if (stones[k][z] != 0 && (checkIfIsRedStone(stones[k][z]))) {
                        rowDiff++;
                        colDiff++;
                    }
                }
                if (j - colDiff >= 0) {
                    if (((k == i - rowDiff) && (z == j - colDiff)) && (colDiff % 2 == 0)) {
                        if (stones[k][z] == 0) {
                            positionsToJump.add(positions[k][z]);
                            stonesToEat.add(stones[k + 1][z + 1]);
                            colDiff++;
                            rowDiff++;
                            if (k!=0){
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



    //Noch in Bearbeitung
    private List<Integer> collectPosZickZackLeft(int i, int j) {
        List<Integer> positionsToJump = new ArrayList<>();
        int colDiff = 1;
        int rowDiff = 1;
        int stoneCol=0;
        int stoneRow=0;
        boolean containsStone = false;
        for (int k = i; k >= 0; k--) {
            for (int z = 0; z < positions[i].length; z++) {
                if (z <=6) {
                    if ((k == i - rowDiff && z == j - 1) && (rowDiff%2!=0)) {
                        if ((stones[k][z] != 0 && (checkIfIsRedStone(stones[k][z])))){
                                containsStone=true;
                                stoneCol=z;
                                stoneRow=k;
                                rowDiff++;

                        }
                    }
                        if (((k==i-rowDiff && z==j) &&(rowDiff%2==0)) || ((k==i-rowDiff &&z==j-2)&&(rowDiff%2==0))) {
                            if (stones[k][z] == 0 && containsStone==true) {
                                positionsToJump.add(positions[k][z]);
                                stonesToEat.add(stones[stoneRow][stoneCol]);
                                containsStone=false;
                                rowDiff++;
                                z=positions.length-1;
                            }
                        }
                }

            }

                }
                return positionsToJump;
            }


            //Noch in Bearbeitung
    private List<Integer> collectPosZickZackRight(int i, int j) {
        List<Integer> positionsToJump = new ArrayList<>();
        boolean containsStone=false;
        int stoneRow=0;
        int stoneCol=0;
        int rowDiff = 1;
        for (int k = i; k >=0; k--) {
            for (int z = 0; z < positions[i].length; z++) {
                if (z <=6) {
                    if ((k == i - rowDiff && z == j + 1) && (rowDiff%2!=0)) {
                        if ((stones[k][z] != 0 && (checkIfIsRedStone(stones[k][z])))){
                            containsStone=true;
                            stoneCol=z;
                            stoneRow=k;
                            rowDiff++;

                        }
                    }
                    if (((k==i-rowDiff && z==j && stones[k][z]==0 && rowDiff>2) &&(rowDiff%2==0)) || ((k==i-rowDiff &&z==j+2)&&(rowDiff%2==0))) {
                        if (stones[k][z] == 0 && containsStone==true) {
                            positionsToJump.add(positions[k][z]);
                            stonesToEat.add(stones[stoneRow][stoneCol]);
                            containsStone=false;
                            rowDiff++;
                        }
                    }
                }
            }
        }
        return positionsToJump;

    }


    public List<List<Integer>>returnPosistionsToJump(){

        return returnPosistionsToJump();


    }
    public List<Integer> returnStonesToEat () {
                return stonesToEat;
            }

    }
