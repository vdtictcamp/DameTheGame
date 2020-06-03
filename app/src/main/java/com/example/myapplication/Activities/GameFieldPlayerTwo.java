package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.example.myapplication.Firebase.FirebaseGameController;
import com.example.myapplication.GameEngine.ChangeGameConditionRedStone;
import com.example.myapplication.GameEngine.ChangeGameConditionWhiteStone;
import com.example.myapplication.GameEngine.CheckIfGameIsFinish;
import com.example.myapplication.GameEngine.GameController;
import com.example.myapplication.PlayerController.Controller;
import com.example.myapplication.Queen.RedQueen;
import com.example.myapplication.Queen.WhiteQueen;
import com.example.myapplication.R;
import com.example.myapplication.Threads.PlayerOneThread;
import com.example.myapplication.Threads.PlayerTwoThread;
import com.example.myapplication.Threads.TimeThread;

import java.util.ArrayList;
import java.util.List;

public class GameFieldPlayerTwo extends AppCompatActivity {

        public GridLayout gameLayout;
        private int[][]positionsIds = new int[8][8];
        private int[][]whiteStonesIds;
        private int[][]redStonesIds;
        private int[][] stones = new int[8][8];
        private View[][] redStones;
        private View[][]whiteStones;
        private View[][]positions;
        private int[] validPositionsToMove=new int[2];
        public View movingStone=null;
        private ChangeGameConditionWhiteStone chGameCondWhite;
        private ChangeGameConditionRedStone chGameCondRed;
        private CheckIfGameIsFinish finishChecker = new CheckIfGameIsFinish();
        private List<List<Integer>> posAfterEat = new ArrayList<>();
        private WhiteQueen whiteQueen;
        private RedQueen redQueen;
        private List<Integer>whiteStonesToEat = new ArrayList<>();
        private List<Integer>redStonesToEat = new ArrayList<>();
        private List<Integer>whiteQueens = new ArrayList<>();
        private List<Integer>redQueens = new ArrayList<>();
        private List<Integer>posForRedQueen=new ArrayList<>();
        private List<Integer>posForWhiteQueen=new ArrayList<>();
        private boolean timeLimit;
        private TimeThread timer;
        private PlayerTwoThread pTwoThread;
        private View visualizeTurnOfPlayerOne;
        private View visualizeTurnOfPlayerTwo;
        private Controller controller;
        private FirebaseGameController firebase;
        private GameController gameController;
        boolean sentData=false;

        //We need thie variables to controll the turns of the player
        private String gameName;
        final int WHITETURN = 1;
        final int REDTURN =2;
        int TURN =WHITETURN;
        EditText countdown;


