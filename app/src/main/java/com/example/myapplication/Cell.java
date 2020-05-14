package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Cell extends View implements View.OnTouchListener, View.OnClickListener{

    Rect rect;
    Paint paint;
    int row;
    int col;
    float posx;
    float posy;
    int left, top, right, bottom;
    boolean occupied;
    GameField game = new GameField();

    public Cell(Context context, int left, int top, int right, int bottom, float posx, float posy, int col, int row, Paint p, boolean occupied) {
        super(context);
        this.posx=posx;
        this.posy=posy;
        this.row=row;
        this.col=col;
        this.paint=p;
        this.left = left;
        this.right=right;
        this.top=top;
        this.bottom=bottom;
        this.occupied = occupied;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("Position hinzugefügt");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        System.out.println("clicked from Cell");
        return true;
    }
    public int getCol(){
        return this.col;
    }
    public int getRow(){
        return this.row;
    }

    public boolean checkIfOccupied(){
        return this.occupied;
    }


    @Override
    public void onClick(View v) {
        System.out.println("clicked from Cell");

        //game.checkIfStoneCanMove()
    }
}
