package com.example.myapplication;

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
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class GameField extends Activity implements View.OnClickListener{

    GridLayout gameLayout;
    protected Stone[][] stones = new Stone[8][8];
    Stone stone;
    Paint p;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_field);
        gameLayout = findViewById(R.id.gamelayout);
    }


    final class DrawingStones extends View{

        public DrawingStones(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawBlackStones(canvas);
            drawWhiteStones(canvas);
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
                        p.setColor(Color.BLACK);
                        stone = new darkstone(this, posx, posy, p, j, i);
                        stones[i][j] = stone;
                        gameLayout.addView(stones[i][j]);
                        posx += 2 * diffX;
                    }
                } else {
                    if (j % 2 == 0) {
                        p.setColor(Color.BLACK);
                        stone = new darkstone(this, posx, posy, p, i, j);
                        stones[i][j] = stone;
                        gameLayout.addView(stones[i][j]);
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
                        stone = new whitestone(this, posx, posy, p, j, i);
                        stones[i][j] = stone;
                        gameLayout.addView(stones[i][j]);

                        posx += 2 * diffX;
                    }
                } else {
                    if (j % 2 != 0) {
                        p.setColor(Color.GREEN);
                        stone = new whitestone(this, posx, posy, p, j, i);
                        stones[i][j] = stone;
                        gameLayout.addView(stones[i][j]);
                        posx += 2 * diffX;
                    }
                }
            }
            posy += diffY;
        }


    }

//------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(View v) {

    }
}

