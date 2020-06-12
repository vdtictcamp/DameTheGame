package com.example.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Firebase.FirebaseGameController;
import com.example.myapplication.GameEngine.ChangeGameConditionRedStone;
import com.example.myapplication.GameEngine.ChangeGameConditionWhiteStone;
import com.example.myapplication.GameEngine.CheckIfGameIsFinish;
import com.example.myapplication.GameEngine.GameController;
import com.example.myapplication.PlayerController.Controller;
import com.example.myapplication.Queen.QueenChecker;
import com.example.myapplication.R;
import com.example.myapplication.Threads.PlayerOneThread;
import com.example.myapplication.Threads.PlayerTwoThread;
import com.example.myapplication.Threads.TimeThread;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class localGame extends AppCompatActivity {

    FirebaseAuth currentUserAuth;

    public GridLayout gameLayout;
    private Context context;

    private static int[][] positionsIds;
    private int[][] whiteStonesIds;
    private int[][] redStonesIds;
    static int[][] stones;
    private int[] validPositionsToMove = new int[2];

    private View[][] redStones;
    private View[][] whiteStones;
    private static View[][] positions;
    private List<View> validPosToMove = new ArrayList<>();
    private View visualizeTurnOfPlayerOne;
    private View visualizeTurnOfPlayerTwo;
    private View movingStone;
    private TextView countdown;


    private ChangeGameConditionWhiteStone chGameCondWhite;
    private ChangeGameConditionRedStone chGameCondRed;
    private CheckIfGameIsFinish finishChecker = new CheckIfGameIsFinish();
    private Controller controller;
    private GameController gameController;
    private QueenChecker queenChecker;

    private List<List<Integer>> posAfterEat = new ArrayList<>();
    private List<Integer> whiteStonesToEat = new ArrayList<>();
    private List<Integer> redStonesToEat = new ArrayList<>();
    private List<Integer> whiteQueens = new ArrayList<>();
    private List<Integer> redQueens = new ArrayList<>();
    private List<List<Integer>> posForRedQueen = new ArrayList<>();
    private List<List<Integer>> posForWhiteQueen = new ArrayList<>();
    private List<Integer> allPositionsToJump = new ArrayList<>();
    private List<Integer> positionsToMove_Q = new ArrayList<>();

    private boolean timer;

    //We need thie variables to controll the turns of the player
    private String gameName = " ";
    private String player = " ";

    //These two variables controll the turn of the player
    final int WHITETURN = 1;
    final int REDTURN = 2;

    // At the beginning of the game we set the turn for the player which plays the white stones
    int TURN = WHITETURN;
    //Animation which ist starting when we touch a stone
    private Animation onClickAnim;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_game);
        gameLayout = findViewById(R.id.gamelayout);
        countdown = findViewById(R.id.countdown);
        redStones = new View[8][8];
        whiteStones = new View[8][8];
        positions = new View[8][8];
        visualizeTurnOfPlayerOne = findViewById(R.id.playersOneTurn);
        visualizeTurnOfPlayerTwo = findViewById(R.id.playersTwoTurn);
        countdown=findViewById(R.id.gameCountdown);
        context = this;

        //Identify the User
        currentUserAuth = FirebaseAuth.getInstance();

        TextView lblPlayerOne = findViewById(R.id.lblPlayerOneSwitcher);
        TextView lblPlayerTwo = findViewById(R.id.lblPlayerTwoSwitcher);
        Intent intent = getIntent();

        String gameName = intent.getExtras().getString("gameName");
        String playerOneName = intent.getExtras().getString("playerOneName");
        String playerTwoName = intent.getExtras().getString("playerTwoName");
        lblPlayerOne.setText(playerOneName);
        lblPlayerTwo.setText(playerTwoName);

        player = intent.getExtras().getString("Player");

        //We ask now the GameSettingsActivity if we need to set the timer
        timer=intent.getExtras().getBoolean("Timer");

        //Load the Animation, the Animation helps to identify the stone which has been touched
        onClickAnim = AnimationUtils.loadAnimation(this, R.anim.clicked);

        //All position, the following array represents all positions of the board
        positionsIds = new int[][]{{R.id.pos1a, R.id.pos1b, R.id.pos1c, R.id.pos1d, R.id.pos1e, R.id.pos1f, R.id.pos1g, R.id.pos1h},
                {R.id.pos2a, R.id.pos2b, R.id.pos2c, R.id.pos2d, R.id.pos2e, R.id.pos2f, R.id.pos2g, R.id.pos2h},
                {R.id.pos3a, R.id.pos3b, R.id.pos3c, R.id.pos3d, R.id.pos3e, R.id.pos3f, R.id.pos3g, R.id.pos3h},
                {R.id.pos4a, R.id.pos4b, R.id.pos4c, R.id.pos4d, R.id.pos4e, R.id.pos4f, R.id.pos4g, R.id.pos4h},
                {R.id.pos5a, R.id.pos5b, R.id.pos5c, R.id.pos5d, R.id.pos5e, R.id.pos5f, R.id.pos5g, R.id.pos5h},
                {R.id.pos6a, R.id.pos6b, R.id.pos6c, R.id.pos6d, R.id.pos6e, R.id.pos6f, R.id.pos6g, R.id.pos6h},
                {R.id.pos7a, R.id.pos7b, R.id.pos7c, R.id.pos7d, R.id.pos7e, R.id.pos7f, R.id.pos7g, R.id.pos7h},
                {R.id.pos8a, R.id.pos8b, R.id.pos8c, R.id.pos8d, R.id.pos8e, R.id.pos8f, R.id.pos8g, R.id.pos8h}};

        //The distrubution of the Stones on the Board, this array represents the startsituation of the game after executing the onCreate Method
        stones = new int[][]{{R.id.w1, 0, R.id.w2_2, 0, R.id.w3, 0, R.id.w4, 0},
                {0, R.id.w5, 0, R.id.w6, 0, R.id.w7, 0, R.id.w8},
                {R.id.w9, 0, R.id.w10, 0, R.id.w11, 0, R.id.w12, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, R.id.b9, 0, R.id.b10, 0, R.id.b11, 0, R.id.b12},
                {R.id.b5, 0, R.id.b6, 0, R.id.b7, 0, R.id.b8, 0},
                {0, R.id.b1, 0, R.id.b2, 0, R.id.b3, 0, R.id.b4}};

        //All white stones
        whiteStonesIds = new int[][]{{R.id.w1, R.id.w2_2, R.id.w3, R.id.w4},
                {R.id.w5, R.id.w6, R.id.w7, R.id.w8},
                {R.id.w9, R.id.w10, R.id.w11, R.id.w12}};

        //All red stones
        redStonesIds = new int[][]{{R.id.b1, R.id.b2, R.id.b3, R.id.b4},
                {R.id.b5, R.id.b6, R.id.b7, R.id.b8},
                {R.id.b9, R.id.b10, R.id.b11, R.id.b12}};


        //Fill the Array with all Positions
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                int id = positionsIds[i][j];
                View pos = findViewById(id);
                positions[i][j] = pos;

            }
        }

        //GameController, the game controller controlls the game
        gameController = new GameController(this, positionsIds);

        //QueenChecker
        queenChecker = new QueenChecker(this, positionsIds);

        //PlayerController
        controller = new Controller();

        //We set that the player one starts
        controller.changeTurnOfPlayer(false, true, visualizeTurnOfPlayerOne, visualizeTurnOfPlayerTwo);

        //Initialize the Time Thread, which starts the countdown of 10 minutes
        if(timer){
            TimeThread timerThread = new TimeThread(localGame.this, countdown);
        }
        else {
            countdown.setText(" ");
        }

        //Clears the Board
        clearBoard();

        //This ist the click listener for all red stones
        View.OnClickListener redStoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBoard();
                allPositionsToJump.clear();
                chGameCondRed = new ChangeGameConditionRedStone(context, stones, positionsIds, redStonesIds);
                if ((TURN & REDTURN) != 0) {
                    movingStone = v;
                    v.startAnimation(onClickAnim);
                    //We check if the choosen stone is a queen
                    if (queenChecker.checkIfIsRedQueen(redQueens, v)) {
                        int[] index = queenChecker.getRowAndCol(stones, v);
                        int row = index[0];
                        int col = index[1];
                        queenChecker.getPositionsToJumpForwardRight(stones, row, col, whiteStonesIds, redStonesIds, false);
                        queenChecker.getPositionsToJumpForwardLeft(stones, row, col, whiteStonesIds, redStonesIds, false);
                        queenChecker.getPositionsToJumpBackwardRight(stones, row, col, whiteStonesIds, redStonesIds, false);
                        queenChecker.getPositionsToJumpBackwardLeft(stones, row, col, whiteStonesIds, redStonesIds, false);
                        posForRedQueen = queenChecker.returnPostions();
                        whiteStonesToEat = queenChecker.returnStonesToEat();
                        allPositionsToJump = gameController.fillPositionsToJumpInList(posForRedQueen, true);
                        if (allPositionsToJump.size() > 0){
                            showValidPosForQueen(allPositionsToJump);
                    }
                        else{
                            positionsToMove_Q = queenChecker.getPositionsToMove(stones, row, col);
                            showValidPosForQueen(positionsToMove_Q);
                        }

                    } else {
                        posAfterEat = chGameCondRed.canEateWhiteStone(v);
                        allPositionsToJump = gameController.fillPositionsToJumpInList(posAfterEat, true);
                        whiteStonesToEat = chGameCondRed.returnStonesToEat();
                        if (allPositionsToJump.size()>0) {
                            showThePositionAfterEatingWhiteStone(allPositionsToJump);
                        }else{
                            showValidPositionsForRedStones(v);
                        }
                    }
                }
                posForRedQueen.clear();
            }
        };

        //This is the click listener for all white stones
        View.OnClickListener whiteStoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBoard();
                allPositionsToJump.clear();
                chGameCondWhite = new ChangeGameConditionWhiteStone(context, stones, positionsIds, whiteStonesIds);
                //Checks whoms Turn it is
                if ((TURN & WHITETURN) != 0) {
                    movingStone = v;
                    v.startAnimation(onClickAnim);
                    //We check if the choosen Stone is a queen
                    if (queenChecker.checkIfIsWhiteQueen(whiteQueens, v)) {
                        int[] index = queenChecker.getRowAndCol(stones, v);
                        int row = index[0];
                        int col = index[1];
                        queenChecker.getPositionsToJumpForwardLeft(stones, row, col, whiteStonesIds, redStonesIds, true);
                        queenChecker.getPositionsToJumpBackwardLeft(stones, row, col, whiteStonesIds, redStonesIds, true);
                        queenChecker.getPositionsToJumpBackwardRight(stones, row, col, whiteStonesIds, redStonesIds, true);
                        queenChecker.getPositionsToJumpForwardRight(stones, row, col, whiteStonesIds, redStonesIds, true);
                        posForWhiteQueen = queenChecker.returnPostions();
                        redStonesToEat = queenChecker.returnStonesToEat();
                        allPositionsToJump = gameController.fillPositionsToJumpInList(posForWhiteQueen, true);
                        if(allPositionsToJump.size()>0) {
                            showValidPosForQueen(allPositionsToJump);
                        }else{
                            positionsToMove_Q = queenChecker.getPositionsToMove(stones, row, col);
                            showValidPosForQueen(positionsToMove_Q);
                        }

                    } else {
                        posAfterEat = chGameCondWhite.canEateRedStone(v);
                        allPositionsToJump = gameController.fillPositionsToJumpInList(posAfterEat, true);
                        redStonesToEat = chGameCondWhite.returnStonesToEat();
                        if (allPositionsToJump.size()>0) {
                            showThePositionAfterEatingRedStone(allPositionsToJump);
                        }else{
                            showValidPositionsForWhiteStones(v);
                        }
                    }
                }
                posForWhiteQueen.clear();
            }
        };

        //Set the Listener for the redStones
        for (int i = 0; i < redStonesIds.length; i++) {
            for (int j = 0; j < redStonesIds[i].length; j++) {
                View v = findViewById(redStonesIds[i][j]);
                if (v == null) {
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
                if (v == null) {
                    continue;
                }
                whiteStones[i][j] = v;
                whiteStones[i][j].setOnClickListener(whiteStoneClickListener);
            }
        }
    }

