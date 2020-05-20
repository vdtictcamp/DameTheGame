package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameField extends AppCompatActivity{

    public GridLayout gameLayout;
    private int[][]positionsIds;
    private int[][]whiteStonesIds;
    private int[][]redStonesIds;
    private int[][] stones;
    private View[][] redStones;
    private View[][]whiteStones;
    private View[][]positions;
    private int[] validPositionsToMove=new int[2];
    public  int[] helpPositions = new int[2];
    public View movingStone;
    changeGameConditions chGameCond;
    private int[] posAfterEat=new int[4];
    private int[] redStoneToEat = new int[4];


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        gameLayout = findViewById(R.id.gamelayout);
        redStones = new View[8][8];
        whiteStones = new View[8][8];
        positions = new View[8][8];

        //All position
        positionsIds = new int[][]{{R.id.pos1a, R.id.pos1b, R.id.pos1c, R.id.pos1d, R.id.pos1e, R.id.pos1f, R.id.pos1e, R.id.pos1h},
                {R.id.pos2a, R.id.pos2b, R.id.pos2c, R.id.pos2d, R.id.pos2e, R.id.pos2f, R.id.pos2g, R.id.pos2h},
                {R.id.pos3a, R.id.pos3b, R.id.pos3c, R.id.pos3d, R.id.pos3e, R.id.pos3f, R.id.pos3g, R.id.pos3h},
                {R.id.pos4a, R.id.pos4b, R.id.pos4c, R.id.pos4d, R.id.pos4e, R.id.pos4f, R.id.pos4g, R.id.pos4h},
                {R.id.pos5a, R.id.pos5b, R.id.pos5c, R.id.pos5d, R.id.pos5e, R.id.pos5f, R.id.pos5g, R.id.pos5h},
                {R.id.pos6a, R.id.pos6b, R.id.pos6c, R.id.pos6d, R.id.pos6e, R.id.pos6f, R.id.pos6g, R.id.pos6h},
                {R.id.pos7a, R.id.pos7b, R.id.pos7c, R.id.pos7d, R.id.pos7e, R.id.pos7f, R.id.pos7g, R.id.pos7h},
                {R.id.pos8a, R.id.pos8b, R.id.pos8c, R.id.pos8d, R.id.pos8e, R.id.pos8f, R.id.pos8g, R.id.pos8h}};


        //Fill the Array with all Positions
        for(int i=0; i<positions.length;i++){
            for(int j=0; j<positions[i].length; j++){
                int id = positionsIds[i][j];
                View pos = findViewById(id);
                positions[i][j]=pos;
            }
        }

        //All white stones
        whiteStonesIds = new int[][]{{R.id.w1, 0, R.id.w2, 0, R.id.w3, 0, R.id.w4, 0},
                {0,R.id.w5, 0,R.id.w6, 0,R.id.w7, 0,R.id.w8},
                {R.id.w9, 0, R.id.w10, 0, R.id.w11, 0, R.id.w12, 0},
                {0, 0,0,0,0,0,0,0}, {0, 0,0,0,0,0,0,0},
                {0, 0,0,0,0,0,0,0}, {0, 0,0,0,0,0,0,0},
                {0, 0,0,0,0,0,0,0}};

        //All red stones
        redStonesIds = new int[][]{{0,R.id.b1, 0,R.id.b2, 0,R.id.b3, 0,R.id.b4},
                {R.id.b5,0, R.id.b6,0, R.id.b7,0, R.id.b8,0},
                {0,R.id.b9, 0,R.id.b10, 0,R.id.b11, 0,R.id.b12},
                {0, 0,0,0,0,0,0,0},{0, 0,0,0,0,0,0,0},
                {0, 0,0,0,0,0,0,0}, {0, 0,0,0,0,0,0,0},
                {0, 0,0,0,0,0,0,0}};

        //The distrubution of the Stones on the Board
        stones = new int[][]{{R.id.w1, 0, R.id.w2, 0, R.id.w3, 0, R.id.w4, 0},
                { 0, R.id.w5, 0, R.id.w6, 0, R.id.w7, 0, R.id.w8},
                {R.id.w9, 0, R.id.w10, 0, R.id.w11, 0, R.id.w12, 0},
                {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                {0,R.id.b9, 0,R.id.b10, 0,R.id.b11, 0,R.id.b12},
                {R.id.b5,0, R.id.b6,0, R.id.b7,0, R.id.b8,0},
                {0,R.id.b1, 0,R.id.b2, 0,R.id.b3, 0,R.id.b4}};

        chGameCond = new changeGameConditions(this, stones, positionsIds);


        View.OnClickListener redStoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("aus redstone listener");
                showValidPositionsForRedStones(v);
            }
        };

        View.OnClickListener whiteStoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                showValidPositionsForWhiteStones(v);
                 posAfterEat= chGameCond.canEateRedStone(v);
                 if(posAfterEat!=null) {
                     if (posAfterEat[0] != 0 && posAfterEat[1] != 0) {
                         ShowThePositionAfterEatingStone(posAfterEat[1], posAfterEat[0]);
                     }
                     if (posAfterEat[2] != 0 && posAfterEat[3] != 0) {
                         ShowThePositionAfterEatingStone(posAfterEat[3], posAfterEat[2]);
                     }
                 }

                 redStoneToEat = chGameCond.redStoneToEat(v);
            }
        };

        /*
        View.OnClickListener redStoneEatsWhiteStone = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chGameCond.canEateStone(v);
            }
        };

        View.OnClickListener whiteStoneEatsRedStone = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chGameCond.canEateStone(v);
            }
        };

         */


        //Set the Listener for the redStones
        for (int i = 0; i < redStonesIds.length; i++) {
            for (int j = 0; j < redStonesIds[i].length; j++) {
                View v = findViewById(redStonesIds[i][j]);
                if (v==null){
                    continue;
                }
                redStones[i][j] = v;
                redStones[i][j].setOnClickListener(redStoneClickListener);
            }
        }
        //Set the Listener for the whiteStones
        for (int i = 0; i < whiteStonesIds.length; i++) {
            for (int j = 0; j < redStonesIds[i].length; j++) {
                View v = findViewById(whiteStonesIds[i][j]);
                if (v==null){
                    continue;
                }
                whiteStones[i][j] = v;
                whiteStones[i][j].setOnClickListener(whiteStoneClickListener);
            }
        }


    }

