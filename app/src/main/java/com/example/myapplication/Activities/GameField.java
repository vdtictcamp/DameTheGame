package com.example.myapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Firebase.FirebaseGameController;
import com.example.myapplication.GameEngine.ChangeGameConditionRedStone;
import com.example.myapplication.GameEngine.ChangeGameConditionWhiteStone;
import com.example.myapplication.GameEngine.CheckIfGameIsFinish;
import com.example.myapplication.GameEngine.GameController;
import com.example.myapplication.PlayerController.Controller;
import com.example.myapplication.Queen.QueenChecker;
import com.example.myapplication.Queen.RedQueen;
import com.example.myapplication.Queen.WhiteQueen;
import com.example.myapplication.R;
import com.example.myapplication.Threads.PlayerOneThread;
import com.example.myapplication.Threads.PlayerTwoThread;
import com.example.myapplication.Threads.TimeThread;

import java.util.ArrayList;
import java.util.List;

public class GameField extends AppCompatActivity{

    public GridLayout gameLayout;
    private static int[][]positionsIds;
    private int[][]whiteStonesIds;
    private int[][]redStonesIds;

    static int[][] stones;

    private View[][] redStones;
    private View[][]whiteStones;
    private static View[][]positions;
    private int[] validPositionsToMove=new int[2];
    private List<View>validPosToMove = new ArrayList<>();
    public View movingStone;
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
    private List<List<Integer>>posForRedQueen=new ArrayList<>();
    private List<List<Integer>>posForWhiteQueen=new ArrayList<>();
    List<Integer>allPositionsToJump = new ArrayList<>();
    private boolean timeLimit;
    private TimeThread timer;
    private PlayerOneThread pOneThread;
    private PlayerTwoThread pTwoThread;
    private View visualizeTurnOfPlayerOne;
    private View visualizeTurnOfPlayerTwo;
    private Controller controller;
    private FirebaseGameController firebase;
    private GameController gameController;
    private QueenChecker queenChecker;
    boolean sentData=false;

    //We need thie variables to controll the turns of the player
    private String gameName =" ";
    private String player=" ";
    final int WHITETURN = 1;
    final int REDTURN =2;
    int TURN =WHITETURN;
    EditText countdown;
    Animation onClickAnim;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        gameLayout = findViewById(R.id.gamelayout);
        countdown = findViewById(R.id.countdown);
        redStones = new View[8][8];
        whiteStones = new View[8][8];
        positions = new View[8][8];
        visualizeTurnOfPlayerOne=findViewById(R.id.playersOneTurn);
        visualizeTurnOfPlayerTwo=findViewById(R.id.playersTwoTurn);
        controller=new Controller();
        gameController=new GameController(positionsIds);
        Intent intent= getIntent();
        gameName = intent.getExtras().getString("gameName");
        player = intent.getExtras().getString("Player");
        if (player==null){
            player="Offline";
        }

        //Load the Animation
        onClickAnim=AnimationUtils.loadAnimation(this, R.anim.clicked);
        //All position
        positionsIds = new int[][]{{R.id.pos1a, R.id.pos1b, R.id.pos1c, R.id.pos1d, R.id.pos1e, R.id.pos1f, R.id.pos1g, R.id.pos1h},
                {R.id.pos2a, R.id.pos2b, R.id.pos2c, R.id.pos2d, R.id.pos2e, R.id.pos2f, R.id.pos2g, R.id.pos2h},
                {R.id.pos3a, R.id.pos3b, R.id.pos3c, R.id.pos3d, R.id.pos3e, R.id.pos3f, R.id.pos3g, R.id.pos3h},
                {R.id.pos4a, R.id.pos4b, R.id.pos4c, R.id.pos4d, R.id.pos4e, R.id.pos4f, R.id.pos4g, R.id.pos4h},
                {R.id.pos5a, R.id.pos5b, R.id.pos5c, R.id.pos5d, R.id.pos5e, R.id.pos5f, R.id.pos5g, R.id.pos5h},
                {R.id.pos6a, R.id.pos6b, R.id.pos6c, R.id.pos6d, R.id.pos6e, R.id.pos6f, R.id.pos6g, R.id.pos6h},
                {R.id.pos7a, R.id.pos7b, R.id.pos7c, R.id.pos7d, R.id.pos7e, R.id.pos7f, R.id.pos7g, R.id.pos7h},
                {R.id.pos8a, R.id.pos8b, R.id.pos8c, R.id.pos8d, R.id.pos8e, R.id.pos8f, R.id.pos8g, R.id.pos8h}};