//----------------------------------------------------------------------------------------

    //This method displays the Menu on the Activity
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (currentUserAuth.getCurrentUser() != null) {
            menu.removeItem(R.id.menuLoginItem);
        }
        if (currentUserAuth.getCurrentUser() == null) {
            menu.removeItem(R.id.menuLogoutItem);
            menu.removeItem(R.id.lblAccountMenu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    //This is the selection of the Activities, each elemente has a listener
    //If the User clicks on a menu element he navigates directly to the specified Acitvity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLoginItem:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulesMenuItem:
                intent = new Intent(getApplicationContext(), GameRulesActivity.class);
                startActivity(intent);
                return true;
            case R.id.registerMenuItem:
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                return true;
            case R.id.menuOnlineItem:
                if (currentUserAuth.getCurrentUser() == null) {
                    Toast.makeText(localGame.this, "Um online zu spielen melde dich bitte mit deinem Account an", Toast.LENGTH_LONG).show();

                } else {
                    intent = new Intent(getApplicationContext(), OnlineOptionsActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.menuLogoutItem:
                currentUserAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.lblAccountMenu:
                intent=new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Display the 'The End Game' Activity
    public void stopGame() {
        Intent intent = new Intent(this, EndOfGameActivity.class);
        startActivity(intent);
    }


    //Dieser Position muss anschliessend ein View.OnClickListener gegeben werden
    private void showThePositionAfterEatingRedStone(List<Integer> positions) {
        for (int i = 0; i < positions.size(); i++) {
            int id = positions.get(i);
            View position = findViewById(id);
            position.setBackgroundColor(Color.parseColor("#D2691E"));
            position.setOnClickListener(moveListenerForWhiteStone);

        }
    }

    //This Method shows all
    private void showThePositionAfterEatingWhiteStone(List<Integer> positions) {
        for (int i = 0; i < positions.size(); i++) {
            int id = positions.get(i);
            View position = findViewById(id);
            position.setBackgroundColor(Color.parseColor("#D2691E"));
            position.setOnClickListener(moveListenerForRedStone);

        }
    }

    //This method clears the board
    @SuppressLint("ResourceAsColor")
    private void clearBoard() {
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        positions[i][j].setBackgroundColor(Color.BLACK);
                    } else {
                        positions[i][j].setBackgroundColor(Color.GRAY);
                    }
                } else {
                    if (j % 2 != 0) {
                        positions[i][j].setBackgroundColor(Color.BLACK);
                    } else {
                        positions[i][j].setBackgroundColor(Color.GRAY);

                    }
                }
            }
        }
    }

    //This methods shows all available positions for the queen
    public void showValidPosForQueen(List<Integer> positions) {
        for (int i = 0; i < positions.size(); i++) {
            View position = findViewById(positions.get(i));
            position.setOnClickListener(queenMover);
            position.setBackgroundColor(Color.parseColor("#D2691E"));
        }
    }

    @SuppressLint("ResourceAsColor")
    public void showValidPositionsForRedStones(View v) {
        validPosToMove = new ArrayList<>();
        clearBoard();
        boolean posIsBlocked;
        int id = v.getId();
        int row = 0;
        int col = 0;
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones[i].length; j++) {
                if (stones[i][j] == id) {
                    System.out.println("Dieser Stein liegt in Spalte" + j + " " + "und zeile" + " " + i);
                    id = stones[i][j];
                    row = i;
                    col = j;
                    i = stones.length - 1;
                    break;
                }
            }
        }

        //Index for the Array which contains the positions where the stone can move
        int z = 0;
        //Help index
        for (int i = row - 1; i > row - 2; i--) {
            for (int j = col - 1; j < col + 2; j++) {
                if (j == col || j < 0 || j > 7 || i < 0 || i > 7) {
                    continue;
                }
                id = positionsIds[i][j];
                View validPos = findViewById(id);
                posIsBlocked = gameController.checkIfStoneIsBlockingPos(stones, j, i);
                if (!posIsBlocked) {
                    validPos.setBackgroundColor(Color.parseColor("#D2691E"));
                    validPosToMove.add(validPos);
                    validPositionsToMove[z] = positionsIds[i][j];
                    System.out.println(validPositionsToMove.length);
                    z++;
                }
            }
        }
        //Set listener for the move of the Stones
        setMoveListenerRedStone(validPositionsToMove);
    }


    @SuppressLint("ResourceAsColor")
    public void showValidPositionsForWhiteStones(View v) {
        clearBoard();
        validPosToMove = new ArrayList<>();
        boolean posIsBlocked = false;
        int id = v.getId();
        int row = 0;
        int col = 0;
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0; j < stones[i].length; j++) {
                if (stones[i][j] == id) {
                    System.out.println("Dieser Stein liegt in Spalte" + j + " " + "und zeile" + " " + i);
                    id = stones[i][j];
                    row = i;
                    col = j;
                    i = stones.length - 1;
                    break;
                }
            }
        }
        int z = 0;
        for (int i = row + 1; i < row + 2; i++) {
            for (int j = col - 1; j < col + 2; j++) {
                if (j == col || j < 0 || j > 7 || i < 0 || i > 7) {
                    continue;
                }
                id = positionsIds[i][j];
                View validPos = findViewById(id);
                posIsBlocked = gameController.checkIfStoneIsBlockingPos(stones, j, i);
                if (!posIsBlocked) {
                    validPos.setBackgroundColor(Color.parseColor("#D2691E"));
                    validPosToMove.add(validPos);
                    validPositionsToMove[z] = id;
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
    View.OnClickListener moveListenerForRedStone = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moveRedStone(v);

        }
    };
    View.OnClickListener moveListenerForWhiteStone = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            moveWhiteStone(v);
        }
    };

    //Sets the click listener to the valid positions for the movements of the Red Stone
    private void setMoveListenerRedStone(int[] positionsToMove) {
        if (positionsToMove != null) {
            for (int i = 0; i < positionsToMove.length; i++) {
                int id = positionsToMove[i];
                if (positionsToMove[i] != 0) {
                    View posToMove = findViewById(id);
                    posToMove.setOnClickListener(moveListenerForRedStone);
                }
            }
        }
    }

    //Sets the click listener to the positions which are not blocked and therefore available for a movemente
    private void setMoveListenerWhiteStone(int[] positionsToMove) {
        if (positionsToMove != null) {
            for (int i = 0; i < positionsToMove.length; i++) {
                int id = positionsToMove[i];
                if (positionsToMove[i] != 0) {
                    View posToMove = findViewById(id);
                    posToMove.setOnClickListener(moveListenerForWhiteStone);
                }
            }
        }
    }

    //This Method moves the red stone after choosen a destination position, the stone moves only if the position is not blocked of with another stone
    public void moveRedStone(View view) {
        int posId = view.getId();
        int stoneId = movingStone.getId();
        if ((TURN & REDTURN) != 0) {
            if (movingStone.getY() < view.getY() && validPosToMove.contains(view) || allPositionsToJump.contains(view.getId())) {
                clearBoard();
                float diffX = view.getX() - movingStone.getX();
                float diffY = view.getY() - movingStone.getY();
                movingStone.animate()
                        .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                        .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                        .start();
                if (whiteStonesToEat.size() > 0) {
                    gameController.removeStones(stones, allPositionsToJump, stoneId, posId);
                }
                boolean isFinish = finishChecker.checkIfGameIsFinish(whiteStonesIds, redStonesIds);
                if (isFinish) {
                    stopGame();
                }
                //After each move we need to change the position of the stone in the stone array
                stones = gameController.switchPosOfStoneInArray(stones, positionsIds, movingStone.getId(), view.getId());

                //Controller visualizes the Turn of the Player
                controller.changeTurnOfPlayer(true, false, visualizeTurnOfPlayerTwo, visualizeTurnOfPlayerOne);

                //Gets the Column and Row of the destination Position
                int[] index = gameController.getChoosenPositionToJump(stones, view.getId());
                //If a red stone has reached the lowest row he's transforming himself into a queen
                if (index[0] == 0) {
                    int id = stones[index[0]][index[1]];
                    redQueens.add(id);
                    View stoneToQueen = findViewById(id);
                    queenChecker.setRedQueen(stoneToQueen);
                }
                controller.changeTurnOfPlayer(true, false, visualizeTurnOfPlayerTwo, visualizeTurnOfPlayerOne);

                TURN = WHITETURN;
                allPositionsToJump.clear();
            }

        }
    }

    //This Method moves the red stone after choosen a destination position, the stone moves only if the position is not blocked of with another stone
    public void moveWhiteStone(View view) {
        int posId = view.getId();
        int stoneId = movingStone.getId();
        float diffX = 0;
        float diffY = 0;
        if ((TURN & WHITETURN) != 0) {
            if (movingStone.getY() > view.getY() && validPosToMove.contains(view) || allPositionsToJump.contains(view.getId())) {
                clearBoard();
                diffX = view.getX() - movingStone.getX();
                diffY = view.getY() - movingStone.getY();
                movingStone.animate()
                        .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                        .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                        .start();
                if (redStonesToEat.size() > 0) {
                    gameController.removeStones(stones, allPositionsToJump, stoneId, posId);
                }
                boolean isFinish = finishChecker.checkIfGameIsFinish(whiteStonesIds, redStonesIds);
                if (isFinish) {
                    stopGame();
                }
                //After each move we need to change the position of the stone in the stone array
                stones = gameController.switchPosOfStoneInArray(stones, positionsIds, movingStone.getId(), view.getId());

                //Thie line of code let's appear the view which visualize the turn of the player
                controller.changeTurnOfPlayer(false, true, visualizeTurnOfPlayerTwo, visualizeTurnOfPlayerOne);

                int[] index = gameController.getChoosenPositionToJump(stones, view.getId());
                //If a white stone has reached the seventh row, its transforming itself to a queen
                if (index[0] == 7) {
                    int id = stones[index[0]][index[1]];
                    whiteQueens.add(id);
                    View stoneToQueen = findViewById(id);
                    queenChecker.setWhiteQueen(stoneToQueen);
                }
                controller.changeTurnOfPlayer(false, true, visualizeTurnOfPlayerTwo, visualizeTurnOfPlayerOne);
                TURN = REDTURN;
                allPositionsToJump.clear();
            }
        }
    }
    //This method moves the Queen
    public void moveQueen(View position) {
        float diffX = position.getX() - movingStone.getX();
        float diffY = position.getY() - movingStone.getY();
        clearBoard();
        if (allPositionsToJump.contains(position.getId()) || positionsToMove_Q.contains(position.getId())) {
            movingStone.animate()
                    .x(movingStone.getX() + diffX + (movingStone.getWidth() / 2))
                    .y(movingStone.getY() + diffY + (movingStone.getHeight() / 2))
                    .start();
            if (whiteQueens.contains(movingStone.getId())) {
                if (redStonesToEat.size() > 0) {
                    gameController.removeStonesQueen(stones, allPositionsToJump, movingStone.getId(), position.getId());
                }
            } else {
                if (whiteStonesToEat.size() > 0) {
                    gameController.removeStonesQueen(stones, allPositionsToJump, movingStone.getId(), position.getId());
                }
            }
            boolean isFinish = finishChecker.checkIfGameIsFinish(whiteStonesIds, redStonesIds);
            if (isFinish) {
                stopGame();
            }
            stones = gameController.switchPosOfStoneInArray(stones, positionsIds, movingStone.getId(), position.getId());
            if (TURN == WHITETURN) {
                controller.changeTurnOfPlayer(false, true, visualizeTurnOfPlayerTwo, visualizeTurnOfPlayerOne);
                TURN = REDTURN;
            } else {
                controller.changeTurnOfPlayer(true, false, visualizeTurnOfPlayerTwo, visualizeTurnOfPlayerOne);
                TURN = WHITETURN;
            }
            allPositionsToJump.clear();
            positionsToMove_Q.clear();
        }
    }

    //This method removes the stone from the Board
    public void removeStone(int row, int col) {
        for (int i = 0; i < whiteStonesIds.length; i++) {
            for (int j = 0; j < whiteStonesIds.length; j++) {
                if (whiteStonesIds[i][j] == stones[row][col]) {
                    whiteStonesIds[i][j]=0;
                    System.out.println(whiteStonesIds.length + " " + whiteStonesIds[i].length);
                }
                else if (redStonesIds[i][j] == stones[row][col]) {
                    redStonesIds[i][j] = 0;
                }
            }
        }
        View v = findViewById(stones[row][col]);
        stones[row][col] = 0;
        gameLayout.removeView(v);
    }




}