//----------------------------------------------------------------------------------------



    //Dieser Position muss anschliessend ein View.OnClickListener gegeben werden
    private void ShowThePositionAfterEatingStone(int col, int row){
        positions[row][col].setBackgroundColor(Color.GREEN);
        positions[row][col].setOnClickListener(moveListenerForWhiteStone);
    }

    private void removeStone(int col, int row){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

@SuppressLint("ResourceAsColor")
private void clearBoard(){
        for(int i=0; i<positions.length; i++){
            for(int j=0; j<positions[i].length; j++){
                if(i%2==0){
                    if(j%2==0){
                        positions[i][j].setBackgroundColor(Color.BLACK);
                    }else{
                        positions[i][j].setBackgroundColor(Color.GRAY);
                    }
                }else{
                    if(j%2!=0){
                        positions[i][j].setBackgroundColor(Color.BLACK);
                    }else{
                        positions[i][j].setBackgroundColor(Color.GRAY);

                    }
                }
            }
        }

}

    @SuppressLint("ResourceAsColor")
    public int[] showValidPositionsForRedStones(View v){
        clearBoard();
        boolean posIsBlocked;
        int id = v.getId();
        int row =0;
        int col = 0;
        View stone = null;
        for(int i=0; i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                if(stones[i][j]==id){
                    id = stones[i][j];
                    movingStone = findViewById(id);
                    row = i;
                    col = j;
                    i=stones.length-1;
                    break;
                }
            }
        }

        //Index for the Array which contains the positions where the stone can move
        int z=0;
        //Help index
        int k=0;
        for(int i=row-1; i>row-2; i--){
                for(int j=col-1; j<col+2; j++){
                    if(j==col){
                        continue;
                    }
                    if(j<0 || j>7 || i<0 || i>7){
                        continue;
                    }
                    id = positionsIds[i][j];
                    helpPositions[k]=id;
                    k++;
                    View validPos = findViewById(id);
                    posIsBlocked=checkIfStoneIsBlockingPos(j,i);
                    if(!posIsBlocked){
                        validPos.setBackgroundColor(Color.GREEN);
                        validPositionsToMove[z]=positionsIds[i][j];
                        z++;
                    }
                }
            }
        //Set listener for the move of the Stones
        setMoveListenerRedStone(validPositionsToMove);
        return helpPositions;
        }


    @SuppressLint("ResourceAsColor")
    public void showValidPositionsForWhiteStones(View v){
        clearBoard();
        boolean posIsBlocked = false;
        int id = v.getId();
        int row =0;
        int col = 0;
        View stone = null;
        for(int i=0; i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                if(stones[i][j]==id){
                    id = stones[i][j];
                    movingStone = findViewById(id);
                    row = i;
                    col = j;
                    i=stones.length-1;
                    break;
                }
            }
        }
        int z=0;
        for(int i=row+1; i<row+2; i++){
            for(int j=col-1; j<col+2; j++){
                if(j==col){
                    continue;
                }
                if(j<0 || j>7 || i<0 || i>7){
                    continue;
                }
                id = positionsIds[i][j];
                View validPos = findViewById(id);
                posIsBlocked=checkIfStoneIsBlockingPos(j,i);
                if(!posIsBlocked){
                    validPos.setBackgroundColor(Color.GREEN);
                    validPositionsToMove[z]=positionsIds[i][j];
                    z++;
                }

            }
        }
        setMoveListenerWhiteStone(validPositionsToMove);
    }


    public void removeWhiteStone(View stone){
            gameLayout.removeView(stone);
    }
    public void removeRedStone(View stone){
        gameLayout.removeView(stone);
    }



    //Eventuell auslagern
    View.OnClickListener moveListenerForRedStone = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Clicked");
            moveRedStone(v);
        }
    };
    //Eventuell auslagern
    View.OnClickListener moveListenerForWhiteStone = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("Clicked");
            moveWhiteStone(v);
        }
    };


    private void setMoveListenerRedStone(int[]positionsToMove){
        for(int i=0; i<positionsToMove.length;i++){
            int id = positionsToMove[i];
            View posToMove = findViewById(id);
            posToMove.setOnClickListener(moveListenerForRedStone);
        }

    }

    private void setMoveListenerWhiteStone(int[]positionsToMove){
        for(int i=0; i<positionsToMove.length;i++){
            int id = positionsToMove[i];
            View posToMove = findViewById(id);
            posToMove.setOnClickListener(moveListenerForWhiteStone);
        }
    }


    //Muss mit Touch Event erledigt werden
    public void moveRedStone(View view){
        if(movingStone.getY()<view.getY()) {
            clearBoard();
            float diffX = view.getX() - movingStone.getX();
            float diffY = view.getY() - movingStone.getY();
            movingStone.animate()
                    .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                    .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                    .start();

            switchPosOfStoneInArray(movingStone, view);
        }
    }

    //If the Stones move, we need to change the index in the stone-Array
    public void moveWhiteStone(View view){
        if(movingStone.getY()>view.getY()) {
            clearBoard();
            float diffX = view.getX() - movingStone.getX();
            float diffY = view.getY() - movingStone.getY();
            movingStone.animate()
                    .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                    .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                    .start();
            if (redStoneToEat != null) {
                if (redStoneToEat[0] != 0 && redStoneToEat[1] != 0) {
                    int id = stones[redStoneToEat[0]][redStoneToEat[1]];
                    View stone = findViewById(id);
                    gameLayout.removeView(stone);
                    chGameCond.removeStone(redStoneToEat[1], redStoneToEat[0]);
                }
                if (redStoneToEat[0] != 0 && redStoneToEat[1] != 0) {
                    int id = stones[redStoneToEat[2]][redStoneToEat[3]];
                    View stone = findViewById(id);
                    gameLayout.removeView(stone);
                }
            }

            switchPosOfStoneInArray(movingStone, view);
        }
    }

    public void switchPosOfStoneInArray(View stone, View pos){
        int idStone = stone.getId();
        int idPos = pos.getId();
        int oldCol=0;
        int oldRow=0;
        int temp =0;
        for(int i=0; i<positionsIds.length; i++){
            for(int j=0; j<positionsIds[i].length; j++){
                if(stones[i][j]==idStone){
                    oldCol=j;
                    oldRow=i;
                    System.out.println("Alte Spalte:"+oldCol+" "+"Alte Reihe"+oldRow);
                    stones[oldRow][oldCol]=0;
                }
                //Now we got the stone and we need to change the index
                if(positionsIds[i][j]==idPos){
                    stones[i][j]=idStone;
                    System.out.println("Row:"+i+"Col:"+j);
                }
            }
        }

    }

    public boolean checkIfStoneIsBlockingPos(int col, int row){
        int id = stones[row][col];
        View containingStone = findViewById(id);
        if(containingStone==null || stones[row][col]==0){
            return false;
        }else{
            return true;
        }

    }

    //This Method returns true when a player finished his turn
    public boolean update(){

        return true;
    }


    public void printField(){


        for(int i=0; i<stones.length;i++){
            for(int j=0; j<stones[i].length; j++){
                System.out.println(stones[i][j]);
            }
            System.out.println();
        }

    }

}


