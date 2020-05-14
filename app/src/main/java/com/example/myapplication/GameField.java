package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class GameField extends Activity implements View.OnClickListener{


    FrameLayout gameLayout;
    protected Stone[][] stones = new Stone[8][8];
    Cell[][] cells = new Cell[8][8];
    Stone stone;
    Paint p;
    Context context;
    Cell cell;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        gameLayout = findViewById(R.id.gamelayout);
        context = this;
        gameLayout.addView(new DrawingView(this), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    public void drawNewStone(float posx, float posy, Stone stone, int col, int row, Cell position){
        if(stone instanceof darkstone){
            gameLayout.addView(new darkstone(this, posx, posy, new Paint(Color.BLACK),col, row));
        }else{
            gameLayout.addView(new whitestone(this, posx, posy, new Paint(Color.BLACK),col, row));
        }

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        System.out.println("Click");
        v.setBackgroundColor(R.color.green);
    }

    final class DrawingView extends View {

        public DrawingView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            super.onDraw(canvas);
            drawBoard(canvas);
            drawBlackStones(canvas);
            drawWhiteStones(canvas);
        }

    }
    public void drawBoard(Canvas canvas) {
        p = new Paint();
        Rect rect = new Rect();
        int diffX = canvas.getWidth() / 8;
        int diffY = canvas.getHeight() / 8;
        int left = 0, top = 0, right = left + diffX, bottom = top + diffY;
        float posx = left;
        float posy = top;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        p.setColor(Color.GRAY);
                        cell = new Cell(context, left, top, right, bottom, posx, posy, j, i, p, false);
                        cell.setOnClickListener(this);
                        cells[i][j] = cell;
                        gameLayout.addView(cells[i][j]);
                        rect = new Rect(left, top, right, bottom);


                    } else {
                        p.setColor(Color.BLACK);
                        cell = new Cell(context, left, top, right, bottom, posx, posy, j, i, p, true);
                        cell.setOnClickListener(this);

                        cells[i][j] = cell;
                        gameLayout.addView(cells[i][j]);
                        rect = new Rect(left, top, right, bottom);
                    }
                } else {
                    if (j % 2 != 0) {
                        p.setColor(Color.GRAY);
                        cell = new Cell(context, left, top, right, bottom, posx, posy, j, i, p, false);
                        cell.setOnClickListener(this);

                        cells[i][j] = cell;
                        gameLayout.addView(cells[i][j]);
                        rect = new Rect(left, top, right, bottom);

                    } else {
                        p.setColor(Color.BLACK);
                        cell = new Cell(context, left, top, right, bottom, posx, posy, j, i, p, true);
                        cell.setOnClickListener(this);
                        cells[i][j] = cell;
                        gameLayout.addView(cells[i][j]);
                        rect = new Rect(left, top, right, bottom);
                    }
                }
                canvas.drawRect(rect, p);
                left = right;
                right += diffX;

            }
            left = 0;
            right = left + diffX;
            top = bottom;
            bottom += diffY;
        }
    }
    public void drawBlackStones(Canvas canvas) {
        p = new Paint();
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int diffX = width / 8;
        int diffY = height / 8;
        float posx=0;
        float posy = diffY / 2;
        for (int i = 0; i < 3; i++) {
            if (i % 2 != 0) {
                posx = diffX / 2;
            } else {
                posx = diffX + (diffX / 2);
            }
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0) {
                    if (j % 2 != 0) {
                        p.setColor(Color.GRAY);
                        stone = new darkstone(context, posx, posy, p, j, i);
                        stone.setOnClickListener(this);
                        stones[i][j] = stone;
                        gameLayout.addView(stones[i][j]);
                        posx += 2 * diffX;
                    }
                } else {
                    if (j % 2 == 0) {
                        p.setColor(Color.GRAY);
                        stone = new darkstone(context, posx, posy, p, i, j);
                        stones[i][j] = stone;
                        stone.setOnClickListener(this);
                        gameLayout.addView(stones[i][j]);
                        canvas.drawCircle(posx, posy,20, p);
                        posx += 2 * diffX;
                    }
                }
            }
            posy += diffY;
        }
    }

    public void drawWhiteStones(Canvas canvas) {
        p = new Paint();
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int diffX = width / 8;
        int diffY = height / 8;
        float posy = 5 * diffY + diffY / 2;
        float posx = diffX / 2;
        for (int i = 5; i < 8; i++) {
            if (i % 2 != 0) {
                posx = diffX / 2;
            } else {
                posx = diffX + (diffX / 2);
            }
            for (int j = 0; j < 8; j++) {
                if (i % 2 != 0) {
                    if (j % 2 == 0) {
                        p.setColor(Color.GREEN);
                        stone = new whitestone(context, posx, posy, p, j, i);
                        stones[i][j] = stone;
                        gameLayout.addView(stones[i][j]);
                        canvas.drawCircle(posx, posy,20, p);

                        posx += 2 * diffX;
                    }
                } else {
                    if (j % 2 != 0) {
                        p.setColor(Color.GREEN);
                        stone = new whitestone(context, posx, posy, p, j, i);
                        stones[i][j] = stone;
                        gameLayout.addView(stones[i][j]);
                        canvas.drawCircle(posx, posy,20, p);

                        posx += 2 * diffX;
                    }
                }
            }
            posy += diffY;
        }

    }

        @SuppressLint("ResourceAsColor")
        public List<Cell> getAvailablePositionsForDarkStones(int col, int row) {
            List<Cell> availablePositions = new ArrayList<>();
            for (int i = row + 1; i < row + 2; i++) {
                for (int j = col - 1; j < col + 2; j++) {
                    if (j == col) {
                        continue;
                    }
                    if (j < 0 && j > 7 && i < 0 && i > 7){

                    }
                }
            }
            return availablePositions;
        }

        @SuppressLint("ResourceAsColor")
        public List<Cell> getAvailablePositionsForWhiteStones(int col, int row) {
            List<Cell> availablePositions = new ArrayList<>();
            for (int i = row -1; i > row -2; i--) {
                for (int j = col - 1; j < col + 2; j++) {
                    if (j == col) {
                        continue;
                    }
                    if (j > 0 && j < 8 && i > 0 && i <8)
                        availablePositions.add(cells[i][j]);

                }
            }
            return availablePositions;
        }


        //Prüft ob der Stein sich bewegen kann
        public boolean checkIfStoneCanMove(Stone stone, Cell position){
        boolean canMove = false;
        if(stone instanceof darkstone){
            if(stone.getRow()< position.getRow()&&(stone.getCol()-position.getCol()<1) && !position.checkIfOccupied() ){
                return true;
            }
            else {
                return false;
            }
        }

        return canMove;
        }
    }