        @SuppressLint("ResourceAsColor")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game_field_player_two);
            gameLayout = findViewById(R.id.gamelayout);
            countdown = findViewById(R.id.countdown);
            redStones = new View[8][8];
            whiteStones = new View[8][8];
            positions = new View[8][8];
            stones = new int[8][8];
            positionsIds = new int[8][8];
            visualizeTurnOfPlayerOne=findViewById(R.id.playersOneTurn);
            visualizeTurnOfPlayerTwo=findViewById(R.id.playersTwoTurn);
            controller=new Controller();
            Intent intent=getIntent();
            gameName = intent.getExtras().getString("gameName");
            System.out.println(gameName);
            //All position
            positionsIds = new int[][]{{R.id.pos1a, R.id.pos1b, R.id.pos1c, R.id.pos1d, R.id.pos1e, R.id.pos1f, R.id.pos1g, R.id.pos1h},
                    {R.id.pos2a, R.id.pos2b, R.id.pos2c, R.id.pos2d, R.id.pos2e, R.id.pos2f, R.id.pos2g, R.id.pos2h},
                    {R.id.pos3a, R.id.pos3b, R.id.pos3c, R.id.pos3d, R.id.pos3e, R.id.pos3f, R.id.pos3g, R.id.pos3h},
                    {R.id.pos4a, R.id.pos4b, R.id.pos4c, R.id.pos4d, R.id.pos4e, R.id.pos4f, R.id.pos4g, R.id.pos4h},
                    {R.id.pos5a, R.id.pos5b, R.id.pos5c, R.id.pos5d, R.id.pos5e, R.id.pos5f, R.id.pos5g, R.id.pos5h},
                    {R.id.pos6a, R.id.pos6b, R.id.pos6c, R.id.pos6d, R.id.pos6e, R.id.pos6f, R.id.pos6g, R.id.pos6h},
                    {R.id.pos7a, R.id.pos7b, R.id.pos7c, R.id.pos7d, R.id.pos7e, R.id.pos7f, R.id.pos7g, R.id.pos7h},
                    {R.id.pos8a, R.id.pos8b, R.id.pos8c, R.id.pos8d, R.id.pos8e, R.id.pos8f, R.id.pos8g, R.id.pos8h}};

            //All white stones
            whiteStonesIds = new int[][]{{R.id.w1_2, R.id.w2_2, R.id.w3, R.id.w4},
                    {R.id.w5, R.id.w6, R.id.w7, R.id.w8},
                    {R.id.w9_2, R.id.w10,  R.id.w11, R.id.w12, },};

            //All red stones
            redStonesIds = new int[][]{{R.id.b1, R.id.b2, R.id.b3, R.id.b4},
                    {R.id.b5, R.id.b6, R.id.b7, R.id.b8},
                    {R.id.b9, R.id.b10, R.id.b11, R.id.b12},};

            //The distrubution of the Stones on the Board
            stones = new int[][]{{R.id.w1_2, 0, R.id.w2_2, 0, R.id.w3, 0, R.id.w4, 0},
                    { 0, R.id.w5, 0, R.id.w6, 0, R.id.w7, 0, R.id.w8},
                    {R.id.w9_2, 0, R.id.view10, 0, R.id.w11, 0, R.id.w12, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0,R.id.b9, 0,R.id.b10, 0,R.id.b11, 0,R.id.b12},
                    {R.id.b5,0, R.id.b6,0, R.id.b7,0, R.id.b8,0},
                    {0,R.id.b1, 0,R.id.b2, 0,R.id.b3, 0,R.id.b4}};

            View.OnClickListener checkPositions = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0; i<positionsIds.length;i++){
                        for(int j=0; j<positionsIds[i].length; j++){
                            if(positionsIds[i][j]==v.getId()){
                                if(stones[i][j]==0){
                                    System.out.println("Diese Position ist leer");
                                }else{
                                    System.out.println("Position nicht leer");
                                }
                            }
                        }
                    }
                }
            };


            //Fill the Array with all Positions
            for(int i=0; i<positions.length;i++){
                for(int j=0; j<positions[i].length; j++){
                    int id = positionsIds[i][j];
                    View pos = findViewById(id);
                    positions[i][j]=pos;
                    positions[i][j].setOnClickListener(checkPositions);
                }
            }



        System.out.println(stones[2][2]);


            View.OnClickListener redStoneClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chGameCondRed = new ChangeGameConditionRedStone(stones, positionsIds, redStonesIds);
                    redQueen=new RedQueen(v,redQueens, positionsIds, stones, redStonesIds);
                    if((TURN & REDTURN)!=0 ) {
                        if(redQueen.checkIfIsQueen(v)){
                            System.out.println("Es ist eine Königin");
                            //Positions to eat a enemy stone
                            int[]index = redQueen.getRowAndCol(v);
                            int row=index[0];
                            int col = index[1];
                            posForRedQueen=redQueen.getPositionsToJumpForwardRight(row, col);
                            showValidPosForQueen(posForRedQueen);
                            posForRedQueen=redQueen.getPositionsToJumpForwardLeft(row, col);
                            showValidPosForQueen(posForRedQueen);                        //if this list is empty show normal positions to move
                            if(posForRedQueen.size()<=0){
                                System.out.println("Keine Steine zum fressen");
                                //collect positions to conduct a normal step without eating a enemy stone
                                posForRedQueen=redQueen.getPositionsToMove(row, col);
                                System.out.println("Anzahl Positionen für die Königin"+ posForRedQueen.size());
                                //if the stone can make a normal move shoew this available positions
                                if(posForRedQueen.size()>0){
                                    showValidPosForQueen(posForRedQueen);
                                }
                            }
                        }else {
                            showValidPositionsForRedStones(v);
                            posAfterEat = chGameCondRed.canEateWhiteStoneBeta(v);
                            whiteStonesToEat = chGameCondRed.returnStonesToEat();
                            if(posAfterEat!=null){
                                for (int i = 0; i < posAfterEat.size(); i++) {
                                    for (int j = 0; j < posAfterEat.get(i).size(); j++) {
                                        int id = posAfterEat.get(i).get(j);
                                        ShowThePositionAfterEatingWhiteStone(id);
                                    }

                                }
                            }
                        }

                    }

                }

            };


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

            visualizeTurnOfPlayerTwo.setBackgroundColor(Color.WHITE);
            //The Game starts
            //timer = new TimeThread(this, countdown);
            //timer.start();

            firebase = new FirebaseGameController(stones, gameName);
            pTwoThread=new PlayerTwoThread(stones, positionsIds, gameName);
            pTwoThread.start();

        View pos = findViewById(positionsIds[3][1]);
        pos.setBackgroundColor(Color.GREEN);

        movingStone = findViewById(stones[2][2]);

        }