        //The distrubution of the Stones on the Board
        stones = new int[][]{{R.id.w1_2, 0, R.id.w2_2, 0, R.id.w3, 0, R.id.w4, 0},
                { 0, R.id.w5, 0, R.id.w6, 0, R.id.w7, 0, R.id.w8},
                {0, R.id.w9_2, 0, R.id.w10, 0, R.id.w11, 0, R.id.w12},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0,R.id.b9, 0,R.id.b10, 0,R.id.b11, 0,R.id.b12},
                {R.id.b5,0, R.id.b6,0, R.id.b7,0, R.id.b8,0},
                {0,R.id.b1, 0,R.id.b2, 0,R.id.b3, 0,R.id.b4}};

        //All white stones
        whiteStonesIds = new int[][]{{R.id.w1_2, R.id.w2_2, R.id.w3, R.id.w4},
                {R.id.w5, R.id.w6, R.id.w7, R.id.w8},
                {R.id.w9_2, R.id.w10,  R.id.w11, R.id.w12, },};

        //All red stones
        redStonesIds = new int[][]{{R.id.b1, R.id.b2, R.id.b3, R.id.b4},
                {R.id.b5, R.id.b6, R.id.b7, R.id.b8},
                {R.id.b9, R.id.b10, R.id.b11, R.id.b12},};


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

        clearBoard();


        queenChecker=new QueenChecker(positionsIds);

        View.OnClickListener redStoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingStone=v;
                chGameCondRed = new ChangeGameConditionRedStone(stones, positionsIds, redStonesIds);
                redQueen=new RedQueen(v,redQueens, positionsIds, stones, redStonesIds);
                if((TURN & REDTURN)!=0 && player.equals("PlayerTwo")) {
                    v.startAnimation(onClickAnim);
                    if(redQueen.checkIfIsQueen(v)){
                        int[]index = redQueen.getRowAndCol(v);
                        int row=index[0];
                        int col = index[1];
                        List<Integer> positionsForRedQueen=queenChecker.getPositionsToMove(stones, row, col);
                        showValidPosForQueen(positionsForRedQueen);
                        redQueen.getPositionsToJumpForwardRight(row, col);
                        redQueen.getPositionsToJumpForwardLeft(row, col);
                        redQueen.getPositionsToJumpBackwardRight(row, col);
                        redQueen.getPositionsToJumpBackwardLeft(row, col);
                        posForRedQueen=redQueen.returnPostions();
                        if(posForRedQueen.size()<=0){
                            System.out.println("Keine Steine zum fressen");
                            //collect positions to conduct a normal step without eating a enemy stone
                            //if the stone can make a normal move shoew this available positions
                        }else{
                            for(int i=0; i<posForRedQueen.size(); i++) {
                                showValidPosForQueen(posForRedQueen.get(i));
                            }
                        }
                    }
                    else {
                        showValidPositionsForRedStones(v);
                        posAfterEat = chGameCondRed.canEateWhiteStone(v);
                        whiteStonesToEat = chGameCondRed.returnStonesToEat();
                        if(posAfterEat!=null) {
                            allPositionsToJump = gameController.fillPositionsToJumpInList(posAfterEat);
                        }
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

        View.OnClickListener whiteStoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movingStone=v;
                chGameCondWhite = new ChangeGameConditionWhiteStone( stones, positionsIds, whiteStonesIds);
                whiteQueen=new WhiteQueen(whiteQueens, positionsIds, stones, whiteStonesIds);
                //Checks whoms Turn it is
                if ((TURN & WHITETURN) != 0 && player.equals("PlayerOne")) {
                    v.startAnimation(onClickAnim);
                    if(whiteQueen.checkIfIsQueen(v)){
                        System.out.println("Es ist eine Königin"+posForWhiteQueen.size());
                        int[]index = whiteQueen.getRowAndCol(v);
                        int row=index[0];
                        int col = index[1];
                        List<Integer>positionForWhiteQueen = queenChecker.getPositionsToMove(stones, row, col);
                        showValidPosForQueen(positionForWhiteQueen);
                        System.out.println("Positionen zum bewegen:"+positionForWhiteQueen.size());
                        whiteQueen.getPositionsToJumpForwardRight(row, col);
                        whiteQueen.getPositionsToJumpForwardLeft(row, col);
                        whiteQueen.getPositionsToJumpBackwardLeft(row, col);
                        whiteQueen.getPositionsToJumpBackwardRight(row, col);
                        posForWhiteQueen=whiteQueen.returnPostions();
                        if(posForWhiteQueen.size()<=0) {
                            System.out.println("Nichts zu fressen");
                            System.out.println("Anzahl Positionen für die Königin" + posForWhiteQueen.size());

                        }else{
                            for(int i=0; i<posForWhiteQueen.size(); i++) {
                                showValidPosForQueen(posForWhiteQueen.get(i));
                            }
                        }
                    }else {
                        showValidPositionsForWhiteStones(v);
                        posAfterEat = chGameCondWhite.canEateRedStone(v);
                        redStonesToEat = chGameCondWhite.returnStonesToEat();
                        if(posAfterEat!=null) {
                            allPositionsToJump = gameController.fillPositionsToJumpInList(posAfterEat);
                        }
                        if (posAfterEat != null) {
                            for (int i = 0; i < posAfterEat.size(); i++) {
                                for (int j = 0; j < posAfterEat.get(i).size(); j++) {
                                    int id = posAfterEat.get(i).get(j);
                                    ShowThePositionAfterEatingRedStone(id);
                                }

                            }
                        }
                    }
                   // redStoneToEat = chGameCondWhite.redStoneToEat(v);
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

        visualizeTurnOfPlayerTwo.setBackgroundColor(Color.WHITE);
        //The Game starts

        firebase = new FirebaseGameController(stones, gameName);
        firebase.initStartSituation(player);

        pTwoThread = new PlayerTwoThread(stones, gameName, this);
        pOneThread=new PlayerOneThread(stones, gameName, this);


        if(player.equals("PlayerTwo")){
            pTwoThread.start();
        }
        else if(player.equals("PlayerOne")){
            pOneThread.start();
        }
        else{
            timer = new TimeThread(this, countdown);
            timer.start();
        }
    }

//----------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }



    //Helper function that fills all Positions to jum in one List, which we need for controll if the
    //moves stones only to valid positions

    public void connectionSuccessfull(){
        Toast.makeText(GameField.this, "Spieler zwei ist erfolgreich dem Spiel beigetreten",Toast.LENGTH_LONG).show();
    }
    public void waitingForConnection(){

    }

    public void moveHelperFunc(final int stoneCol, final int stoneRow, final int posRow, final int posCol){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View position = findViewById(positionsIds[posRow][posCol]);
                View stone = findViewById(stones[stoneRow][stoneCol]);
                movingStone=stone;
                float diffX=0;
                float diffY=0;
                System.out.println("Der Stein:"+stone+" "+"die Position:"+position);
                diffX = position.getX() - movingStone.getX();
                diffY = position.getY() - movingStone.getY();
                movingStone.animate()
                        .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                        .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                        .start();
                stones= gameController.switchPosOfStoneInArray(stones, positionsIds, stone.getId(), position.getId());
                if(TURN==WHITETURN){
                    firebase.finishPlayerOneTurn();
                    firebase.setPlayerTwoTurn();
                    TURN=REDTURN;
                }else{
                    TURN=WHITETURN;
                    firebase.setPlayerOneTurn();
                    firebase.finishPlayerTwoTurn();
                }

            }
        });

    }

    public void stopGame(){
        //Display the 'The End Game' Activity
        Intent intent = new Intent(this, EndOfGameActivity.class);
        startActivity(intent);
    }


    //Dieser Position muss anschliessend ein View.OnClickListener gegeben werden
    private void ShowThePositionAfterEatingRedStone(int id){
        View position = findViewById(id);
        position.setBackgroundColor(Color.GREEN);
        position.setOnClickListener(moveListenerForWhiteStone);
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
        position.setOnClickListener(queenMover);
        position.setBackgroundColor(Color.GREEN);

    }

}

    @SuppressLint("ResourceAsColor")
    public void showValidPositionsForRedStones(View v){
        validPosToMove=new ArrayList<>();
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
                        validPosToMove.add(validPos);
                        validPositionsToMove[z]=positionsIds[i][j];
                        System.out.println(validPositionsToMove.length);
                        z++;
                    }
                }
            }
        //Set listener for the move of the Stones
        setMoveListenerRedStone(validPositionsToMove);
        }


    @SuppressLint("ResourceAsColor")
    public void showValidPositionsForWhiteStones(View v){
        clearBoard();
        validPosToMove=new ArrayList<>();

        boolean posIsBlocked = false;
        int id = v.getId();
        int row =0;
        int col = 0;
        View stone = null;
        for(int i=0; i<stones.length; i++){
            for(int j=0; j<stones[i].length; j++){
                if(stones[i][j]==id){
                    System.out.println("Dieser Stein liegt in Spalte"+ j +" "+ "und zeile"+" "+i);
                    id = stones[i][j];
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
                if(j==col || j<0 || j>7 || i<0 || i>7){
                    continue;
                }
                id = positionsIds[i][j];
                View validPos = findViewById(id);
                posIsBlocked=checkIfStoneIsBlockingPos(j,i);
                if(!posIsBlocked){
                    validPos.setBackgroundColor(Color.GREEN);
                    validPosToMove.add(validPos);
                    validPositionsToMove[z]=id;
                    z++;
                }

            }
        }
        setMoveListenerWhiteStone(validPositionsToMove);
    }

    //On click the Queen is moving
    View.OnClickListener queenMover = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moveQueen(v);
        }
    };


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


    private void setMoveListenerRedStone(int[]positionsToMove){
        if (positionsToMove[0]!=0) {
            for (int i = 0; i < positionsToMove.length; i++) {
                int id = positionsToMove[i];
                View posToMove = findViewById(id);
                posToMove.setOnClickListener(moveListenerForRedStone);
            }
        }

    }

    private void setMoveListenerWhiteStone(int[]positionsToMove){
       if(positionsToMove[0]!=0) {
           for (int i = 0; i < positionsToMove.length; i++) {
               int id = positionsToMove[i];
               View posToMove = findViewById(id);
               if (positionsToMove != null) {
                   posToMove.setOnClickListener(moveListenerForWhiteStone);
               }
           }
       }
    }
    //Muss mit Touch Event erledigt werden
    public void moveRedStone(View view){
        if((TURN & REDTURN)!=0 ){
        if(movingStone.getY()<view.getY() && validPosToMove.contains(view)|| allPositionsToJump.contains(view.getId())) {
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
                                removeStone(k, j);

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
                TURN=WHITETURN;

        }
            }
        }

    //If the Stones move, we need to change the index in the stone-Array
    public void moveWhiteStone(View view){
        int posId = view.getId();
        int stoneId = movingStone.getId();
        float diffX=0;
        float diffY=0;
        int rowPos = 0;
        int colPos = 0;
        int rowStone = 0;
        int colStone = 0;
        if((TURN & WHITETURN)!=0) {
            if (movingStone.getY() > view.getY() && validPosToMove.contains(view)|| allPositionsToJump.contains(view.getId())) {
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
                                    removeStone(k, j);
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
                    TURN=REDTURN;

            }

            }
        }

    public void moveQueen(View position){
        float diffX = position.getX() - movingStone.getX();
        float diffY = position.getY() - movingStone.getY();
        movingStone.animate()
                .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                .start();
        switchPosOfStoneInArray(movingStone, position);

        if(TURN==WHITETURN){
            TURN = REDTURN;
        }else {
            TURN=WHITETURN;
        }

    }


    public void removeStone(int row, int col){
        View v = findViewById(stones[row][col]);
        gameLayout.removeView(v);
        stones[row][col]=0;

    }


    public void switchPosOfStoneInArray(View stone, View pos){
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
                    System.out.println("Alte Spalte:"+oldCol+" "+"Alte Reihe"+oldRow);
                }
                //Now we got the stone and we need to change the index
                if(positionsIds[i][j]==idPos){
                    stones[i][j]=idStone;
                    col = j;
                    row = i;
                    System.out.println("Row:"+i+"Col:"+j);
                }
            }
        }
        stones[oldRow][oldCol]=0;
        stones[row][col]=idStone;
        firebase.sendUpdateInformaions(oldRow, oldCol, row, col);
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


}


