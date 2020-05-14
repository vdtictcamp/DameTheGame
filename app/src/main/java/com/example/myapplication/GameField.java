package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class GameField extends AppCompatActivity {


    FrameLayout gameLayout;
    DrawingView board;
    DrawingStones drawingStones;
    protected Stone[][] stones = new Stone[8][8];
    Cell[][] cells = new Cell[8][8];
    Stone stone;
    Paint p;
    Context context;
    List<Cell> availablePostions = new ArrayList<>();
    Canvas canvas=new Canvas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        board = new DrawingView(this);
        drawingStones=new DrawingStones(this);
        gameLayout = findViewById(R.id.gamelayout);
        context=this;
        gameLayout.addView(board);
        gameLayout.addView(drawingStones);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    final class DrawingView extends View {

        public DrawingView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            super.onDraw(canvas);
            p = new Paint();
            drawBoard(canvas);
        }

        public void drawBoard(Canvas canvas) {
            Cell cell;
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
                            p.setColor(Color.BLACK);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i, p);
                            gameLayout.addView(cell);
                            cells[i][j]=cell;

                        } else {
                            p.setColor(Color.GRAY);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i, p);
                            cells[i][j]=cell;
                            gameLayout.addView(cell);

                        }
                    } else {
                        if (j % 2 != 0) {
                            p.setColor(Color.GRAY);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i, p);
                            cells[i][j]=cell;
                            gameLayout.addView(cell);

                        } else {
                            p.setColor(Color.BLACK);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i,p);
                            cells[i][j]=cell;
                            gameLayout.addView(cell);
                        }
                    }

                    left = right;
                    right += diffX;
                }
                left = 0;
                right = left + diffX;
                top = bottom;
                bottom += diffY;
            }
        }


    }


    final class DrawingStones extends View{

        public DrawingStones(Context context) {
            super(context);
        }

        public void drawStones(Canvas canvas) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            int diffX = width / 8;
            int diffY = height / 8;
            float posx = diffX+diffX/2;
            float posy = diffY / 2;
            for (int i = 0; i < 3; i++) {
                if(i%2!=0){
                    posx = diffX/2;
                }else{
                    posx = diffX+(diffX/2);
                }
                for (int j = 0; j < 8; j++) {
                    if (i % 2 == 0) {
                        if (j % 2 != 0) {
                            p.setColor(Color.WHITE);
                            stone = new darkstone(context, posx, posy, p, j, i, cells[i][j]);
                            stones[i][j] = stone;
                            gameLayout.addView(stone);
                            posx+=2*diffX;
                        }
                    }
                    else if(i%2!=0){
                        if(j % 2==0){
                            p.setColor(Color.BLACK);
                            stone = new darkstone(context, posx, posy, p, i, j, cells[i][j]);
                            stones[i][j] = stone;
                            gameLayout.addView(stone);
                            posx+=2*diffX;
                        }
                    }
                }

                posy+=diffY;
            }

            posy = 5*diffY+diffY/2;
            posx = diffX/2;
            for(int i=5; i<8; i++){
                if(i%2!=0){
                    posx = diffX/2;
                }else{
                    posx = diffX+(diffX/2);
                }
                for(int j=0; j<8; j++){
                    if(i%2!=0){
                        if(j%2==0){
                            p.setColor(Color.BLACK);
                            stone = new whitestone(context, posx, posy, p, j, i, cells[i][j]);
                            stones[i][j] = stone;
                            gameLayout.addView(stone);
                            posx+=2*diffX;
                        }
                    }
                    else if(i%2==0){
                        if(j % 2 != 0){
                            p.setColor(Color.WHITE);
                            stone = new darkstone(context, posx, posy, p, j, i, cells[i][j]);
                            stones[i][j] = stone;
                            gameLayout.addView(stone);
                            posx+=2*diffX;
                        }
                    }
                }
                posy+=diffY;
            }

        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawStones(canvas);
        }
    }



    public List<Cell> getAvailablePositionsForDarkStones(int col, int row){
        List<Cell> availablePositions = new ArrayList<>();
        for(int i=row+1; i<row+2; i++){
            for(int j=col-1; j<col+2; j++){
                if(j==col){
                    continue;
                }
                if(j<0 && j>7 && i<0 && i>7)
                availablePositions.add(cells[i][j]);
            }
        }
        return availablePositions;
    }

    public List<Cell> getAvailablePositionsForWhiteStones(int col, int row){
        List<Cell> availablePositions = new ArrayList<>();
        for(int i=row+1; i<row+2; i++){
            for(int j=col-1; j<col+2; j++){
                if(j==col){
                    continue;
                }
                if(j<0 && j>7 && i<0 && i>7)
                    availablePositions.add(cells[i][j]);
            }
        }
        return availablePositions;
    }


}