//----------------------------------------------------------------------------------------


    public void helpViewMover(int stonId, int posId){

            System.out.println(stones[2][2]);
            System.out.println("StoneID su helper:"+stonId+ " posId aus helper"+posId);
        moveBeta(stonId, posId);
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        public void stopGame(){
            //Display the 'The End Game' Activity
            Intent intent = new Intent(this, EndOfGameActivity.class);
            startActivity(intent);
        }


        private void ShowThePositionAfterEatingWhiteStone(int id){
            View position = findViewById(id);
            position.setBackgroundColor(Color.GREEN);
            position.setOnClickListener(moveListenerForRedStone);
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


        //This methods shows all available positions for the queen
        public void showValidPosForQueen(List<Integer>positions){
            for(int i=0; i<positions.size(); i++){
                View position = findViewById(positions.get(i));
                position.setBackgroundColor(Color.GREEN);

            }

        }

        @SuppressLint("ResourceAsColor")
        public void showValidPositionsForRedStones(View v){
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
            for(int i=row-1; i>row-2; i--){
                for(int j=col-1; j<col+2; j++){
                    if(j==col || j<0 || j>7 || i<0 || i>7){
                        continue;
                    }
                    id = positionsIds[i][j];
                    View validPos = findViewById(id);
                    posIsBlocked=checkIfStoneIsBlockingPos(j,i);
                    if(!posIsBlocked){
                        validPos.setBackgroundColor(Color.GREEN);
                        validPositionsToMove[z]=positionsIds[i][j];
                        System.out.println(validPositionsToMove.length);
                        z++;
                    }
                }
            }
            //Set listener for the move of the Stones
            setMoveListenerRedStone(validPositionsToMove);
        }

        //Eventuell auslagern
        View.OnClickListener moveListenerForRedStone = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRedStone(v);

            }
        };
        //Eventuell auslagern
        View.OnClickListener moveListenerForWhiteStone = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveWhiteStone(v);
            }
        };


        public void setTurn(){
            TURN = REDTURN;
        }

        private void setMoveListenerRedStone(int[]positionsToMove){
            if (positionsToMove[0]!=0) {
                for (int i = 0; i < positionsToMove.length; i++) {
                    int id = positionsToMove[i];
                    View posToMove = findViewById(id);
                    posToMove.setOnClickListener(moveListenerForRedStone);
                }
            }

        }


        //Muss mit Touch Event erledigt werden
        public void moveRedStone(View view){
            if((TURN & REDTURN)!=0 ){
                if(movingStone.getY()<view.getY()) {
                    clearBoard();
                    float diffX = view.getX() - movingStone.getX();
                    float diffY = view.getY() - movingStone.getY();
                    movingStone.animate()
                            .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                            .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                            .start();

                    switchPosOfStoneInArray(movingStone, view);
                    if (whiteStonesToEat.size() != 0) {
                        for (int i = 0; i < whiteStonesToEat.size(); i++) {
                            int id = whiteStonesToEat.get(i);
                            for (int k = 0; k < stones.length; k++) {
                                for (int j = 0; j < stones[k].length; j++) {
                                    if (stones[k][j] == id) {
                                        eatWhiteStone(k, j);
                                    }
                                }
                            }
                        }

                    }
                }

                boolean isFinish =finishChecker.checkIfGameIsFinish(whiteStonesIds, redStonesIds);
                if(isFinish){
                    stopGame();
                }
                controller.changeTurnOfPlayer(visualizeTurnOfPlayerTwo, visualizeTurnOfPlayerOne);
                TURN = WHITETURN;
            }
        }

        //If the Stones move, we need to change the index in the stone-Array

    public void moveBeta(int stoneId, int positionId){

            System.out.println("aus aray:"+stones[2][2]+" übergeben"+ stoneId);

            for(int i=0; i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                if(stones[i][j]==stoneId){
                    System.out.println("Stein gefunden");
                    System.out.println(i +" "+j);
                }

            }
        }

    }
    public void moveWhiteStone(View view){
            float diffX=0;
            float diffY=0;
            if((TURN&WHITETURN)!=0) {
                if (movingStone.getY() > view.getY()) {
                    clearBoard();
                    diffX = view.getX() - movingStone.getX();
                    diffY = view.getY() - movingStone.getY();
                    movingStone.animate()
                            .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                            .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                            .start();
                    switchPosOfStoneInArray(movingStone, view);
                    if (redStonesToEat.size() != 0) {

                        for (int i = 0; i < redStonesToEat.size(); i++) {
                            int id = redStonesToEat.get(i);
                            for (int k = 0; k < stones.length; k++) {
                                for (int j = 0; j < stones[k].length; j++) {
                                    if (stones[k][j] == id) {
                                        eatRedStone(k, j);
                                    }
                                }
                            }
                        }

                    }
                    boolean isFinish = finishChecker.checkIfGameIsFinish(whiteStonesIds, redStonesIds);

                    if (isFinish) {
                        stopGame();
                    }
                    controller.changeTurnOfPlayer(visualizeTurnOfPlayerOne, visualizeTurnOfPlayerTwo);
                    TURN = REDTURN;
                }
            }

        }


        public void removeStone(int row, int col){
            View v = findViewById(stones[row][col]);
            int id = v.getId();
            gameLayout.removeView(v);
            stones[row][col]=0;

        }

        public void eatWhiteStone(int i, int j){
            View v = findViewById(stones[i][j]);
            int id = v.getId();
            gameLayout.removeView(v);
            stones[i][j]=0;
            for(int k=0; k<whiteStonesIds.length; k++){
                for(int z=0; z<whiteStonesIds[k].length; z++){
                    if(whiteStonesIds[k][z]==id){
                        whiteStonesIds[k][z]=0;
                    }
                }
            }
        }

        public void eatRedStone(int i, int j){
            View v = findViewById(stones[i][j]);
            gameLayout.removeView(v);
            stones[i][j]=0;
            int id = v.getId();
            for(int k=0; k<redStonesIds.length; k++){
                for(int z=0; z<redStonesIds[k].length; z++){
                    if(redStonesIds[k][z]==id){
                        redStonesIds[k][z]=0;
                    }
                }
            }
        }

        public void switchPosOfStoneInArray(View stone, View pos){

            firebase=new FirebaseGameController(stones, gameName);
            int col=0, row =0;
            int idStone = stone.getId();
            int idPos = pos.getId();
            int oldCol=0;
            int oldRow=0;
            for(int i=0; i<positionsIds.length; i++){
                for(int j=0; j<positionsIds[i].length; j++){
                    if(stones[i][j]==idStone){
                        oldCol=j;
                        oldRow=i;
                        sentData = firebase.sendUpdateInformaions(oldRow, oldCol, row, col);

                    }
                    //Now we got the stone and we need to change the index
                    if(positionsIds[i][j]==idPos){
                        stones[i][j]=idStone;
                        col = j;
                        row = i;
                        System.out.println("Row:"+i+"Col:"+j);
                    }
                    stones[oldRow][oldCol]=0;
                }
            }
            sentData = firebase.sendUpdateInformaions(oldRow, oldCol, row, col);



            //Every Time when we switch a Position we need to check if we got a new Queen
            if(row==7){
                int id = stones[row][col];
                whiteQueens.add(id);
                View stoneToQueen = findViewById(id);
                setWhiteQueen(stoneToQueen);
            }
            if(row==0){
                int id = stones[row][col];
                redQueens.add(id);
                View stoneToQueen = findViewById(id);
                setWhiteQueen(stoneToQueen);
                setRedQueen(stoneToQueen);

            }

            //firebase.updateField();

        }


        public boolean returnUpdate(){
            return sentData;
        }
        public void setWhiteQueen(View stoneToQueen ){
            whiteQueen.setQueen(stoneToQueen);
        }
        public void setRedQueen(View stoneToQueen){
            redQueen.setQueen(stoneToQueen);
        }

        public boolean checkIfStoneIsBlockingPos(int col, int row){
            int id = stones[row][col];
            View containingStone = findViewById(id);
            if(stones[row][col]==0){
                return false;
            }else{
                return true;
            }

        }

        //This Method returns true when a player finished his turn
        public boolean update(){

            return true;
        }

    }


