package com.example.myapplication;

import com.example.myapplication.Activities.GameField;
import com.example.myapplication.Activities.localGame;
import com.example.myapplication.GameEngine.GameController;
import com.example.myapplication.Queen.QueenChecker;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    Random rand = new Random();

    GameController controller;
    int[][]positionsIds;
    int[][]stones;
    int[][]redStonesIds;
    int[][]whiteStonesIds;
    localGame game;
    GameField gameBeta;
    QueenChecker queenChecker;


    public ExampleUnitTest(){
    game = new localGame();
    gameBeta=new GameField();
    queenChecker = new QueenChecker(game, positionsIds);

    //For the testst we need the following resources
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

        controller = new GameController(game, positionsIds);
}

    public int getRow(){
        return rand.nextInt(7);
    }
     public int getCol(){
        return rand.nextInt(7);
    }

    @Test public void switchIndexOfStoneTest(){
        int rowStone = getRow();
        int colStone=getCol();
        int rowPos= getRow();
        int colPos = getCol();
        stones = controller.switchPosOfStoneInArray(stones, positionsIds, stones[rowStone][colStone],positionsIds[rowPos][colPos]);
        assertEquals(stones[rowStone][colStone], 0);
        assertNotNull(stones[rowPos][colStone]);
    }


    @Test public void removeStoneTest(){
        int rowStone = getRow();
        int colStone = getCol();
        stones = controller.removeStoneTest(stones, rowStone, colStone);
        assertTrue(stones[rowStone][colStone]==0);
    }

    @Test public void checkIfPosIsBlockedTest(){
        //With the following Code we test if
        int rowPos = getRow();
        int colPos = getCol();
        int colStone = getCol();
        int rowStone = getCol();
        boolean blocked = controller.checkIfStoneIsBlockingPos(stones, colPos, rowPos);
        if(!blocked){
            assertTrue(stones[rowPos][colPos]==0);
        }else{
            assertTrue(stones[rowPos][colPos]!=0);
        }

        //We test now three times if the position is blocked when we change the position of a stone in the array
        //This Test shows the following fact: We change the postion of stone. The destination position should now be blocked
        //So the value of the stone array at the row position and colposition should not be null
       for(int i=0; i<3; i++){
            do {
                rowPos=getRow();
                colPos = getCol();
                rowStone= getCol();
                colStone = getCol();
            }while(stones[rowStone][colStone]==0);
            stones = controller.switchPosOfStoneInArray(stones, positionsIds, stones[rowStone][colStone],positionsIds[rowPos][colPos]);
            boolean isBlocked = controller.checkIfStoneIsBlockingPos(stones,colPos, rowPos);
            assertTrue(stones[rowPos][colPos]!=0);
        }
    }

    @Test public void checkNextJumpTest(){
        int row = getRow();
        int col = getCol();
        boolean canJump = queenChecker.checkNextJump(stones, row, col);
        if(!canJump){
            assertTrue(stones[row][col]==0);
        }else{
            assertTrue(stones[row][col]!=0);
        }
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

}