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

import java.util.List;

public class GameField extends AppCompatActivity {


    FrameLayout gameLayout;
    DrawingView board;
    protected Stone[][] stones = new Stone[8][8];
    Cell[][] cells = new Cell[8][8];
    Stone stone;
    Paint p;
    Context context;
    List<Cell> availablePostions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        board = new DrawingView(this);
        gameLayout = findViewById(R.id.gamelayout);
        gameLayout.addView(board);
        context=this;
        p = new Paint();
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
            drawBoard(canvas);
            drawStones(canvas);
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
                            p.setColor(Color.GRAY);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i, p);
                            gameLayout.addView(cell);
                            cells[i][j]=cell;



                        } else {
                            p.setColor(Color.BLACK);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i, p);
                            cells[i][j]=cell;
                            gameLayout.addView(cell);

                        }
                    } else {
                        if (j % 2 == 0) {
                            p.setColor(Color.BLACK);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i, p);
                            cells[i][j]=cell;
                            gameLayout.addView(cell);

                        } else {
                            p.setColor(Color.GRAY);
                            cell = new Cell(context, left, top,right, bottom, posx, posy,j,i, p);
                            cells[i][j]=cell;
                            gameLayout.addView(cell);
                        }
                    }
                    //canvas.drawRect(rect, p);

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
                        p.setColor(Color.BLACK);
                        stone = new Stone(this, posx, posy, p, j, i, cells[i][j]);
                        stones[i][j] = stone;
                        gameLayout.addView(stone);
                        posx+=2*diffX;
                    }
                }
                else if(i%2!=0){
                    if(j % 2==0){
                        p.setColor(Color.BLUE);
                        stone = new Stone(this, posx, posy, p, i, j, cells[i][j]);
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
                        p.setColor(Color.BLUE);
                        stone = new Stone(this, posx, posy, p, j, i, cells[i][j]);
                        stones[i][j] = stone;
                        gameLayout.addView(stone);
                        posx+=2*diffX;
                    }
                }
                else if(i%2==0 && i<=8){
                    if(j % 2 != 0){
                        p.setColor(Color.BLUE);
                        stone = new Stone(this, posx, posy, p, j, i, cells[i][j]);
                        stones[i][j] = stone;
                        gameLayout.addView(stone);
                        posx+=2*diffX;
                    }
                }
            }
            posy+=diffY;
        }

    }

}
